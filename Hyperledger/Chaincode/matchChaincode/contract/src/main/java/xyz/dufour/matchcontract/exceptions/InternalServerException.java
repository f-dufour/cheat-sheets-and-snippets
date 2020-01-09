package xyz.dufour.matchcontract.exceptions;

import org.hyperledger.fabric.shim.ChaincodeException;

public class InternalServerException extends ChaincodeException {
    public InternalServerException(){
        super("An internal error happened", "500");
    }
}
