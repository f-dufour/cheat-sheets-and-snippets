package xyz.dufour.matchcontract;

import org.junit.jupiter.api.Test;
import xyz.dufour.matchcontract.utils.Transform;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransformTest {

    private final List<Integer> dumbIntegerList = List.of(1, 3, 6, 9, 12);
    private final Iterator<Integer> dumbIntegerIterator = dumbIntegerList.iterator();


    @Test
    public void iteratorToList(){
        List<Integer> tranformedIterator = Transform.iteratorToList(this.dumbIntegerIterator);
        assertTrue(tranformedIterator.containsAll(dumbIntegerList));
    }

}
