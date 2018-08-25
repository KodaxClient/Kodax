package me.kodingking.kodax.event.events.world;

import me.kodingking.kodax.event.Event;
import net.minecraft.client.multiplayer.WorldClient;

public class WorldChangeEvent extends Event {

    public enum Type {
        PRE,
        POST
    }

    private WorldClient world;
    private String message;
    private Type type;

    public WorldChangeEvent(WorldClient world, String message, Type type) {
        this.world = world;
        this.message = message;
        this.type = type;
    }

    public WorldClient getWorld() {
        return world;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        return type;
    }
}
