package me.kodingking.kodax.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private List<Object> registeredObjects = new ArrayList<>();
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private File configFile;

    public ConfigManager(File configFile) {
        this.configFile = configFile;
    }

    public void register(Object obj) {
        registeredObjects.add(obj);
    }

    public void save() {
        JsonObject saveObject = new JsonObject();
        for (Object o : registeredObjects) {
            Class<?> clazz = o.getClass();
            JsonObject classObject = new JsonObject();
            for (Field f : clazz.getDeclaredFields()) {
                if (f.getAnnotation(SaveVal.class) != null) {
                    try {
                        f.setAccessible(true);
                        classObject.add(f.getName(), gson.toJsonTree(f.get(o)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            saveObject.add(clazz.getName(), classObject);
        }
        try {
            if (!configFile.exists())
                configFile.createNewFile();
            IOUtils.write(gson.toJson(saveObject), new FileOutputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            if (!configFile.exists())
                configFile.createNewFile();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
            JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
            if (jsonObject == null)
                return;
            for (Object o : registeredObjects) {
                try {
                    Class<?> clazz = o.getClass();
                    if (!jsonObject.has(clazz.getName()))
                        continue;
                    JsonObject classObject = jsonObject.getAsJsonObject(clazz.getName());
                    for (Field f : clazz.getDeclaredFields()) {
                        if (f.isAnnotationPresent(SaveVal.class) && classObject.has(f.getName())) {
                            try {
                                f.setAccessible(true);
                                f.set(o, gson.fromJson(classObject.get(f.getName()), f.getType()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
