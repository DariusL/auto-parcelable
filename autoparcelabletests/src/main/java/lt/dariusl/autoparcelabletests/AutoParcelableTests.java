package lt.dariusl.autoparcelabletests;

import android.test.InstrumentationTestCase;

import lt.dariusl.autoparcelabletests.data.StringLongAuto;

/**
 * Created by Darius on 2015.01.09.
 */
public class AutoParcelableTests extends InstrumentationTestCase {
    public void testStringLong(){
        StringLongAuto value = new StringLongAuto("fasgasg", 816148L);
        TestUtils.writeReadAssert(value, StringLongAuto.class);
    }
}
