package net.kingdomsofarden.andrew2060.toolhandler.listeners.durability;


import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Extends Durability of ChainMail armor by appx 2 times (random durability loss chance).
 */
public class ChainMailListener implements Listener {
    Random rand;
    public ChainMailListener() {
        this.rand = new Random();
    }
    
    @EventHandler(priority = EventPriority.HIGHEST,ignoreCancelled = true) 
	public void onDurabilityChange(PlayerItemDamageEvent event) {
		ItemStack i = event.getItem();
		switch(i.getType()) {
		
		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS: {
		    if(!(rand.nextInt(2) == 0)) {
		        event.setCancelled(true);
		    }
		}
		default: {
		    return;
		}
		
		}
		
	}
}
