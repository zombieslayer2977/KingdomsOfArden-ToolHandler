package net.kingdomsofarden.andrew2060.toolhandler.effects;

import net.minecraft.server.v1_6_R2.Packet63WorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ClientEffectSender {

    /**
     * Plays the client effect to all players on the server
     * @param effect The ClientEffectType to play
     * @param loc The location to play the effect at.
     * @param speed The speed for the particles to travel at
     * @param count The number of particles to play
     */
    public static void playClientEffect(ClientEffectType effect, Location loc, float speed, int count) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            playClientEffect(p, effect, loc, speed, count);
        }
    }
    /**
     * Plays the client effect to one specific player on the server
     * @param effect The ClientEffectType to play
     * @param loc The location to play the effect at
     * @param speed The speed for the particles to travel at
     * @param count The number of particles to play
     */
    public static void playClientEffect(Player client, ClientEffectType effect, Location loc, float speed, int count) {
        if(!(client instanceof CraftPlayer)) {
            throw new IllegalArgumentException("The provided player is NOT a CraftPlayer!");
        }
        Packet63WorldParticles clientEffectPacket = new Packet63WorldParticles(effect.getParticleName(), (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0F, 0F, 0F, speed, count);
        ((CraftPlayer)client).getHandle().playerConnection.sendPacket(clientEffectPacket);
    }
    
}
