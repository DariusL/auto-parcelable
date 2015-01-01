package lt.dariusl.library.test;

import android.test.InstrumentationTestCase;

/**
 * Created by Darius on 2015.01.01.
 */
public class ArrayTests extends InstrumentationTestCase{
    public void testIntArray(){
        TestUtils.writeReadAssertArray(new int[]{5, 8, 9}, int[].class);
    }

    public void testStringArray(){
        TestUtils.writeReadAssertArray(new String[]{"asfafs", "shtdhbf", "bcxbxbr", "tyuj"}, String[].class);
    }

    public void testMultidimensionalPrimitives(){
        int[][] array = new int[2][2];
        array[0][0] = 16;
        array[0][1] = 95;
        array[1][0] = 51;
        array[1][1] = 1;
        TestUtils.writeReadAssertArray(array, int[][].class);
    }
}
