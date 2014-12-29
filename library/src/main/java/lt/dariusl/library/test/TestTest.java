package lt.dariusl.library.test;

import android.test.InstrumentationTestCase;

import lt.dariusl.library.Utils;

/**
 * Created by Darius on 2014.12.29.
 */
public class TestTest extends InstrumentationTestCase {

    public class Foo{
        private String bar;
    }

    public void testTest() throws NoSuchFieldException{
        Foo f = new Foo();
        Utils.classNames(Integer.class, Integer.TYPE,
                Boolean.class, Boolean.TYPE,
                Long.class, Long.TYPE,
                Character.class, Character.TYPE,
                Byte.class, Byte.TYPE,
                Short.class, Short.TYPE,
                Float.class, Float.TYPE,
                Double.class, Double.TYPE);
    }
}
