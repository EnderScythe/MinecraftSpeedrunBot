package me.ender.nmsexample;

import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

    public MovementListener(Main plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onMovement(PlayerMoveEvent event){
        //Different Player implementations
        Player player = event.getPlayer(); // Bukkit
        CraftPlayer craftPlayer = (CraftPlayer) player; // CraftBukkit
        EntityPlayer entityPlayer = craftPlayer.getHandle(); // NMS (net.minecraft.server)

        PlayerConnection playerConnection = entityPlayer.c;
        PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.c, 0.0f);
        playerConnection.b(packet);
        player.sendMessage("Am I cooked?");
    }
}
