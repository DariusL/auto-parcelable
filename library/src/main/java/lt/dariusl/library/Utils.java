package lt.dariusl.library;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Darius on 2014.12.29.
 */
public final class Utils {

    public static void classNames(Class<?> ...classes){
        String[] names = new String[classes.length];
        for(int i = 0; i < classes.length; i++){
            names[i] = classes[i].getName();
        }
        classes.hashCode();
    }

    public static Set<Class<?>> getInheritance(Class<?> cls){
        Set<Class<?>> set = new HashSet<>();
        getInheritance(cls, set);
        return set;
    }

    private static void getInheritance(Class<?> cls, Set<Class<?>> set){
        set.add(cls);
        Class<?> superclass = cls.getSuperclass();
        Class<?>[] interfaces = cls.getInterfaces();
        if(superclass != null){
            getInheritance(superclass, set);
        }
        for (Class<?> i : interfaces){
            getInheritance(i, set);
        }
    }

    public static List<Field> getAllFields(Class<?> cls){
        List<Field> fields = new ArrayList<>();
        getAllFields(cls, fields);
        return fields;
    }

    private static void getAllFields(Class<?> cls, List<Field> fields){
        Collections.addAll(fields, cls.getDeclaredFields());
        Class<?> superclass = cls.getSuperclass();
        if(superclass != null){
            getAllFields(superclass, fields);
        }
    }

    public static <T> boolean equals(T left, T right, Class<?> cls){
        if(Modifier.isFinal(cls.getModifiers())){
            return left.equals(right);
        }

        try {
            for (Field field : cls.getDeclaredFields()) {
                if(!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    Object l = field.get(left);
                    Object r = field.get(right);
                    if(l != null && r != null){
                        if(!equals(l, r, l.getClass())){
                            return false;
                        }
                    }else{
                        if(!(l == null && r == null)){
                            return false;
                        }
                    }
                }
            }
            return true;
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }
}
