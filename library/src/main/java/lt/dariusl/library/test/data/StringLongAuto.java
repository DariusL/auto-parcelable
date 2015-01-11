package lt.dariusl.library.test.data;

import lt.dariusl.library.AutoParcelable;
import lt.dariusl.library.Creators;

/**
 * Created by Darius on 2015.01.09.
 */
public class StringLongAuto extends AutoParcelable{
    protected String foo;
    protected long bar;

    private StringLongAuto(){}

    public StringLongAuto(String foo, long bar){
        this.foo = foo;
        this.bar = bar;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  StringLongAuto)){
            return false;
        }

        StringLongAuto that = (StringLongAuto) o;

        return foo.equals(that.foo) && bar == that.bar;
    }

    public static Creator<StringLongAuto> CREATOR = Creators.get(StringLongAuto.class);
}
