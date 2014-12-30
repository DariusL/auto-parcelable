package lt.dariusl.library.test;

import android.test.InstrumentationTestCase;

import java.lang.reflect.Array;

import lt.dariusl.library.Primitives;
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

    public void testPrimitiveArrayFromClassName() throws Exception{
        Object foo = new int[0];
        String name = foo.getClass().getComponentType().getName();
        Class<?> cls = Primitives.getPrimitiveType(name);
        Object bar = Array.newInstance(cls, 0);
        cls.getName();
    }

    public void testAutoBox() throws Exception{
        int foo = 5;
        Object bar = foo;
        foo++;
    }
}
