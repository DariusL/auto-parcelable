package lt.dariusl.library;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darius on 2014.12.30.
 */
public class Primitives {
    private static final Map<String, Class<?>> types;

    static {
        types = new HashMap<>();
        add(int.class);
        add(long.class);
        add(boolean.class);
        add(byte.class);
        add(char.class);
        add(short.class);
        add(float.class);
        add(double.class);
    }

    private static void add(Class<?> cls){
        types.put(cls.getName(), cls);
    }

    public static Class<?> getPrimitiveType(String name){
        return types.get(name);
    }
}
