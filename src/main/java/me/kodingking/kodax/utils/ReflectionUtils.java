package me.kodingking.kodax.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {

    public static Method getMethod(Class<?> clazz, String[] methodNames, Class[] parameters) {
        for (String name : methodNames) {
            try {
                Method m = clazz.getDeclaredMethod(name, parameters);
                if (m != null) {
                    m.setAccessible(true);
                    return m;
                }
            } catch (NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String[] fieldNames) {
        for (String name : fieldNames) {
            try {
                Field f = clazz.getDeclaredField(name);
                if (f != null) {
                    f.setAccessible(true);
                    return f;
                }
            } catch (NoSuchFieldException ignored) {
            }
        }
        return null;
    }

    public static boolean isSubclassOf(Class<?> clazz, Class<?> superClass) {
        if (superClass.equals(Object.class)) {
            // Every class is an Object.
            return true;
        }
        if (clazz.equals(superClass)) {
            return true;
        } else {
            clazz = clazz.getSuperclass();
            // every class is Object, but superClass is below Object
            if (clazz == null || clazz.equals(Object.class)) {
                // we've reached the top of the hierarchy, but superClass couldn't be found.
                return false;
            }
            // try the next level up the hierarchy.
            return isSubclassOf(clazz, superClass);
        }
    }

}
