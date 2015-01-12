package lt.dariusl.autoparcelabletests.data;

/**
 * Created by Darius on 2015.01.12.
 */
public class Outer {
    private long bar;
    private Inner inner;

    public Outer(String foo, long bar){
        inner = new Inner();
        inner.foo = foo;
        this.bar = bar;
    }

    private Outer(){}

    public class Inner{
        private String foo;
    }
}
