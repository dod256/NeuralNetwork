package com.company;

import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MyMathTest {

    private Vector string = new Vector(Arrays.asList(1.0, 2.0, 3.0), true);
    private Vector column = new Vector(Arrays.asList(1.0, 2.0, 3.0));

    @Test
    public void testMultiplyVV() {
        Matrix resColStr = new Matrix(Arrays.asList(
                Arrays.asList(1.0, 2.0, 3.0),
                Arrays.asList(2.0, 4.0, 6.0),
                Arrays.asList(3.0, 6.0, 9.0)
        ));
        assertEquals(resColStr, MyMath.multiplyVV(column, string));
    }

    @Test
    public void multiplyElementWiseVV() {
        Vector resColCol = new Vector(Arrays.asList(1.0, 4.0, 9.0));
        Vector resStrStr = new Vector(Arrays.asList(1.0, 4.0, 9.0), true);

        assertEquals(resColCol, MyMath.multiplyElementWiseVV(column, column));
        assertEquals(resStrStr, MyMath.multiplyElementWiseVV(string, string));
        assertEquals(null, MyMath.multiplyElementWiseVV(string, column));
        assertEquals(null, MyMath.multiplyElementWiseVV(column, new Vector(Arrays.asList(1.0, 2.0))));
    }
}