package me.kodingking.kodax.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import me.kodingking.kodax.api.capes.CapeImageBuffer;
import me.kodingking.kodax.utils.Multithreading;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class KodaxApi {

  public static volatile Map<UUID, ResourceLocation> capeLocations = new HashMap<>();
  private static volatile int capeThreadCount = 0;
  private static volatile Queue<GameProfile> capeQueue = new ArrayDeque<>();

  public static void clearCache() {
    capeLocations.clear();
    capeQueue.clear();
    capeThreadCount = 0;
  }

  public static void init() {
    Multithreading.run(() -> {
      for (; ; ) {
        if (capeThreadCount < 0) {
          capeThreadCount = 0;
        }

        GameProfile profile = capeQueue.poll();
        if (profile == null || capeThreadCount + 1 > 3) {
          continue;
        }

        KodaxApi.capeThreadCount++;
        System.out.println(
            "Downloading cape for " + profile.getName() + " in thread #" + capeThreadCount);
        Multithreading.run(new CapeRunnable(profile));
      }
    });
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
      HttpClient client = HttpClientBuilder.create().build();
      HttpResponse httpResponse = client.execute(new HttpGet(
          "http://api.kodingking.com/kodax/endpoints/capes.php?UUID=" + uuid.toString()));
      String json = IOUtils.toString(httpResponse.getEntity().getContent());
      if (json.isEmpty()) {
        return false;
      }
      JsonObject apiObj = new JsonParser().parse(json)
          .getAsJsonObject();
      return apiObj.has("status") && !apiObj.get("status").getAsString().equalsIgnoreCase("Error");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  public static String getCapeUrl(UUID uuid) {
    try {
      HttpClient client = HttpClientBuilder.create().build();
      HttpResponse httpResponse = client.execute(new HttpGet(
          "http://api.kodingking.com/kodax/endpoints/capes.php?UUID=" + uuid.toString()));
      String json = IOUtils.toString(httpResponse.getEntity().getContent());
      if (json.isEmpty()) {
        return "";
      }
      JsonObject apiObj = new JsonParser().parse(json)
          .getAsJsonObject();
      return apiObj.get("capeUrl").getAsString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  public static void downloadCape(GameProfile profile) {
    capeQueue.add(profile);
  }

  public static void downloadCape(GameProfile profile, String url) {
    System.out.println("Downloading cape from " + url);
    MinecraftProfileTexture mpt = new MinecraftProfileTexture(url, new HashMap<>());
    final ResourceLocation rl = new ResourceLocation("kodax/capes/" + mpt.getHash());
    CapeImageBuffer iib = new CapeImageBuffer(profile, rl);
    ThreadDownloadImageData textureCape = new ThreadDownloadImageData(null, mpt.getUrl(), null,
        iib);
    Minecraft.getMinecraft().getTextureManager().loadTexture(rl, textureCape);
  }

  public static class CapeRunnable implements Runnable {

    private GameProfile gameProfile;

    public CapeRunnable(GameProfile gameProfile) {
      this.gameProfile = gameProfile;
    }

    @Override
    public void run() {
      if (gameProfile.getName() != null && !gameProfile.getName().isEmpty()) {
        if (hasCape(gameProfile.getId())) {
          downloadCape(gameProfile, getCapeUrl(gameProfile.getId()));
        } else {
          downloadCape(gameProfile,
              "http://s.optifine.net/capes/" + gameProfile.getName() + ".png");
        }
      }
      KodaxApi.capeThreadCount = Math.max(0, KodaxApi.capeThreadCount - 1);
    }
  }

}
