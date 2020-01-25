---
title: Learning chaincode
author: Florent Dufour
---

# What is it?

- Smart contracts written in Go, JS, or Java
- Executed inside a channel
- No limitations in terms of logic or libraries
- As many chaincodes (cc) as we need inside a channel
- Only cc are able to update the ledger
- cc implement an interface: define the methods available (see below)
- Deploying a cc is done in 2 phases:
	1. Install the cc on the peer: Has to be on the peer because the peer contains the ledger. Once installed nothing happens really.
	2. Instantiate the cc on the peer: This is when the magin happens: connect to the ledger and create the connections. This is where we have to define the **Endorsement policy** wit the logic operators. (e.g. Before you update, I want every peer to verify before committing // I want the majority // I want at least one peer)
- Policy is per chain code. Each chaincode has a policy

> The chaincode manages the ledger state through transactions invoked by applications
>> Assets are created and updated by a specific chaincode, and cannot be accessed by another chaincode
>>> chaincode needs to be installed on every peer that will endorse a
transaction and instantiated on the channel.

# Intrication

- 1 peer = 1 or many channels
- 1 channel = 1 ledger
- 1 channel = 1 or many chaincodes
- 1 chaincode = 1 policy

# Different type of system chaincodes

| Short name | Long name | Role |
|:-:|-|-|
|QSCC| Query System ChainCode   | Ledger and Fabric related queries  |
|CSCC| Configuration System ChainCode  | Helps regulate access control  |
|LSCC| Lifecycle System Chaincode  | Defines the rules for the channel  |
|ESCC| Endorsement System Chaincode  | For endorsing transactions  |
|VSCC| Validation System Chaincode  | For validating transactions  |

# Chaincode APIs

Important interface: `ChaincodeStub`: provides functions to interact with the ledger. In js:

```typescript
getState(key: string): Promise<Buffer>; // Returns the value of the specified key from the ledger
putState(key: string, value: Buffer): Promise<void>; //Puts the specified key and value into the transaction's Write set as a datawrite proposal
deleteState(key: string): Promise<void>; // Records the specified key to be deleted in the Write set of the transaction proposal
```

:warning: put and delete take effect when the then transaction is validated **and** sucessfully commited.

:warning: Get only return the value when data modified by putState is commited. If the key does not exist in the db, empty buffer is returned.

# In practice

- Add the propoer dependencies (go, Java, or node.js)
- 1 chaincode = 2 methods to create:
	1. `Init`: Called when the cc is instantiated (and when the peer is restarted and chaincode is upgraded !!)
	1. `Invoke`: Method that will update the ledger
- Chaincode contains a main method that starts the chaincode: shim.Start(new(Chaincode))

>  :bangbang: See [NakedChaincode](./NakedChaincode/chaincode.go) in this repo.

## Init

- We can do whatever we want. This is just a method. Full language and libraries available
- Initialize the structures (if needed)
- Can be empty, but still has to feature shim.Success or shim.Error

## Invoke

A good practice is to catch arguments to see what to do:

```go
func (t *Chaincode) Invoke (stub shim.ChaincodeStubeInterface) pb.Response {
	fmt.Println("Invoke HERE")
	function, args := stub.GetFunctionAndParameters()
	if function == "invoke" {
		return t.invoke(stub, args)
	} else if function == "delete" {
		return t.delete(stub, args)
	} else if function == "query" {
		return t.query(stub, args)
	}
	return shim.Error("Method not found. Expecting invoke, delete or query")
}
```

- The shim.Success are in the methods invoke, delete and query
- stub methods and the world state:
	- stub.GetState(A): Get the current key associated with A in the world state (from the state database)
	- stub.PutState(A, Aval): Update the state (key) of A with Aval
	- ... to delete etc.
- CouchDB store the world state: Advantage: Natively works with JSON
- Possible to index **values** and make complex and efficient queries
- LevelDB = Key - Value store

# Fabric contrat

Common logic inside a contract:

1. Get the parameters
1. Validate the param
1. Fix the types
1. Evaluate the method
1. Read the current sate of the data
1. Simple updates
1. Serialize the modification
1. (possible to make composite key stub.CreateCompositeKey(index, []string{aaa, bbb, ccc}) to accelerate queries)
1. Write to the chain
 
No heavy computing in the contract: Image processing...

## Interfaces and methods

In `interfaces.go` we find the important methods and their documentation

```go
type Chaincode interface {
    Init(stub ChaincodesStubInterface) pb.Response
    Init(stub ChaincodeStubInterface) pb.Resonse
}

type ChaincodesStubInterface interface {
    GetArgs() ...
    GetStringArgs() ...
    ...
    GetState(key string) ...
    PutState(key string, value []byte) ...
    DelState(key string)
    ...
}
```

Also possible to work directly with couchDB
> Exemple with GetQueryResult

## Fabric contract API (In js)

Everything as above

- **Fabric-contract-api** node.js module : A high-level contract API for implementing smart contracts
- **Fabric-shim** node.js module: A low-level contract API for implementing smart contracts

### TypeScript Decorators

#### asset

- `@Property()`

#### asset-contract

- `@Transaction()` Functions that define the transaction on the ledger. This is how we interact.
	- `@Transaction(true)` or `@Transaction()` *submitted transaction*: Change the content of the ledger
	- `@Transaction(false)` *evaluated transaction*: Does not change the content. It's a query.
- `@Info`
- `@Returns`

# Events 

```go
stub.SetEvent(name string, payload []byte)
```

- Send an event with a name and a payload. Payload can be anything as it is an array of bytes (JSON, file, string...)
- The event is sent to the SDK once the block has been committed to the blockchain. Data is now immutable.
- SDK can listen for such events, unmarshall the payload, and do whatever we want (send email, kick another process...) !

- Event won't be sent if we hit a shim.Error(). Good practice: Put the SetEvent as late as possible.


# Packaging a smart contract

- A smart contract is packaged as a `.CDS` file
- A CDS can be installed on a Fabric peer

For help:

```sh
~$ peer chaincode -h
```

1. The chaincode must be installed:

```sh
~$ peer chaincode install
```

2. instantiated:

```sh
~$ peer chaincode instantiate
```

3. invoke chaincode's function:

```sh
~$ using the peer chaincode invoke
```

4. transactions can be created