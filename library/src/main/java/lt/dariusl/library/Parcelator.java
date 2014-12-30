package lt.dariusl.library;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.text.TextUtils;

/**
 * Created by Darius on 2014.12.29.
 */
public class Parcelator {
    public static void writeToParcel(Parcel parcel, Object value, Class<?> cls){
        int flags = Flags.makeFlags(cls, value);

        if(Flags.isPrimitive(flags)){
            if(Flags.isObject(flags)){
                writeBoxedPrimitive(parcel, value, flags);
            }else{
                writePrimitive(parcel, value, flags);
            }
        }else {
            writeObject(parcel, value, flags);
        }
    }

    private static void writePrimitive(Parcel parcel, Object value, int flags){
        switch (flags & Flags.MASK_METHOD_PRIMITIVE){
            case Flags.METHOD_INT:
                parcel.writeInt((Integer)value);
                break;
            case Flags.METHOD_LONG:
                parcel.writeLong((Long)value);
                break;
            case Flags.METHOD_BOOLEAN:
                parcel.writeInt((Boolean) value ? 1 : 0);
                break;
            case Flags.METHOD_BYTE:
                parcel.writeInt((Byte) value);
                break;
            case Flags.METHOD_CHAR:
                parcel.writeInt((Character) value);
                break;
            case Flags.METHOD_SHORT:
                parcel.writeInt((Short) value);
                break;
            case Flags.METHOD_FLOAT:
                parcel.writeFloat((Float) value);
                break;
            case Flags.METHOD_DOUBLE:
                parcel.writeDouble((Double)value);
                break;
            default:
                throw new IllegalArgumentException("writePrimitive flags argument was not a primitive flag");
        }
    }

    private static void writeObject(Parcel parcel, Object value, int flags){
        if(!Flags.isNull(flags)){
            switch (flags & Flags.MASK_METHOD_NON_PRIMITIVE){
                case Flags.METHOD_STRING:
                    writeString(parcel, (String) value, flags);
                    break;
                case Flags.METHOD_PRIMITIVE_ARRAY:
                    writePrimitiveArray(parcel, value, flags);
                    break;
                case Flags.METHOD_OBJECT_ARRAY:
                    writeObjectArray(parcel, value, flags);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private static void writeBoxedPrimitive(Parcel parcel, Object value, int flags){
        writeObjectData(parcel, value, flags);
        if(!Flags.isNull(flags)){
            writePrimitive(parcel, value, flags);
        }
    }

    private static void writeString(Parcel parcel, String value, int flags){
        writeObjectData(parcel, value, flags);
        parcel.writeString(value);
    }

    //Types cannot be parceled statically, because that would defeat the purpose
    private static void writeObjectType(Parcel parcel, Class<?> value){
        parcel.writeString(value.getName());
    }

    //writes the component typename instead
    private static void writeArrayType(Parcel parcel, Class<?> value){
        parcel.writeString(value.getComponentType().getName());
    }

    /**
     * writes the header for all non-primitives except arrays, because arrays are 'special'
     */
    private static void writeObjectData(Parcel parcel, Object value, int flags){
        parcel.writeInt(flags);
        if((flags & Flags.OBJECT_DYNAMIC) != 0){
            writeObjectType(parcel, value.getClass());
        }
    }

    private static void writeArrayData(Parcel parcel, Object value, int flags){
        parcel.writeInt(flags);
        if((flags & Flags.OBJECT_DYNAMIC) != 0){
            writeArrayType(parcel, value.getClass());
        }
    }

    private static void writePrimitiveArray(Parcel parcel, Object value, int flags){
        writeArrayData(parcel, value, flags);
        if((flags & Flags.OBJECT_NULL) == 0) {
            switch (flags & Flags.MASK_METHOD_PRIMITIVE) {
                case Flags.METHOD_INT:
                    parcel.writeIntArray((int[]) value);
                    break;
                case Flags.METHOD_LONG:
                    parcel.writeLongArray((long[]) value);
                    break;
                case Flags.METHOD_BOOLEAN:
                    parcel.writeBooleanArray((boolean[]) value);
                    break;
                case Flags.METHOD_BYTE:
                    parcel.writeByteArray((byte[]) value);
                    break;
                case Flags.METHOD_CHAR:
                    parcel.writeCharArray((char[]) value);
                    break;
                case Flags.METHOD_SHORT:
                    writeShortArray(parcel, (short[]) value);
                    break;
                case Flags.METHOD_FLOAT:
                    parcel.writeFloatArray((float[]) value);
                    break;
                case Flags.METHOD_DOUBLE:
                    parcel.writeDoubleArray((double[]) value);
                    break;
                default:
                    throw new IllegalArgumentException("writePrimitive flags argument was not a primitive flag");
            }
        }
    }

    private static void writeObjectArray(Parcel parcel, Object value, int flags){
        writeArrayData(parcel, value, flags);
        if((flags & Flags.OBJECT_NULL) == 0){
            Object[] array = (Object[]) value;
            Class<?> component = value.getClass().getComponentType();
            parcel.writeInt(array.length);
            for(Object obj : array){
                int objFlags = Flags.makeFlags(component, obj);
                writeObject(parcel, obj, objFlags);
            }
        }
    }

    private static void writeShortArray(Parcel parcel, short[] value){
        int n = value.length;
        parcel.writeInt(n);
        for(short s : value){
            parcel.writeInt(s);
        }
    }
}
