package lt.dariusl.library;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Darius on 2014.12.29.
 */
public class Utils {

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
}
