package me.kodingking.kodax.mixins.client.multiplayer;

import java.io.File;
import java.util.List;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.manifests.ClientManifest.PinnedServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerList.class)
public class MixinServerList {

  @Shadow
  @Final
  private List<ServerData> servers;

  @Shadow
  @Final
  private Minecraft mc;
  @Shadow
  @Final
  private static Logger logger;

  @Inject(method = "loadServerList", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", shift = Shift.AFTER))
  private void loadServerList(CallbackInfo callbackInfo) {
    for (PinnedServer pinnedServer : Kodax.FULL_MANIFEST.getPinnedServers()) {
      this.servers.add(new ServerData(pinnedServer.getName(), pinnedServer.getIp(), true));
    }
  }

  /**
   * @author KodingKing
   */
  @Overwrite
  public void saveServerList() {
    try {
      NBTTagList nbttaglist = new NBTTagList();

      for (ServerData serverdata : this.servers) {
        if (Kodax.FULL_MANIFEST.getPinnedServers().stream().noneMatch(
            pinnedServer -> pinnedServer.getName().equals(serverdata.serverName) && pinnedServer
                .getIp().equals(serverdata.serverIP))) {
          nbttaglist.appendTag(serverdata.getNBTCompound());
        }
      }

      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.setTag("servers", nbttaglist);
      CompressedStreamTools.safeWrite(nbttagcompound, new File(this.mc.mcDataDir, "servers.dat"));
    } catch (Exception exception) {
      logger.error((String) "Couldn\'t save server list", (Throwable) exception);
    }
  }
}
