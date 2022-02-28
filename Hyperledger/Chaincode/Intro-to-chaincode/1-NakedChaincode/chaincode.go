package main

// ------------------------------------------------------------------------------------
// Import the chaincode dependencies (mandatory)
// ------------------------------------------------------------------------------------

import (
	"fmt"
	"github.com/hyperledger/fabric-chaincode-go/shim"
	pb "github.com/hyperledger/fabric-protos-go/peer"
)

type NakedChaincode struct { }

// ------------------------------------------------------------------------------------
// The 2 methods from the interface to implement (mandatory)
// ------------------------------------------------------------------------------------

// Called upon instantiation; use to initialize components
func (t *NakedChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {
	// All the logic we want (or nothing) and return ...
	return shim.Success(nil)
	// ... or ...
	return shim.Error("Error during Init")
}

// Method that will update the blockchain
func (t *NakedChaincode) Invoke(stub shim.ChaincodeStubInterface) pb.Response {
	// Look for invoke, delete, query... and return ...
	return shim.Success(nil)
	// ... or ...
	return shim.Error("Error during Invoke")
}

// ------------------------------------------------------------------------------------
// The methods we define to interact with the blockchain (as we want)
// ------------------------------------------------------------------------------------

func (t *NakedChaincode) invoke(stub shim.ChaincodeStubInterface) pb.Response {
	// Do stuff and return ...
	return shim.Success(nil) // shim.Error(nil)
}


func (t *NakedChaincode) delete(stub shim.ChaincodeStubInterface) pb.Response {
	// Do stuff
	return shim.Success(nil) // shim.Error(nil)
}

func (t *NakedChaincode) query(stub shim.ChaincodeStubInterface) pb.Response {
	// Do stuff
	return shim.Success(nil) // shim.Error(nil)
}

// ------------------------------------------------------------------------------------
// The method we define to interact with the blockchain.
// ------------------------------------------------------------------------------------

func main() {
	// Entry point of the chaincode:
	err := shim.Start(new(NakedChaincode))
	if err != nil {
		fmt.Printf("Could not start the NakedChaincode: %s", err)
	}
}
