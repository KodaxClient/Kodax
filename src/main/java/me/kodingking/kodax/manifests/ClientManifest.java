package me.kodingking.kodax.manifests;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import me.kodingking.kodax.utils.HttpUtils;

public class ClientManifest {

  private List<PinnedServer> pinned_servers;

  public List<PinnedServer> getPinnedServers() {
    return pinned_servers;
  }

  public static class PinnedServer {

    private String name, ip;

    public String getName() {
      return name;
    }

    public String getIp() {
      return ip;
    }
  }

  public static ClientManifest fetch(String url) {
    try {
      String json = HttpUtils.get(url);
      if (json.isEmpty()) {
        return new ClientManifest();
      }

      return new Gson().fromJson(json, ClientManifest.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new ClientManifest();
  }

}
