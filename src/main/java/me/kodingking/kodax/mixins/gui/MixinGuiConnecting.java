package me.kodingking.kodax.mixins.gui;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.event.events.world.ServerConnectEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {

  @Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("RETURN"))
  private void postConnectToServer(GuiScreen guiMultiplayer, Minecraft minecraft,
      ServerData serverEntry, CallbackInfo callbackInfo) {
    ServerConnectEvent serverConnectEvent = new ServerConnectEvent(guiMultiplayer, serverEntry);
    EventBus.call(serverConnectEvent);
  }

  @Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/Minecraft;Ljava/lang/String;I)V", at = @At("RETURN"))
  private void postConnectToServerAlt(GuiScreen guiMultiplayer, Minecraft minecraft,
      String hostname, int port, CallbackInfo callbackInfo) {
    ServerConnectEvent serverConnectEvent = new ServerConnectEvent(guiMultiplayer,
        new ServerData("Server", hostname + ":" + port, false));
    EventBus.call(serverConnectEvent);
  }

}
