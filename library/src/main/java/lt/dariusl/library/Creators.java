package lt.dariusl.library;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darius on 2015.01.09.
 */
public final class Creators {

    private static final Map<Class<?>, Parcelable.Creator<?>> creators = new HashMap<>();

    public static synchronized <T extends Parcelable> Parcelable.Creator<T> get(Class<T> cls){
        if(!creators.containsKey(cls)){
            creators.put(cls, new AutoParcelable.ReflectCreator<T>(cls));
        }
        return (Parcelable.Creator<T>) creators.get(cls);
    }
}
