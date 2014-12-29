package lt.dariusl.library;

import android.animation.FloatArrayEvaluator;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import android.util.SparseArray;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Darius on 2014.12.28.
 */
public class Flags {
    //object flags
    public static final int OBJECT_NULL = 1 << 31;
    public static final int OBJECT_STATIC = 1 << 30;
    public static final int OBJECT_DYNAMIC = 1 << 29;

    //primitives
    public static final int TYPE_INT = 1 << 0;
    public static final int TYPE_LONG = 1 << 1;
    public static final int TYPE_BOOLEAN = 1 << 2;
    public static final int TYPE_BYTE = 1 << 3;
    public static final int TYPE_CHAR = 1 << 4;
    public static final int TYPE_SHORT = 1 << 5;
    public static final int TYPE_FLOAT = 1 << 6;
    public static final int TYPE_DOUBLE = 1 << 7;

    //Parcel implementation
    public static final int TYPE_STRING = 1 << 8;
    public static final int TYPE_CHAR_SEQUENCE = 1 << 9;
    public static final int TYPE_IINTERFACE = 1 << 10;
    public static final int TYPE_IBINDER = 1 << 11;

    //for future use
    public static final int TYPE_PRIMITIVE_ARRAY = 1 << 12;
    public static final int TYPE_OBJECT_ARRAY = 1 << 13;

    public static final int TYPE_LIST = 1 << 14;
    public static final int TYPE_MAP = 1 << 15;
    public static final int TYPE_SET = 1 << 16;

    public static final int TYPE_SPARSE_ARRAY = 1 << 17;
    public static final int TYPE_PARCELABLE = 1 << 18;
    public static final int TYPE_REFLECTION = 1 << 19;

    public static final int MASK_OBJECT = OBJECT_STATIC | OBJECT_DYNAMIC | OBJECT_NULL;
    public static final int MASK_PRIMITIVE = TYPE_INT | TYPE_LONG | TYPE_BOOLEAN | TYPE_BYTE | TYPE_CHAR | TYPE_SHORT | TYPE_FLOAT | TYPE_DOUBLE;

    public static boolean isObject(int flag){
        return (flag & MASK_OBJECT) != 0;
    }

    public static boolean isPrimitive(int flag){
        return (flag & MASK_PRIMITIVE) != 0;
    }

    public static int makeFlags(Object object, Field field){
        try {
            field.setAccessible(true);
            int flags = 0;
            Class<?> type = field.getType();

            if (type.isPrimitive()) {
                flags |= getTypeFlag(type);
            }else{
                Object value = field.get(object);
                if(value == null){
                    flags |= OBJECT_NULL;
                }else{
                    flags |= value.getClass().equals(type) ? OBJECT_STATIC : OBJECT_DYNAMIC;
                }

                if(type.isArray()){
                    Class<?> component = type.getComponentType();
                    flags |= component.isPrimitive() ? TYPE_PRIMITIVE_ARRAY : TYPE_OBJECT_ARRAY;
                }else {
                    if((flags & OBJECT_DYNAMIC) != 0){
                        flags |= getTypeFlag(value.getClass());
                    }else{
                        flags |= getTypeFlag(type);
                    }
                }
            }
            return flags;
        }catch (SecurityException | IllegalAccessException e){
            e.printStackTrace();
            throw new RuntimeException("Unable to read field " + field.getName(), e);
        }
    }

    public static int getTypeFlag(Class<?> type){
        int flag = 0;

        if(isInteger(type)){
            flag = TYPE_INT;
        }else if(isLong(type)){
            flag = TYPE_LONG;
        }else if(isBoolean(type)){
            flag = TYPE_BOOLEAN;
        }else if(isByte(type)){
            flag = TYPE_BYTE;
        }else if(isChar(type)){
            flag = TYPE_CHAR;
        }else if(isShort(type)){
            flag = TYPE_SHORT;
        }else if(isFloat(type)){
            flag = TYPE_FLOAT;
        }else if(isDouble(type)){
            flag = TYPE_DOUBLE;
        }else if(isString(type)){
            flag = TYPE_STRING;
        }else if (isCharSequence(type)) {
            flag = TYPE_CHAR_SEQUENCE;
        }else if(isSparseArray(type)){
            flag = TYPE_SPARSE_ARRAY;
        }else if(isList(type)){
            flag = TYPE_LIST;
        }else if(isMap(type)){
            flag = TYPE_MAP;
        }else if(isSet(type)){
            flag = TYPE_SET;
        }else if(isIBinder(type)){
            flag = TYPE_IBINDER;
        }else if(isIInterface(type)){
            flag = TYPE_IINTERFACE;
        }else if(isParcelable(type)){
            flag = TYPE_PARCELABLE;
        }else{
            flag = TYPE_REFLECTION;
        }
        return flag;
    }

    public static boolean isInteger(Class<?> cls){
        return cls == Integer.class || cls == int.class;
    }

    public static boolean isLong(Class<?> cls){
        return cls == Long.class || cls == long.class;
    }

    public static boolean isBoolean(Class<?> cls){
        return cls == Boolean.class || cls == boolean.class;
    }

    public static boolean isByte(Class<?> cls){
        return cls == Boolean.class || cls == boolean.class;
    }

    public static boolean isChar(Class<?> cls){
        return cls == Character.class || cls == char.class;
    }

    public static boolean isShort(Class<?> cls){
        return cls == Short.class || cls == short.class;
    }

    public static boolean isFloat(Class<?> cls){
        return cls == Float.class || cls == float.class;
    }

    public static boolean isDouble(Class<?> cls){
        return cls == Double.class || cls == double.class;
    }

    public static boolean isString(Class<?> cls){
        return cls == String.class;
    }

    public static boolean isList(Class<?> cls){
        return List.class.isAssignableFrom(cls);
    }

    public static boolean isSet(Class<?> cls){
        return Set.class.isAssignableFrom(cls);
    }

    public static boolean isMap(Class<?> cls){
        return Map.class.isAssignableFrom(cls);
    }

    public static boolean isSparseArray(Class<?> cls){
        return SparseArray.class.isAssignableFrom(cls);
    }

    public static boolean isCharSequence(Class<?> cls){
        return CharSequence.class.isAssignableFrom(cls);
    }

    public static boolean isIBinder(Class<?> cls){
        return IBinder.class.isAssignableFrom(cls);
    }

    public static boolean isIInterface(Class<?> cls){
        return IInterface.class.isAssignableFrom(cls);
    }

    public static boolean isParcelable(Class<?> cls){
        return Parcelable.class.isAssignableFrom(cls);
    }
}
