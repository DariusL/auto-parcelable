package lt.dariusl.library.test;

import android.os.Parcel;
import android.test.InstrumentationTestCase;

import java.lang.reflect.Constructor;

import lt.dariusl.library.Parcelator;
import lt.dariusl.library.test.data.StringLong;

/**
 * Created by Darius on 2015.01.03.
 */
public class ReflectionTests extends InstrumentationTestCase {
    public void testStringLong(){
        StringLong value = new StringLong("fasgasg", 816148L);
        TestUtils.writeReadAssert(value, StringLong.class);
    }
}
