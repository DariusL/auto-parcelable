package lt.dariusl.autoparcelabletests.data;

/**
 * Created by Darius on 2015.01.12.
 */
public class ReflectedField {
    private StringLong field;
    private int a;

    public ReflectedField(StringLong field, int a){
        this.field = field;
        this.a = a;
    }

    protected ReflectedField(){}
}
