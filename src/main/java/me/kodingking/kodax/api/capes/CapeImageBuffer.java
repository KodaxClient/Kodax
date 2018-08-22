package me.kodingking.kodax.api.capes;

import com.mojang.authlib.GameProfile;
import java.awt.image.BufferedImage;
import me.kodingking.kodax.api.KodaxApi;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.util.ResourceLocation;

public class CapeImageBuffer implements IImageBuffer {

  private GameProfile gameProfile;
  private ResourceLocation resourceLocation;

  public CapeImageBuffer(GameProfile gameProfile,
      ResourceLocation resourceLocation) {
    this.gameProfile = gameProfile;
    this.resourceLocation = resourceLocation;
  }

  public BufferedImage parseUserSkin(BufferedImage var1) {
    return KodaxApi.parseCape(var1);
  }

  public void skinAvailable() {
    KodaxApi.capeLocations.put(gameProfile.getId(), resourceLocation);
  }

}
