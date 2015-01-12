# auto-parcelable
A library to handle Android's [Parcelable](http://developer.android.com/reference/android/os/Parcelable.html) interface using reflection instead of hardcoded serialization. 

The idea is to handle serialization in the same automagical way of Gson and the like.

# Usage

There are 4 ways to use the library:

## ParcelableWrapper

Can be used to wrap any object and pass it as a `Parcelable`

```
Foo foo = new Foo();
bundle.putParcelable("a foo", new ParcelableWrapper<>(foo));

Foo bar =((ParcelableWrapper<Foo>)bundle.getParcelable("a foo")).get();
```

While this produces the least amount of code, it can't be easily replaced with other implementations of `Parcelable` and thus, is not recommended.

## AutoParcelable

```
public class Foo extends AutoParcelable{
    //fields, methods, whatever
    
    public static final Creator<Foo> CREATOR = Creators.get(Foo.class);
}
```

This produces a class that can be used a usual `Parcelable` class. This is the recommended usage.

## Implementing Parcelable

If you cannot extend `AutoParcelable`, the library can still be used by implementing `Parcelable`

```
public class Foo implements Parcelable{
    //fields, methods, whatever
        
    public static final Creator<Foo> CREATOR = Creators.get(Foo.class);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Parcelator.writeReflective(dest, this, Foo.class);
    }
}
```

Keep in mind that the second argument for `writeReflective` has to be `this`, otherwise _Fun_ might happen.

## Calling static methods

If you wish to write data to a parcel yourself.

```
Parcel parcel = Parcel.obtain();
Parcelator.writeToParcel(parcel, foo, Foo.class);

Parcelator.readFromParcel(parcel, Foo.class);
```

The type arguments to read and write must match, otherwise _Fun_ is bound to happen. If the type is unknown (if you're using generics, thank you, Java), passing Object.class also works.

# Supported types

* Primitives
* Boxed primitives
* String 
* CharSequence (not yet implemented)
* IInterface (not yet implemented)
* IBinder (not yet implemented)
* Serializable objects (not yet implemented)
* Parcelable objects
* Enums (not yet implemented)
* Arrays of the aforementioned
* Sets of the aforementioned (not yet implemented)
* Lists of the aforementioned (not yet implemented)
* Maps with keys and values of the aforementioned (not yet implemented)
* SparseArrays of the aforementioned (not yet implemented)

Generics are fully supported. I think. I need more tests.

I might add special support for new types as long as I don't run out of bits for flags. 7 bits left.

# Epilogue

Annotate fields that should be skipped with `@Ignore`

Nested types are supported, but the enclosing class reference is set to `null`. I believe Gson does the same. Keep this in mind if your nested types are not static.

Don't accidentally parcel the application context.

Circular references will produce a stack overflow.
