package me.kodingking.kodax.event.events.gui;

import me.kodingking.kodax.event.CancellableEvent;
import net.minecraft.client.gui.GuiScreen;

public class GuiOpenEvent extends CancellableEvent {

    private GuiScreen guiScreen;

    public GuiOpenEvent(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}
