package me.kodingking.kodax.events.world;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.multiplayer.WorldClient;

public class WorldChangeEvent implements Event {

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
