package lt.dariusl.autoparcelabletests.data;

import lt.dariusl.autoparcelable.AutoParcelable;
import lt.dariusl.autoparcelable.Creators;

/**
 * Created by Darius on 2015.01.12.
 */
public class ParcelableField extends AutoParcelable {
    private StringLongParcelable field;
    int a;

    private ParcelableField(){}

    public ParcelableField(StringLongParcelable field, int a){
        this.field = field;
        this.a = a;
    }

    public static final Creator<ParcelableField> CREATOR = Creators.get(ParcelableField.class);
}
