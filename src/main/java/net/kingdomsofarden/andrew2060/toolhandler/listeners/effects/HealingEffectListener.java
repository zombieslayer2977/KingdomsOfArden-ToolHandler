package net.kingdomsofarden.andrew2060.toolhandler.listeners.effects;

import net.kingdomsofarden.andrew2060.toolhandler.effects.ClientEffectSender;
import net.kingdomsofarden.andrew2060.toolhandler.effects.ClientEffectType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;

/**
 * Simple listener to play wolf hearts on heal for the sake of visibility
 * @author Andrew
 */

public class HealingEffectListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onHeroRegainHealth(HeroRegainHealthEvent event) {
		if(!(event.getAmount() > 0)) {
			return;
		}
		Player p = event.getHero().getPlayer();
		if(p.getHealth() == p.getMaxHealth()) {
			return;
		}
		ClientEffectSender.playClientEffect(p, ClientEffectType.HEART, new Vector(0,0,0), 1F, 10, true);
	}

}
