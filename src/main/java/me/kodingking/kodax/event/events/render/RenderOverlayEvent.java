package me.kodingking.kodax.event.events.render;

import me.kodingking.kodax.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class RenderOverlayEvent extends Event {

    public enum Type {
        PRE,
        POST
    }

    private ScaledResolution scaledResolution;
    private float partialTicks;
    private Type type;

    public RenderOverlayEvent(ScaledResolution scaledResolution, float partialTicks, Type type) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
        this.type = type;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public Type getType() {
        return type;
    }
}
