package me.kodingking.kodax.manifests;

import com.google.gson.Gson;
import java.net.URL;
import java.util.List;
import jdk.nashorn.api.scripting.URLReader;

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

  public static ClientManifest fetch(URL url) {
    return new Gson().fromJson(new URLReader(url), ClientManifest.class);
  }

}
