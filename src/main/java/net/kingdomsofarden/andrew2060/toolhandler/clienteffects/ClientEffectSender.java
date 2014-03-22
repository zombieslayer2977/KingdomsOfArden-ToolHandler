package net.kingdomsofarden.andrew2060.toolhandler.clienteffects;

import net.minecraft.server.v1_7_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ClientEffectSender {

    /**
     * Sends a client effect to a player or a group of players
     *  
     * @param player The player to associate with playing this effect - this also applies as the origin of the effect being played!
     * @param effect The ClientEffectType to play
     * @param offset The area to be covered by particles in origin to the player's location
     * @param speed The speed for the particles to travel at
     * @param count The number of particles to play
     * @param sendToAll Whether this should client effect should be played everywhere or just to the player associated with this client effect
     * @verticalOffset vertical offset to be added to the player's location for the origin
     */
    public static void playClientEffect(Player player, ClientEffectType effect, Vector offset, float speed, int count, boolean sendToAll, double verticalOffset) {
        if(!(player instanceof CraftPlayer)) {
            throw new IllegalArgumentException("The provided player is NOT a CraftPlayer!");
        }
        Location loc = player.getLocation();
        PacketPlayOutWorldParticles clientEffectPacket = new PacketPlayOutWorldParticles(effect.getParticleName(), (float)loc.getX(), (float)(loc.getY() + verticalOffset), (float)loc.getZ(), (float)offset.getX(), (float)offset.getY(), (float)offset.getZ(), speed, count);
        if(sendToAll) {
            ((CraftWorld)loc.getWorld()).getHandle().getTracker().sendPacketToEntity(((CraftPlayer)player).getHandle(), clientEffectPacket);
        } else {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(clientEffectPacket);

        }
    }
    
    public static void strikeLightningNonGlobal(Location loc) {
        LightningStrike strike = loc.getWorld().spigot().strikeLightningEffect(loc, true);
        for(Entity e : strike.getNearbyEntities(100, 64, 100)) {
            if(e instanceof Player) {
                PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect("ambient.weather.thunder", loc.getX() + 0.5,loc.getY() + 0.5,loc.getZ(), 3.0f, 1.0f);
                ((CraftPlayer)e).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }
    
}
