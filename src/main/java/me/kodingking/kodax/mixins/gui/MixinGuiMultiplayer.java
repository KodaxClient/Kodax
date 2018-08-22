package me.kodingking.kodax.mixins.gui;

import java.lang.reflect.Field;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.manifests.ClientManifest.PinnedServer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer {

  @Shadow
  private GuiButton btnDeleteServer;

  @Shadow
  private ServerSelectionList serverListSelector;

  @Shadow
  private ServerList savedServerList;

  @Shadow
  private GuiButton btnEditServer;

  @Shadow
  protected abstract void refreshServerList();

  @Inject(method = "updateScreen", at = @At("HEAD"))
  private void updateScreen(CallbackInfo callbackInfo) {
    try {
      Field selectedIndexField = serverListSelector.getClass()
          .getDeclaredField("selectedSlotIndex");
      selectedIndexField.setAccessible(true);
      int selectedIndex = selectedIndexField.getInt(serverListSelector);

      if (selectedIndex == -1) {
        return;
      }

      ServerData selectedServerData = savedServerList
          .getServerData(selectedIndex);

      for (PinnedServer pinnedServer : Kodax.FULL_MANIFEST.getPinnedServers()) {
        if (selectedServerData != null && selectedServerData.serverName
            .equalsIgnoreCase(pinnedServer.getName()) && selectedServerData.serverIP
            .equalsIgnoreCase(pinnedServer.getIp())) {
          btnDeleteServer.enabled = false;
          btnEditServer.enabled = false;
          return;
        }
      }

      btnDeleteServer.enabled = true;
      btnEditServer.enabled = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Inject(method = "mouseClicked", at = @At("RETURN"))
  private void mouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo callbackInfo) {
    for (int i = 0; i < Kodax.FULL_MANIFEST.getPinnedServers().size(); i++) {
      PinnedServer pinnedServer = Kodax.FULL_MANIFEST.getPinnedServers().get(i);

      if (serverListSelector.getListEntry(i) == null || !(serverListSelector
          .getListEntry(i) instanceof ServerListEntryNormal)) {
        continue;
      }

      if (!((ServerListEntryNormal) serverListSelector.getListEntry(i)).getServerData().serverIP
          .equals(pinnedServer.getIp()) || !((ServerListEntryNormal) serverListSelector
          .getListEntry(i)).getServerData().serverName.equals(pinnedServer.getName())) {

        refreshServerList();

        return;
      }
    }
  }

}
