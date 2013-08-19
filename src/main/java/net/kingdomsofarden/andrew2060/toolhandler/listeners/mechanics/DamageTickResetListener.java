package net.kingdomsofarden.andrew2060.toolhandler.listeners.mechanics;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class DamageTickResetListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true) 
    public void onProjectileHit(ProjectileHitEvent event) {
        for(Entity e : event.getEntity().getNearbyEntities(1, 2, 1)) {
            if(e instanceof LivingEntity) {
                ((LivingEntity)e).setNoDamageTicks(0);
            }
        }
    }
}
