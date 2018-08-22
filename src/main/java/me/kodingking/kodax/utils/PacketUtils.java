package me.kodingking.kodax.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.netty.buffer.Unpooled;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public class PacketUtils {

  public static C17PacketCustomPayload getPayload(String channel, Map<String, Object> msg) {
    PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
    String message = new Gson().toJson(msg);
    buf.writeInt(message.length());
    buf.writeString(message);
    return new C17PacketCustomPayload(channel, buf);
  }

  public static S3FPacketCustomPayload getPayloadS(String channel, Map<String, Object> msg) {
    PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
    String message = new Gson().toJson(msg);
    buf.writeInt(message.length());
    buf.writeString(message);
    return new S3FPacketCustomPayload(channel, buf);
  }

  public static Payload getPayload(S3FPacketCustomPayload incoming) {
    int length = incoming.getBufferData().readInt();
    String message = incoming.getBufferData().readStringFromBuffer(length);
    java.lang.reflect.Type type = new TypeToken<Map<String, Object>>() {
    }.getType();
    return new Payload(incoming.getChannelName(), new Gson().fromJson(message, type));
  }

  public static Payload getPayloadS(C17PacketCustomPayload incoming) {
    int length = incoming.getBufferData().readInt();
    String message = incoming.getBufferData().readStringFromBuffer(length);
    java.lang.reflect.Type type = new TypeToken<Map<String, Object>>() {
    }.getType();
    return new Payload(incoming.getChannelName(), new Gson().fromJson(message, type));
  }

  public static class Payload {

    public String channel;
    private Map<String, Object> message;

    private Payload(String channel, Map<String, Object> message) {
      this.channel = channel;
      this.message = message;
    }

    public Map<String, Object> getMessage() {
      String json = new Gson().toJson(message);
      java.lang.reflect.Type type = new TypeToken<HashMap<String, Object>>() {
      }.getType();
      return new Gson().fromJson(json, type);
    }

    @Override
    public String toString() {
      return "Payload[" + new Gson().toJson(this) + "]";
    }
  }
}