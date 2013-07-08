package net.kingdomsofterraria.andrew2060.toolhandler.listeners.potions;

import net.kingdomsofterraria.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PotionEffectManagerListener implements Listener {
    private PotionEffectManager pEMan;
    public PotionEffectManagerListener(PotionEffectManager effectMan) {
        this.pEMan = effectMan;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        pEMan.untrackEntity(event.getEntity());
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        pEMan.untrackEntity(event.getEntity());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        pEMan.untrackEntity(event.getPlayer());
    }
}
