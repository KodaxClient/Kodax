package me.kodingking.kodax.events.gui;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.client.gui.GuiScreen;

public class GuiOpenEvent extends EventCancellable {

    private GuiScreen guiScreen;

    public GuiOpenEvent(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }
}
