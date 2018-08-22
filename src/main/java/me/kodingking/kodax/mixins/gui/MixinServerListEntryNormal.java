package me.kodingking.kodax.mixins.gui;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.manifests.ClientManifest.PinnedServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerListEntryNormal.class)
public abstract class MixinServerListEntryNormal {

  @Shadow public abstract ServerData getServerData();

  @Inject(method = "drawEntry", at = @At("RETURN"))
  private void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo callbackInfo) {
    for (PinnedServer pinnedServer : Kodax.FULL_MANIFEST.getPinnedServers()) {
      if (getServerData() != null && getServerData().serverName
          .equalsIgnoreCase(pinnedServer.getName()) && getServerData().serverIP
          .equalsIgnoreCase(pinnedServer.getIp())) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/icons/pin.png"));
        Gui.drawModalRectWithCustomSizedTexture(x, y + 1, 0, 0, 10, 10, 10, 10);
      }
    }
  }

}
