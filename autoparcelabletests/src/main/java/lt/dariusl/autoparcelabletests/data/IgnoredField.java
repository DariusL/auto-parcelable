package lt.dariusl.autoparcelabletests.data;

import lt.dariusl.autoparcelable.Ignore;

/**
 * Created by Darius on 2015.01.12.
 */
public class IgnoredField {
    private String foo;
    private String bar;

    @Ignore
    private UnParcelable baz;

    private IgnoredField(){}

    public IgnoredField(String foo, String bar){
        this.foo = foo;
        this.bar = bar;
    }
}
