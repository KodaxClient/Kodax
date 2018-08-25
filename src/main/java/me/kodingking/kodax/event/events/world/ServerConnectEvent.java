package me.kodingking.kodax.event.events.world;

import me.kodingking.kodax.event.Event;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;

public class ServerConnectEvent extends Event {

    private GuiScreen guiScreen;
    private ServerData serverData;

    public ServerConnectEvent(GuiScreen guiScreen, ServerData serverData) {
        this.guiScreen = guiScreen;
        this.serverData = serverData;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }

    public ServerData getServerData() {
        return serverData;
    }
}
