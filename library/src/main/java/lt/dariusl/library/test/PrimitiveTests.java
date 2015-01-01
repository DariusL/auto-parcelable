package lt.dariusl.library.test;

import android.test.InstrumentationTestCase;

/**
 * Created by Darius on 2015.01.01.
 */
public class PrimitiveTests extends InstrumentationTestCase {

    public void testInteger(){
        TestUtils.writeReadAssert(5, int.class);
    }

    public void testDouble(){
        TestUtils.writeReadAssert(5.0, double.class);
    }

    public void testBoxedInteger(){
        TestUtils.writeReadAssert(20, Integer.class);
    }

    public void testDynamicInteger(){
        TestUtils.writeReadAssert(60, Object.class);
    }

    public void testNullInteger(){
        TestUtils.writeReadAssert(null, Integer.class);
    }
}
