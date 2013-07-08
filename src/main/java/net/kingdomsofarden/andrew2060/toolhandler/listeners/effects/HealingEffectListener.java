package net.kingdomsofarden.andrew2060.toolhandler.listeners.effects;

import org.bukkit.EntityEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * Simple listener to play wolf hearts on heal for the sake of visibility
 * @author Andrew
 */

public class HealingEffectListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
		if(!(event.getAmount() > 0)) {
			return;
		}
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		LivingEntity lE = (LivingEntity) event.getEntity();
		if(lE.getHealth() == lE.getMaxHealth()) {
			return;
		}
		Wolf w = (Wolf) lE.getWorld().spawnEntity(lE.getLocation(), EntityType.WOLF);
		w.playEffect(EntityEffect.WOLF_HEARTS);
		w.remove();
	}

}
