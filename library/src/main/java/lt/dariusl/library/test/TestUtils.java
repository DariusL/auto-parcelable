package lt.dariusl.library.test;

import android.os.Parcel;

import junit.framework.Assert;

import java.util.Arrays;

import lt.dariusl.library.Parcelator;

/**
 * Created by Darius on 2015.01.01.
 */
public class TestUtils {
    public static  <T> void writeReadAssert(T in, Class<T> cls){
        Parcel parcel = Parcel.obtain();
        Parcelator.writeToParcel(parcel, in, cls);
        parcel.setDataPosition(0);
        T out = Parcelator.readFromParcel(parcel, cls);
        Assert.assertEquals(in, out);
    }

    private static <T> void assertEqualsArrays(T left, T right){
        if(left instanceof int[]){
            Assert.assertTrue(Arrays.equals((int[]) left, (int[]) right));
        }else if(left instanceof long[]){
            Assert.assertTrue(Arrays.equals((long[])left, (long[])right));
        }else if(left instanceof boolean[]){
            Assert.assertTrue(Arrays.equals((boolean[])left, (boolean[])right));
        }else if(left instanceof byte[]){
            Assert.assertTrue(Arrays.equals((byte[])left, (byte[])right));
        }else if(left instanceof short[]){
            Assert.assertTrue(Arrays.equals((short[])left, (short[])right));
        }else if(left instanceof char[]){
            Assert.assertTrue(Arrays.equals((char[])left, (char[])right));
        }else if(left instanceof float[]){
            Assert.assertTrue(Arrays.equals((float[])left, (float[])right));
        }else if(left instanceof double[]){
            Assert.assertTrue(Arrays.equals((double[])left, (double[])right));
        }else if(left instanceof Object[]){
            Assert.assertTrue(Arrays.deepEquals((Object[])left, (Object[])right));
        }else{
            throw new IllegalArgumentException("Not an array type");
        }
    }

    public static <T> void writeReadAssertArray(T in, Class<T> cls){
        Parcel parcel = Parcel.obtain();
        Parcelator.writeToParcel(parcel, in, cls);
        parcel.setDataPosition(0);
        T out = Parcelator.readFromParcel(parcel, cls);
        assertEqualsArrays(in, out);
    }
}
