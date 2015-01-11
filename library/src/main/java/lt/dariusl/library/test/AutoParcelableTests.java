package lt.dariusl.library.test;

import android.test.InstrumentationTestCase;

import lt.dariusl.library.test.data.StringLongAuto;

/**
 * Created by Darius on 2015.01.09.
 */
public class AutoParcelableTests extends InstrumentationTestCase {
    public void testStringLong(){
        StringLongAuto value = new StringLongAuto("fasgasg", 816148L);
        TestUtils.writeReadAssert(value, StringLongAuto.class);
    }
}
