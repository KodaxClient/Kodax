package me.kodingking.kodax.event.events.network;

import me.kodingking.kodax.event.Event;
import net.minecraft.network.Packet;

public class PacketReceivedEvent extends Event {

    private Packet<?> packet;

    public PacketReceivedEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
