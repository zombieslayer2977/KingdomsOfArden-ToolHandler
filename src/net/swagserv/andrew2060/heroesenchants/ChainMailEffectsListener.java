package net.swagserv.andrew2060.heroesenchants;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.herocraftonline.heroes.api.events.SkillDamageEvent;

public class ChainMailEffectsListener implements Listener { 
	@EventHandler(priority=EventPriority.LOWEST)
	public void onSkillDamage(SkillDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) event.getEntity();
		PlayerInventory pInv = p.getInventory();
		ItemStack helmet = pInv.getHelmet();
		ItemStack chest = pInv.getChestplate();
		ItemStack legs = pInv.getLeggings();
		ItemStack boots = pInv.getBoots();
		if(helmet.getType() != Material.CHAINMAIL_HELMET 
				|| chest.getType() != Material.CHAINMAIL_CHESTPLATE 
				|| legs.getType() != Material.CHAINMAIL_LEGGINGS
				|| boots.getType() != Material.CHAINMAIL_BOOTS) {
			return;
		}
		event.setDamage((int) (event.getDamage()*0.6));
	}

}
