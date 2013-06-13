package net.swagserv.andrew2060.toolhandler.listeners.crafting;

import net.swagserv.andrew2060.toolhandler.util.GeneralLoreUtil;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawnListener implements Listener {
	public void onPickupItem(ItemSpawnEvent event) {
		GeneralLoreUtil.populateLoreDefaults(event.getEntity().getItemStack());
	}
}
