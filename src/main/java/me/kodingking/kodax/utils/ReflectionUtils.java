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
            } catch (NoSuchMethodException ignored) { }
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
            } catch (NoSuchFieldException ignored) { }
        }
        return null;
    }

}
