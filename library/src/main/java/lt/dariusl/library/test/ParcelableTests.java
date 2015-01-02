package lt.dariusl.library.test;

import android.os.Parcel;
import android.os.Parcelable;
import android.test.InstrumentationTestCase;

/**
 * Created by Darius on 2015.01.02.
 */
public class ParcelableTests extends InstrumentationTestCase{

    public static class StringLong implements Parcelable{

        private String foo;
        private long bar;

        private StringLong(){}

        public StringLong(String foo, long bar){
            this.foo = foo;
            this.bar = bar;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(foo);
            dest.writeLong(bar);
        }

        public static Creator<StringLong> CREATOR = new Creator<StringLong>() {
            @Override
            public StringLong createFromParcel(Parcel source) {
                StringLong foo = new StringLong();
                foo.foo = source.readString();
                foo.bar = source.readLong();
                return foo;
            }

            @Override
            public StringLong[] newArray(int size) {
                return new StringLong[0];
            }
        };

        @Override
        public boolean equals(Object o) {
            if(!(o instanceof  StringLong)){
                return false;
            }

            StringLong that = (StringLong) o;

            return foo.equals(that.foo) && bar == that.bar;
        }
    }

    public void testStringLong(){
        StringLong value = new StringLong("fsafasfasf", 86219L);
        TestUtils.writeReadAssert(value, StringLong.class);
    }

    public void testStringLongAsParcelable(){
        StringLong value = new StringLong("fsafasfasf", 86219L);
        TestUtils.writeReadAssert(value, Parcelable.class);
    }

    public void testStringLongAsObject(){
        StringLong value = new StringLong("fsafasfasf", 86219L);
        TestUtils.writeReadAssert(value, Object.class);
    }
}
