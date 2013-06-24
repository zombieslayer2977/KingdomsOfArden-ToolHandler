package net.swagserv.andrew2060.toolhandler.listeners.crafting;

import net.swagserv.andrew2060.toolhandler.util.GeneralLoreUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public class ItemStackListener implements Listener {
    @EventHandler
	public void onPickupItem(InventoryPickupItemEvent event) {
		GeneralLoreUtil.updateLore(event.getItem().getItemStack());
	}
}
