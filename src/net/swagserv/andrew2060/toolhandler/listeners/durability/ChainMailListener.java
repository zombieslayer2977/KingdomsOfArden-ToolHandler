package net.swagserv.andrew2060.toolhandler.listeners.durability;


import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
/**
 * Extends Durability of ChainMail armor by 4x.
 */
public class ChainMailListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true) 
	public void onDurabilityChange(PlayerItemDamageEvent event) {
		ItemStack i = event.getItem();
		switch(i.getType()) {
		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
		    event.setDamage((int) (event.getDamage()*0.25));
		default:
		    return;
		}
		
	}
}
