package me.kodingking.kodax.event;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private static Map<Object, Map<Class<? extends Event>, List<Method>>> registeredClasses = new HashMap<>();

    private static boolean isEventMethod(Method method) {
        if (method.getParameterCount() <= 0)
            return false;

        Class<?> paramType = method.getParameters()[0].getType();

        if (paramType.isPrimitive())
            return false;

        return method.isAnnotationPresent(EventListener.class) && method.getParameterCount() == 1 && ReflectionUtils.isSubclassOf(paramType, Event.class);
    }

    public static void register(Object o) {
        Class<?> clazz = o.getClass();
        Map<Class<? extends Event>, List<Method>> map = new HashMap<>();

        for (Method m : clazz.getMethods()) {
            try {
                if (!isEventMethod(m))
                    continue;

                Class<? extends Event> eventClass = (Class<? extends Event>) m.getParameterTypes()[0];

                map.putIfAbsent(eventClass, new ArrayList<>());
                List<Method> methods = map.get(eventClass);

                methods.add(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        registeredClasses.put(o, map);
    }

    public static void unregister(Object o) {
        registeredClasses.remove(o);
    }

    public static void call(Event event) {
        for (Map.Entry<Object, Map<Class<? extends Event>, List<Method>>> entry : registeredClasses.entrySet()) {
            Map<Class<? extends Event>, List<Method>> map = entry.getValue();
            Object o = entry.getKey();

            if (!map.containsKey(event.getClass()))
                continue;

            List<Method> list = map.get(event.getClass());

            for (Method m : list) {
                try {
                    m.invoke(o, event);
                } catch (InvocationTargetException e) {
                    Kodax.INSTANCE.getLogger().error("Error executing event.");
                    e.getCause().printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
