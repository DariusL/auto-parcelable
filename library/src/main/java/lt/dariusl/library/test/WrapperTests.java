package lt.dariusl.library.test;

import android.test.InstrumentationTestCase;

import lt.dariusl.library.test.data.StringLong;

/**
 * Created by Darius on 2015.01.11.
 */
public class WrapperTests extends InstrumentationTestCase {
    public void testStringLong(){
        StringLong value = new StringLong("fasgasg", 816148L);
        TestUtils.writeReadAssertWrapper(value);
    }
}
