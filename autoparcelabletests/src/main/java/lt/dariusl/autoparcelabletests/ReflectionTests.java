package lt.dariusl.autoparcelabletests;

import android.test.InstrumentationTestCase;

import lt.dariusl.autoparcelabletests.data.StringLong;

/**
 * Created by Darius on 2015.01.03.
 */
public class ReflectionTests extends InstrumentationTestCase {
    public void testStringLong(){
        StringLong value = new StringLong("fasgasg", 816148L);
        TestUtils.writeReadAssert(value, StringLong.class);
    }
}
