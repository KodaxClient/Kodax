package me.kodingking.kodax.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiIngameMultiplayer extends GuiMultiplayer {

    public GuiIngameMultiplayer(GuiScreen parentScreen) {
        super(parentScreen);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1 || button.id == 4)
            disconnect();
        super.actionPerformed(button);
    }

    @Override
    public void connectToSelected() {
        disconnect();
        super.connectToSelected();
    }

    private void disconnect() {
        if (Minecraft.getMinecraft().theWorld != null) {
            Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
            Minecraft.getMinecraft().loadWorld(null);
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }
}