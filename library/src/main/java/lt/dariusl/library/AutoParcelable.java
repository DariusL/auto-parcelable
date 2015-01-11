package lt.dariusl.library;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;

/**
 * Created by Darius on 2015.01.09.
 */
public abstract class AutoParcelable implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Parcelator.writeReflective(dest, this, (Class<AutoParcelable>) getClass());
    }

    public static final class ReflectCreator<T extends Parcelable> implements Parcelable.Creator<T>{
        private Class<T> cls;

        public ReflectCreator(Class<T> cls){
            this.cls = cls;
        }

        @Override
        public T createFromParcel(Parcel source) {
            return Parcelator.readReflective(source, cls);
        }

        @Override
        public T[] newArray(int size) {
            return (T[]) Array.newInstance(cls, size);
        }
    }
}
