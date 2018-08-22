package me.kodingking.kodax.api.zyntax;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import me.kodingking.kodax.api.zyntax.objects.CapeData;
import me.kodingking.kodax.api.zyntax.objects.StaffGroup;
import me.kodingking.kodax.utils.HttpUtils;
import me.kodingking.kodax.utils.MiscUtils;

public class ZyntaxAPI {

  public static final ZyntaxAPI INSTANCE = new ZyntaxAPI();
  private Map<String, String> cache = new HashMap<>();
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private String makeHttpsRequest(String url) {
    if (cache.containsKey(url)) {
      return cache.get(url);
    }

    try {
      String response = HttpUtils.get(url);
      cache.put(url, response);
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e.getClass().getName() + ": " + e.getMessage(), e);
    }
  }

  public void clearCache() {
    this.cache.clear();
  }

  private String makeRequest(String endpoint) {
    return makeHttpsRequest("https://api.zyntaxnetwork.tk/endpoints/" + endpoint);
  }

  public StaffGroup[] getStaff() {
    return gson.fromJson(makeHttpsRequest(
        "https://raw.githubusercontent.com/ZyntaxNetwork/Zyntax-Repo/master/files/zyntaxStaff.json"),
        StaffGroup[].class);
  }

  public UUID[] getStaffUUIDs() {
    List<UUID> uuids = new ArrayList<>();
    for (StaffGroup group : getStaff()) {
      for (StaffGroup.StaffMember member : group.members) {
        uuids.add(UUID.fromString(MiscUtils.insertDashUUID(member.uuid)));
      }
    }
    return uuids.toArray(new UUID[0]);
  }

  public CapeData getCapeData(UUID uuid) {
    JsonObject obj = new JsonParser().parse(makeRequest("user/capes.py?uuid=" + uuid.toString()))
        .getAsJsonObject();
    if (obj.get("success").getAsBoolean()) {
      CapeData data = gson.fromJson(obj.getAsJsonObject("cape_data"), CapeData.class);
      if (!data.hasCape() && Arrays.asList(getStaffUUIDs()).contains(uuid)) {
        try {
          Field field1 = CapeData.class.getDeclaredField("hasCape");
          Field field2 = CapeData.class.getDeclaredField("url");
          field1.setAccessible(true);
          field2.setAccessible(true);
          field2
              .set(data,
                  "https://static.zyntaxnetwork.tk/zyntax/capes/developers/zyntax_cape.png");
          field1.setBoolean(data, true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      System.out.println(data);
      return data;
    } else {
      throw new IllegalArgumentException(String
          .format("Failed to request cape for %s: %s", uuid.toString(),
              obj.get("cause").getAsString()));
    }
  }

  public boolean hasCape(UUID uuid) {
    return getCapeData(uuid).hasCape();
  }

  public String getCapeURL(UUID uuid) {
    return getCapeData(uuid).getUrl();
  }
}
