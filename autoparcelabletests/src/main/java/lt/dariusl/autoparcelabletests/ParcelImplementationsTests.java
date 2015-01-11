package lt.dariusl.autoparcelabletests;

import android.test.InstrumentationTestCase;

/**
 * Created by Darius on 2015.01.01.
 */
public class ParcelImplementationsTests  extends InstrumentationTestCase {


    public void testString(){
        TestUtils.writeReadAssert("asasgasfsaf", String.class);
    }

    public void testDynamicString(){
        TestUtils.writeReadAssert("asasgasga", Object.class);
    }
}
