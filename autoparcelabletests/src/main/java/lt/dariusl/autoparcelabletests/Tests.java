package lt.dariusl.autoparcelabletests;

import android.os.Parcelable;
import android.test.InstrumentationTestCase;

import lt.dariusl.autoparcelable.ParcelableWrapper;
import lt.dariusl.autoparcelabletests.data.ReflectedField;
import lt.dariusl.autoparcelabletests.data.StringLong;
import lt.dariusl.autoparcelabletests.data.StringLongAuto;
import lt.dariusl.autoparcelabletests.data.StringLongParcelable;

/**
 * Created by Darius on 2015.01.01.
 */
public class Tests extends InstrumentationTestCase{
    public void testIntArray(){
        TestUtils.writeReadAssert(new int[]{5, 8, 9}, int[].class);
    }

    public void testStringArray(){
        TestUtils.writeReadAssert(new String[]{"asfafs", "shtdhbf", "bcxbxbr", "tyuj"}, String[].class);
    }

    public void testMultidimensionalPrimitives(){
        int[][] array = new int[2][2];
        array[0][0] = 16;
        array[0][1] = 95;
        array[1][0] = 51;
        array[1][1] = 1;
        TestUtils.writeReadAssert(array, int[][].class);
    }

    public void testStringLongAuto(){
        StringLongAuto value = new StringLongAuto("fasgasg", 816148L);
        TestUtils.writeReadAssert(value, StringLongAuto.class);
    }

    public void testStringLongParcelable(){
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

    public void testString(){
        TestUtils.writeReadAssert("asasgasfsaf", String.class);
    }

    public void testDynamicString(){
        TestUtils.writeReadAssert("asasgasga", Object.class);
    }

    public void testInteger(){
        TestUtils.writeReadAssert(506158, int.class);
    }

    public void testDouble(){
        TestUtils.writeReadAssert(5.0, double.class);
    }

    public void testBoxedInteger(){
        TestUtils.writeReadAssert(60, Integer.class);
    }

    public void testDynamicInteger(){
        TestUtils.writeReadAssert(81, Object.class);
    }

    public void testNullInteger(){
        TestUtils.writeReadAssert(null, Integer.class);
    }

    public void testStringLongReflective(){
        StringLong value = new StringLong("fasgasg", 816148L);
        TestUtils.writeReadAssert(value, StringLong.class);
    }

    public void testReflectedField(){
        ReflectedField value = new ReflectedField(new StringLong("sagtrbgs", 16135164L), 8);
        TestUtils.writeReadAssert(value, ReflectedField.class);
    }

    public void testStringLongWrapper(){
        StringLong value = new StringLong("fasgasg", 816148L);
        TestUtils.writeReadAssert(new ParcelableWrapper<>(value), ParcelableWrapper.class);
    }
}
