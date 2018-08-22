package me.kodingking.kodax.manifests;

import com.google.gson.Gson;
import java.net.URL;
import java.util.List;
import me.kodingking.kodaxnetty.utils.HttpUtil;

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
    return new Gson().fromJson(HttpUtil.performGet(url.getPath()), ClientManifest.class);
  }

}
