package lt.dariusl.library.test;

import android.os.Parcel;
import android.os.Parcelable;
import android.test.InstrumentationTestCase;

import lt.dariusl.library.test.data.StringLongParcelable;

/**
 * Created by Darius on 2015.01.02.
 */
public class ParcelableTests extends InstrumentationTestCase{

    public void testStringLong(){
        StringLongParcelable value = new StringLongParcelable("fsafasfasf", 86219L);
        TestUtils.writeReadAssert(value, StringLongParcelable.class);
    }

    public void testStringLongAsParcelable(){
        StringLongParcelable value = new StringLongParcelable("fsafasfasf", 86219L);
        TestUtils.writeReadAssert(value, Parcelable.class);
    }

    public void testStringLongAsObject(){
        StringLongParcelable value = new StringLongParcelable("fsafasfasf", 86219L);
        TestUtils.writeReadAssert(value, Object.class);
    }
}
