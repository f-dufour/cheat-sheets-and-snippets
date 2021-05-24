package main

import (
	"fmt"
	"github.com/hyperledger/fabric-chaincode-go/shim"
	pb "github.com/hyperledger/fabric-protos-go/peer"
	"strconv"
)

type MinimalChaincode struct { }

// Idea: Initialize 2 accounts with 2 amounts provided to the chaincode
// SUPER important to catch early errors here, transaction should not be committed!!!
func (t *MinimalChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {

	// 1. We query the function and the parameters for init

	_, args := stub.GetFunctionAndParameters()
	// 1.1 We initialize the parameters
	var A, B string // Keys
	var Aval, Bval int // Values
	// 1.2 We check everything is fine
	if len(args) != 4 {
		return shim.Error("Expecting 4 parameters")
	}
	// 1.3 We save the parameters
	A = args[0]
	B = args[2]
	// WARNING: Values are function parameters, thus strings ! We convert them to int so we can perform arithmetic poerations
	Aval, err := strconv.Atoi(args[1])
	if err != nil {
		return shim.Error("Expecting integer value for asset")
	}
	Bval, err = strconv.Atoi(args[3])
	if err != nil {
		return shim.Error("Expecting integer value for asset")
	}

	// 2. We can write those 2 accounts to the world state. We use PutState. We have to write data as array of bytes

	err = stub.PutState(A, []byte(strconv.Itoa(Aval)))
	if err != nil {
		return shim.Error(err.Error())
	}

	err = stub.PutState(B, []byte(strconv.Itoa(Bval)))
	if err != nil {
		return shim.Error(err.Error()) // an error here also reverse the state of A. Transaction never happened
	}

	// 3. everything went well, return success !
	return shim.Success(nil)

	// Operation will be simulated, returned to the SDK, committed to the ordere which will make blocks, peer will update the world state
	// Only then, the stub.getState(A)  will return the proper value. It takes time !
}

func (t *MinimalChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	return shim.Success(nil)
}

// This is a method we write, that is not in the interface to ease the business logic
// We query an account
// We pass the stub as an argument so that GetState is reachable
func (t *MinimalChaincode) query(stub shim.ChaincodeStubInterface, args []string) pb.Response{
	var account string
	var err error

	if len(args) != 1 {
		return shim.Error("Incorrect number of argument. Expecting the account to query")
	}

	// the key
	account = args[0]
	// the value we get from the world state
	accountValueBytes, err := stub.GetState(account)

	// check for error
	if err != nil {
		jsonResp := "{\"Error\":\"Nil failed to get state for " + account + "\"}"
		return shim.Error(jsonResp)
	}
	// more error
	if accountValueBytes == nil {
		jsonResp := "{\"Error\":\"Nil amount for " + account + "\"}"
		return shim.Error(jsonResp)
	}

	validJsonResp := "{\"Name\":\"" + account + "\", \"Amount\":\"" + string(accountValueBytes) + "\"}"
	fmt.Printf("Query response: %s", validJsonResp)
	return shim.Success(accountValueBytes)
}

func main() {
	err := shim.Start(new(MinimalChaincode))
	if err != nil {
		fmt.Printf("Could not start the MinimalChaincode: %s", err)
	}
}
