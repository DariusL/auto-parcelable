package lt.dariusl.autoparcelabletests.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Darius on 2015.01.03.
 */
public class StringLongParcelable extends StringLong implements Parcelable {

    public StringLongParcelable(String foo, long bar) {
        super(foo, bar);
    }

    private StringLongParcelable(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foo);
        dest.writeLong(bar);
    }

    public static Creator<StringLongParcelable> CREATOR = new Creator<StringLongParcelable>() {
        @Override
        public StringLongParcelable createFromParcel(Parcel source) {
            StringLongParcelable foo = new StringLongParcelable();
            foo.foo = source.readString();
            foo.bar = source.readLong();
            return foo;
        }

        @Override
        public StringLongParcelable[] newArray(int size) {
            return new StringLongParcelable[0];
        }
    };
}
