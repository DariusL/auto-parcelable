package lt.dariusl.autoparcelabletests.data;

/**
 * Created by Darius on 2015.01.12.
 */
public class UnParcelable {
    //the lack of a parameter-less ctor makes this impossible to parcel
    public UnParcelable(long a){}
}
