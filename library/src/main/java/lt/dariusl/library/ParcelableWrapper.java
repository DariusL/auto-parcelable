package lt.dariusl.library;

/**
 * Created by Darius on 2015.01.11.
 */
public class ParcelableWrapper<T> extends AutoParcelable {
    private T value;

    public ParcelableWrapper(T value){
        this.value = value;
    }

    private ParcelableWrapper(){}

    public T get(){
        return value;
    }

    public static final Creator<ParcelableWrapper> CREATOR = Creators.get(ParcelableWrapper.class);
}
