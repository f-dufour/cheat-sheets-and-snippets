package xyz.dufour.matchcontract.utils;

import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Provides static classes to ease data types transformation */
public class Transform {

    /**
     * Basically transforms any iterable in a list by preserving the type.
     *
     * @param i The iterable
     * @return The iterable as a list
     */
    public static <T> List<T> iterableToList(Iterable<T> i) {
        List<T> l = new ArrayList<>();
        i.forEach(l::add);
        return l;
    }

    /**
     * Basically transforms any iterator in a list by preserving the type.
     *
     * @param i The iterator
     * @return The iterator as a list
     */
    public static <T> List<T> iteratorToList(Iterator<T> i) {
        List<T> l = new ArrayList<>();
        i.forEachRemaining(l::add);
        return l;
    }
}
