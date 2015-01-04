package lt.dariusl.library.test.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Darius on 2015.01.03.
 */
public class StringLong {

    protected String foo;
    protected long bar;

    protected StringLong(){}

    public StringLong(String foo, long bar){
        this.foo = foo;
        this.bar = bar;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  StringLong)){
            return false;
        }

        StringLong that = (StringLong) o;

        return foo.equals(that.foo) && bar == that.bar;
    }
}