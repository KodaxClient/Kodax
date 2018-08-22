package me.kodingking.kodax.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.kodingking.kodax.api.capes.CapeImageBuffer;
import me.kodingking.kodax.utils.Multithreading;
import me.kodingking.kodaxnetty.utils.HttpUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class KodaxApi {

  public static Map<UUID, ResourceLocation> capeLocations = new HashMap<>();

  public static void clearCache() {
    capeLocations.clear();
  }

  public static BufferedImage parseCape(BufferedImage img) {
    int imageWidth = 64;
    int imageHeight = 32;
    int srcWidth = img.getWidth();

    for (int srcHeight = img.getHeight(); imageWidth < srcWidth || imageHeight < srcHeight;
        imageHeight *= 2) {
      imageWidth *= 2;
    }

    BufferedImage imgNew = new BufferedImage(imageWidth, imageHeight, 2);
    Graphics g = imgNew.getGraphics();
    g.drawImage(img, 0, 0, null);
    g.dispose();
    return imgNew;
  }

  public static boolean hasCape(UUID uuid) {
    try {
      JsonObject apiObj = new JsonParser().parse(HttpUtil.performGet(
          "http://api.kodingking.com/kodax/endpoints/capes.php?UUID=" + uuid
              .toString()))
          .getAsJsonObject();
      return apiObj.has("status") && !apiObj.get("status").getAsString().equalsIgnoreCase("Error");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static String getCapeUrl(UUID uuid) {
    try {
      JsonObject apiObj = new JsonParser().parse(HttpUtil.performGet(
          "http://api.kodingking.com/kodax/endpoints/capes.php?UUID=" + uuid
              .toString()))
          .getAsJsonObject();
      return apiObj.get("capeUrl").getAsString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static void downloadCape(GameProfile profile) {
    Multithreading.run(() -> {
      if (profile.getName() != null && !profile.getName().isEmpty()) {
        if (hasCape(profile.getId())) {
          downloadCape(profile, getCapeUrl(profile.getId()));
//        } else if (ZyntaxAPI.INSTANCE.hasCape(profile.getId())) {
//          downloadCape(profile, ZyntaxAPI.INSTANCE.getCapeURL(profile.getId()));
        } else {
          downloadCape(profile, "http://s.optifine.net/capes/" + profile.getName() + ".png");
        }
      }
    });
  }

  public static void downloadCape(GameProfile profile, String url) {
    url = url.replace("https://", "http://");

    System.out.println("Downloading cape from " + url);
    MinecraftProfileTexture mpt = new MinecraftProfileTexture(url, new HashMap<>());
    final ResourceLocation rl = new ResourceLocation("kodax/capes/" + mpt.getHash());
    CapeImageBuffer iib = new CapeImageBuffer(profile, rl);
    ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null,
        iib);
    Minecraft.getMinecraft().getTextureManager().loadTexture(rl, textureCape);
  }

}
