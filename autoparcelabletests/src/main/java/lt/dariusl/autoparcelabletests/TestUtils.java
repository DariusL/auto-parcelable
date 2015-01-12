package lt.dariusl.autoparcelabletests;

import android.os.Parcel;

import junit.framework.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lt.dariusl.autoparcelable.ParcelableWrapper;
import lt.dariusl.autoparcelable.Parcelator;
import lt.dariusl.autoparcelable.Utils;

/**
 * Created by Darius on 2015.01.01.
 */
public class TestUtils {
    public static <T> void writeReadAssert(T in, Class<T> cls){
        Parcel parcel = Parcel.obtain();
        Parcelator.writeToParcel(parcel, in, cls);
        parcel.setDataPosition(0);
        T out = Parcelator.readFromParcel(parcel, cls);
        assertReflectionEquals(in, out);
    }

    public static void assertReflectionEquals(Object expected, Object actual){
        Assert.assertTrue(equals(expected, actual));
    }

    public static <T> boolean equals(T left, T right){
        if(left == right){
            return true;
        }

        if(left == null || right == null){
            return false;
        }

        Class<?> cls = left.getClass();
        if(!right.getClass().equals(cls)){
            return false;
        }

        if(cls.isArray()){
            return arrayEquals(left, right);
        }

        if(Collection.class.isAssignableFrom(cls) || Modifier.isFinal(cls.getModifiers())){
            return left.equals(right);
        }

        try {
            List<Field> fields = Utils.getAllFields(cls);
            for (Field field : fields) {
                if(field.isSynthetic() || Modifier.isStatic(field.getModifiers())){
                    continue;
                }

                field.setAccessible(true);
                Object l = field.get(left);
                Object r = field.get(right);
                if(!equals(l, r)){
                    return false;
                }
            }
            return true;
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    public static <T> boolean arrayEquals(T left, T right){
        if(left instanceof int[]){
            return Arrays.equals((int[]) left, (int[]) right);
        }else if(left instanceof long[]){
            return Arrays.equals((long[])left, (long[])right);
        }else if(left instanceof boolean[]){
            return  Arrays.equals((boolean[])left, (boolean[])right);
        }else if(left instanceof byte[]){
            return Arrays.equals((byte[])left, (byte[])right);
        }else if(left instanceof short[]){
            return Arrays.equals((short[])left, (short[])right);
        }else if(left instanceof char[]){
            return Arrays.equals((char[])left, (char[])right);
        }else if(left instanceof float[]){
            return Arrays.equals((float[])left, (float[])right);
        }else if(left instanceof double[]){
            return Arrays.equals((double[])left, (double[])right);
        }else if(left instanceof Object[]){
            return Arrays.deepEquals((Object[])left, (Object[])right);
        }else{
            throw new IllegalArgumentException("Not an array type");
        }
    }
}
