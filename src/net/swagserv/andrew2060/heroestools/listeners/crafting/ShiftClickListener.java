package net.swagserv.andrew2060.heroestools.listeners.crafting;

import java.util.Objects;

import net.swagserv.andrew2060.heroestools.ToolHandlerPlugin;
import net.swagserv.andrew2060.heroestools.util.GeneralLoreUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

public class ShiftClickListener implements Listener{
	private ToolHandlerPlugin plugin;

	public ShiftClickListener(ToolHandlerPlugin plugin) {
		this.plugin = plugin;
	}
	private boolean hasItems(ItemStack stack) {
        return stack != null && stack.getAmount() > 0;
    }
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClickEvent(InventoryClickEvent event) {
	 
	    if (event.getInventory() != null &&
	        event.getSlotType() == SlotType.RESULT) {
	   
	        switch (event.getInventory().getType()) {
	        case CRAFTING:
	            handleCrafting(event);
	            break;
	        case WORKBENCH:
	            handleCrafting(event);
	            break;
			default:
				return;
	        }
	    }
	}
	 
	private void handleCrafting(InventoryClickEvent event) {
	 
	    HumanEntity player = event.getWhoClicked();
	    ItemStack toCraft = event.getCurrentItem();
	 
	    // Make sure we are actually crafting anything
	    if (player != null && hasItems(toCraft)) {
	        if (event.isShiftClick()) {
	            // Hack ahoy
	            schedulePostDetection(player, toCraft);
	        }
	    }
	}
	 
	// HACK! The API doesn't allow us to easily determine the resulting number of
	// crafted items, so we're forced to compare the inventory before and after.
	private void schedulePostDetection(final HumanEntity player, final ItemStack compareItem) {
	    final ItemStack[] preInv = player.getInventory().getContents();
	    final int ticks = 1;
	 
	    // Clone the array. The content may (was for me) be mutable.
	    for (int i = 0; i < preInv.length; i++) {
	        preInv[i] = preInv[i] != null ? preInv[i].clone() : null;
	    }
	 
	    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	        @Override
	        public void run() {
	            final ItemStack[] postInv = player.getInventory().getContents();
	       
	            for (int i = 0; i < preInv.length; i++) {
	                ItemStack pre = preInv[i];
	                ItemStack post = postInv[i];
	 
	                // We're only interested in filled slots that are different
	                if (hasSameItem(compareItem, post) && (hasSameItem(compareItem, pre) || pre == null)) {
	                    GeneralLoreUtil.populateLore(post);
	                }
	            }
	       
	        }
	    }, ticks);
	}
	 
	private boolean hasSameItem(ItemStack a, ItemStack b) {
	    if (a == null)
	        return b == null;
	    else if (b == null)
	        return a == null;
	 
	    return a.getTypeId() == b.getTypeId() &&
	          a.getDurability() == b.getDurability() &&
	          Objects.equals(a.getData(), b.getData()) &&
	          Objects.equals(a.getEnchantments(), b.getEnchantments());
	}

}
