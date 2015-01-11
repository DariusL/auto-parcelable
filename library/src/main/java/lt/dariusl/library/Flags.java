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
public final class Flags {
    //object flags
    public static final int OBJECT_NULL = 1 << 31;
    public static final int OBJECT_STATIC = 1 << 30;
    public static final int OBJECT_DYNAMIC = 1 << 29;

    //primitives
    public static final int METHOD_INT = 1 << 0;
    public static final int METHOD_LONG = 1 << 1;
    public static final int METHOD_BOOLEAN = 1 << 2;
    public static final int METHOD_BYTE = 1 << 3;
    public static final int METHOD_CHAR = 1 << 4;
    public static final int METHOD_SHORT = 1 << 5;
    public static final int METHOD_FLOAT = 1 << 6;
    public static final int METHOD_DOUBLE = 1 << 7;

    //Parcel implementation
    public static final int METHOD_STRING = 1 << 8;
    public static final int METHOD_CHAR_SEQUENCE = 1 << 9;
    public static final int METHOD_IINTERFACE = 1 << 10;
    public static final int METHOD_IBINDER = 1 << 11;

    //for future use
    public static final int METHOD_PRIMITIVE_ARRAY = 1 << 12;
    public static final int METHOD_OBJECT_ARRAY = 1 << 13;

    public static final int METHOD_LIST = 1 << 14;
    public static final int METHOD_MAP = 1 << 15;
    public static final int METHOD_SET = 1 << 16;

    public static final int METHOD_SPARSE_ARRAY = 1 << 17;
    public static final int METHOD_PARCELABLE = 1 << 18;
    public static final int METHOD_REFLECTION = 1 << 19;

    public static final int MASK_IS_OBJECT = OBJECT_STATIC | OBJECT_DYNAMIC | OBJECT_NULL;
    public static final int MASK_METHOD_PRIMITIVE = METHOD_INT | METHOD_LONG | METHOD_BOOLEAN | METHOD_BYTE | METHOD_CHAR | METHOD_SHORT | METHOD_FLOAT | METHOD_DOUBLE;
    public static final int MASK_METHOD_NON_PRIMITIVE = ~(MASK_IS_OBJECT | MASK_METHOD_PRIMITIVE);

    public static boolean isObject(int flags){
        return (flags & MASK_IS_OBJECT) != 0;
    }

    public static boolean isPrimitive(int flags){
        return (flags & MASK_METHOD_PRIMITIVE) != 0;
    }

    public static boolean isNull(int flags){
        return (flags & OBJECT_NULL) != 0;
    }

    public static boolean isDynamic(int flags){
        return (flags & OBJECT_DYNAMIC) != 0;
    }

    public static boolean isPrimitiveArray(int flags){
        return (flags & METHOD_PRIMITIVE_ARRAY) != 0;
    }

    public static boolean isObjectArray(int flags){
        return (flags & METHOD_OBJECT_ARRAY) != 0;
    }

    public static int makeFlags(Class<?> type, Object object){
        int flags = 0;

        if (type.isPrimitive()) {
            flags |= getTypeFlag(type);
        }else{
            if(object == null){
                flags |= OBJECT_NULL;
            }else{
                flags |= object.getClass().equals(type) ? OBJECT_STATIC : OBJECT_DYNAMIC;
            }

            if(type.isArray()){
                Class<?> component = type.getComponentType();
                flags |= component.isPrimitive() ? METHOD_PRIMITIVE_ARRAY : METHOD_OBJECT_ARRAY;
            }else {
                if((flags & OBJECT_DYNAMIC) != 0){
                    flags |= getTypeFlag(object.getClass());
                }else{
                    flags |= getTypeFlag(type);
                }
            }
        }
        return flags;
    }

    public static int getTypeFlag(Class<?> type){
        int flag;

        if(isInteger(type)){
            flag = METHOD_INT;
        }else if(isLong(type)){
            flag = METHOD_LONG;
        }else if(isBoolean(type)){
            flag = METHOD_BOOLEAN;
        }else if(isByte(type)){
            flag = METHOD_BYTE;
        }else if(isChar(type)){
            flag = METHOD_CHAR;
        }else if(isShort(type)){
            flag = METHOD_SHORT;
        }else if(isFloat(type)){
            flag = METHOD_FLOAT;
        }else if(isDouble(type)){
            flag = METHOD_DOUBLE;
        }else if(isString(type)){
            flag = METHOD_STRING;
        }else if (isCharSequence(type)) {
            flag = METHOD_CHAR_SEQUENCE;
        }else if(isSparseArray(type)){
            flag = METHOD_SPARSE_ARRAY;
        }else if(isList(type)){
            flag = METHOD_LIST;
        }else if(isMap(type)){
            flag = METHOD_MAP;
        }else if(isSet(type)){
            flag = METHOD_SET;
        }else if(isIBinder(type)){
            flag = METHOD_IBINDER;
        }else if(isIInterface(type)){
            flag = METHOD_IINTERFACE;
        }else if(isParcelable(type)){
            flag = METHOD_PARCELABLE;
        }else{
            flag = METHOD_REFLECTION;
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
