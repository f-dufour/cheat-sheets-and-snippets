package xyz.dufour.matchcontract.exceptions;

import org.hyperledger.fabric.shim.ChaincodeException;

public class NotFoundException extends ChaincodeException {
    public NotFoundException(){
        super("Not found exception", "404");
    }
}
