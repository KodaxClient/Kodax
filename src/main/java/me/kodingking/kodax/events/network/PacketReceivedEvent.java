package me.kodingking.kodax.events.network;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.network.Packet;

public class PacketReceivedEvent implements Event {

    private Packet<?> packet;

    public PacketReceivedEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
