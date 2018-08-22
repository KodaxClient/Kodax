package me.kodingking.kodax.mods.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class AbstractCoreMod {

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Meta {
        String name();

        String version();

        String[] authors();
    }

    private final String name = getClass().getAnnotation(Meta.class).name();
    private final String version = getClass().getAnnotation(Meta.class).version();
    private final String[] authors = getClass().getAnnotation(Meta.class).authors();

    public AbstractCoreMod() {
        onLoad();
    }

    public abstract void onLoad();
    public abstract void onClose();

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }
}
