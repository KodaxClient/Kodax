package me.kodingking.kodax.utils.server;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;

public enum Server {

  ZYNTAX("mc05.deluxeno.de.", 40389);

  private String ip;
  private int port;

  Server(String ip) {
    this.ip = ip;
    this.port = 0;
  }

  Server(String ip, int port) {
    this.ip = ip;
    this.port = port;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  public boolean isConneted() {
    ServerAddress serverAddress = ServerAddress.fromString(Minecraft.getMinecraft().getCurrentServerData().serverIP);
    return serverAddress.getIP().endsWith(ip) && (port == 0 || serverAddress.getPort() == port);
  }
}
