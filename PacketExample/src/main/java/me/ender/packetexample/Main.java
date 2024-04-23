package me.ender.packetexample;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("boom").setExecutor(new BoomCommand());

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        //Listen to packets going from the client to the server (incoming packets)
        manager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                //extract info from packet
                double x_pos = packet.getDoubles().read(0);
                double y_pos = packet.getDoubles().read(1);
                double z_pos = packet.getDoubles().read(2);
                boolean onGround = packet.getBooleans().read(0);

                //player.sendMessage("INBOUND PACKET: x:" + x_pos + " y:" + y_pos + " z:" + z_pos + " onGround:" + onGround);
            }
        });

        //Listen to packets going from the server to the client (outgoing packets)
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Server.REL_ENTITY_MOVE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                //extract info from packet
                short deltaX = packet.getShorts().read(0);
                short deltaY = packet.getShorts().read(1);
                short deltaZ = packet.getShorts().read(2);
                int entityID = packet.getIntegers().read(0);
                Entity entity = manager.getEntityFromID(player.getWorld(), entityID);
                //entity.teleport(player.getLocation());

                //player.sendMessage("OUTGOING PACKET: deltaX:" + deltaX + " deltaY:" + deltaY + " deltaZ:" + deltaZ + " ID:" + entityID);

            }
        });

        //Cancel Packets
//        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.CHAT) {
//            @Override
//            public void onPacketReceiving(PacketEvent event) {
//                event.setCancelled(true);
//            }
//        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
