---
title: Learning chaincode
author: Florent Dufour
---

# Whay is it?

- Smart contracts written in Go, JS, or Java
- Executed inside a channel
- No limitations in terms of logic or libraries
- As many cc as we need inside a channel
- Only cc are able to update the ledger
- cc implement an interface: define the methods available (see below)
- Deploying a cc is done in 2 phases:
	1. Install the cc on the peer: Has to be on the peer because the peer contains the ledger. Once installed nothing happens really.
	2. Instantiate the cc on the peer: This is when the magin happens: connect to the ledger and create the connections. This is where we have to define the **Endorsement policy** wit the logic operators. (e.g. Before you update, I want every peer to verify before commiting // I want the majority // I want at least one peer)
- Policy is per chain code. Each chaincode has a policy

# Intrication

- 1 peer = 1 or many channels
- 1 channel = 1 ledger
- 1 channel = 1 or many chaincodes
- 1 chaincode = 1 policy

# In practice

- 1 chaincode = 2 methods to implement from the interface:
	1. Init: Called when the cc is instantiated (and when the peer is restarted and chaincode is upgraded !!)
	1. Invoke: Method that will update the ledger
- Chaincode contains a main method that starts the chaincode: shim.Start(new(Chaincode))

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
