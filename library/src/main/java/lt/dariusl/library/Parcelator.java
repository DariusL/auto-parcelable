package lt.dariusl.library;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.FrameLayout;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * Created by Darius on 2014.12.29.
 */
public class Parcelator {

    public static <T> void writeToParcel(Parcel parcel, T value, Class<T> cls){
        int flags = Flags.makeFlags(cls, value);

        if(!Flags.isObject(flags)){
            writePrimitive(parcel, value, flags);
        }else {
            writeObject(parcel, value, cls, flags);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readFromParcel(Parcel parcel, Class<T> cls){
        if(cls.isPrimitive()){
            return (T) readPrimitive(parcel, cls);
        }else{
            return (T) readObject(parcel, cls);
        }
    }

    private static void writeBoxedPrimitive(Parcel parcel, Object value, int flags){
        parcel.writeInt(flags);
        if(!Flags.isNull(flags)){
            writePrimitive(parcel, value, flags);
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

    private static Object readPrimitive(Parcel parcel, int flags){
        switch (flags & Flags.MASK_METHOD_PRIMITIVE) {
            case Flags.METHOD_INT:
                return parcel.readInt();
            case Flags.METHOD_LONG:
                return parcel.readLong();
            case Flags.METHOD_BOOLEAN:
                return parcel.readInt() == 1;
            case Flags.METHOD_BYTE:
                return parcel.readByte();
            case Flags.METHOD_CHAR:
                return (char) parcel.readInt();
            case Flags.METHOD_SHORT:
                return (short) parcel.readInt();
            case Flags.METHOD_FLOAT:
                return parcel.readFloat();
            case Flags.METHOD_DOUBLE:
                return parcel.readDouble();
            default:
                throw new IllegalArgumentException("writePrimitive flags argument was not a primitive flag");
        }
    }

    private static Object readPrimitive(Parcel parcel, Class<?> cls){
        int flags = Flags.makeFlags(cls, null);
        return readPrimitive(parcel, flags);
    }

    private static void writeObject(Parcel parcel, Object value, Class<?> cls, int flags){
        if(Flags.isPrimitive(flags)){
            writeBoxedPrimitive(parcel, value, flags);
        }else {
            switch (flags & Flags.MASK_METHOD_NON_PRIMITIVE) {
                case Flags.METHOD_STRING:
                    writeString(parcel, (String) value, flags);
                    break;
                case Flags.METHOD_PRIMITIVE_ARRAY:
                    writePrimitiveArray(parcel, value, flags);
                    break;
                case Flags.METHOD_OBJECT_ARRAY:
                    writeObjectArray(parcel, value, cls, flags);
                    break;
                case Flags.METHOD_PARCELABLE:
                    writeParcelable(parcel, (Parcelable) value, flags);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private static Object readObject(Parcel parcel, Class<?> cls){
        int flags = parcel.readInt();
        if(!Flags.isNull(flags)) {
            if (Flags.isPrimitive(flags)) {
                return readPrimitive(parcel, flags);
            } else {
                switch (flags & Flags.MASK_METHOD_NON_PRIMITIVE) {
                    case Flags.METHOD_STRING:
                        return readString(parcel, flags);
                    case Flags.METHOD_PRIMITIVE_ARRAY:
                        return readPrimitiveArray(parcel, cls, flags);
                    case Flags.METHOD_OBJECT_ARRAY:
                        return readObjectArray(parcel, cls, flags);
                    case Flags.METHOD_PARCELABLE:
                        return readParcelable(parcel, cls, flags);
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }else{
            return null;
        }
    }

    private static void writeParcelable(Parcel parcel, Parcelable value, int flags){
        writeObjectData(parcel, value, flags);
        if(value != null){
            parcel.writeParcelable(value, 0);
        }
    }

    private static Parcelable readParcelable(Parcel parcel, Class<?> cls, int flags){
        if(Flags.isDynamic(flags)){
            try {
                cls = Class.forName(parcel.readString());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Unable to create class from parceled name", e);
            }
        }

        return parcel.readParcelable(cls.getClassLoader());
    }

    private static void writeString(Parcel parcel, String value, int flags){
        parcel.writeInt(flags);
        if(value != null) {
            parcel.writeString(value);
        }
    }

    private static String readString(Parcel parcel, int flags){
        return parcel.readString();
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
            int itemFlags = Flags.makeFlags(value.getClass().getComponentType(), null);
            switch (itemFlags & Flags.MASK_METHOD_PRIMITIVE) {
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

    private static Object readPrimitiveArray(Parcel parcel, Class<?> cls, int flags){
        Class<?> component;
        if(Flags.isDynamic(flags)){
            component = Primitives.getPrimitiveType(parcel.readString());
        }else{
            component = cls.getComponentType();
        }
        int itemFlags = Flags.makeFlags(component, null);

        switch (itemFlags & Flags.MASK_METHOD_PRIMITIVE) {
            case Flags.METHOD_INT:
                return parcel.createIntArray();
            case Flags.METHOD_LONG:
                return parcel.createLongArray();
            case Flags.METHOD_BOOLEAN:
                return parcel.createBooleanArray();
            case Flags.METHOD_BYTE:
                return parcel.createByteArray();
            case Flags.METHOD_CHAR:
                return parcel.createCharArray();
            case Flags.METHOD_SHORT:
                return createShortArray(parcel);
            case Flags.METHOD_FLOAT:
                return parcel.createFloatArray();
            case Flags.METHOD_DOUBLE:
                return parcel.createDoubleArray();
            default:
                throw new IllegalArgumentException("writePrimitive flags argument was not a primitive flag");
        }
    }

    private static void writeObjectArray(Parcel parcel, Object value, Class<?> cls, int flags){
        writeArrayData(parcel, value, flags);
        if(value != null){
            Class<?> component = value.getClass().getComponentType();
            Object[] array = (Object[]) value;
            parcel.writeInt(array.length);
            for(Object obj : array){
                int objFlags = Flags.makeFlags(component, obj);
                writeObject(parcel, obj, component, objFlags);
            }
        }
    }

    private static Object readObjectArray(Parcel parcel, Class<?> cls, int flags){
        Class<?> component;
        if(Flags.isDynamic(flags)){
            try {
                component = Class.forName(parcel.readString());
            }catch (ClassNotFoundException e){
                throw new RuntimeException("Unable to create array component class", e);
            }
        }else{
            component = cls.getComponentType();
        }

        int n = parcel.readInt();
        Object[] ret = (Object[]) Array.newInstance(component, n);
        for(int i = 0; i < n; i++){
            ret[i] = readObject(parcel, component);
        }
        return ret;
    }

    private static void writeShortArray(Parcel parcel, short[] value){
        int n = value.length;
        parcel.writeInt(n);
        for(short s : value){
            parcel.writeInt(s);
        }
    }

    private static short[] createShortArray(Parcel parcel){
        int n = parcel.readInt();
        short[] ret = new short[n];
        for(int i = 0; i < n; i++){
            ret[i] = (short) parcel.readInt();
        }
        return ret;
    }
}
