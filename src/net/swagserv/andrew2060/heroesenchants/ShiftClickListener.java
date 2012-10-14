package net.swagserv.andrew2060.heroesenchants;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

public class ShiftClickListener implements Listener{
	private HeroesEnchants plugin;

	ShiftClickListener(HeroesEnchants plugin) {
		this.plugin = plugin;
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
				break;
	        }
	    }
	}
	 
	private void handleCrafting(InventoryClickEvent event) {
	   
	    HumanEntity player = event.getWhoClicked();
	    ItemStack toCraft = event.getCurrentItem();
	    ItemStack toStore = event.getCursor();
	 
	    // Make sure we are actually crafting anything
	    if (player != null && hasItems(toCraft)) {
	 
	        if (event.isShiftClick()) {
	            // Hack ahoy
	            schedulePostDetection(player, toCraft);
	        } else {
	            // The items are stored in the cursor. Make sure there's enough space.
	            if (isStackSumLegal(toCraft, toStore)) {
	                //int newItemsCount = toCraft.getAmount();
	               return;
	                // Do what you need to do here!
	                // ...
	            }
	        }
	    }
	}
	 
	// HACK! The API doesn't allow us to easily determine the resulting number of
	// crafted items, so we're forced to compare the inventory before and after.
	private void schedulePostDetection(final HumanEntity player, final ItemStack compareItem) {
	    final ItemStack[] preInv = player.getInventory().getContents();
	    @SuppressWarnings("unused")
		final int ticks = 1;
	   
	    // Clone the array. The content may (was for me) be mutable.
	    for (int i = 0; i < preInv.length; i++) {
	        preInv[i] = preInv[i] != null ? preInv[i].clone() : null;
	    }
	   
	    
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	        @Override
	        public void run() {
	            final ItemStack[] postInv = player.getInventory().getContents();
	           // int newItemsCount = 0;
	           
	            for (int i = 0; i < preInv.length; i++) {
	                ItemStack pre = preInv[i];
	                ItemStack post = postInv[i];
	                if(pre != post && post.getTypeId()==compareItem.getTypeId()) {
	                	int id = compareItem.getTypeId();
	                	if(!(id == 276 || id == 267 || id == 283 || id == 272 || id == 268 || id == 261)) {
	                		return;
	                	}
	                	post.addEnchantments(compareItem.getEnchantments());
	                	post.setDurability(compareItem.getDurability());
	                }
	                // We're only interested in filled slots that are different
	             //   if (hasSameItem(compareItem, post) && (hasSameItem(compareItem, pre) || pre == null)) {
	           //         newItemsCount += post.getAmount() - (pre != null ? pre.getAmount() : 0);
	           //     }
	            }
	           /* if (newItemsCount > 0) {
	                // Do your thing here too.
	                // ...
	            }*/
	        
	        }
	    }, 40L);
	}
	 
	@SuppressWarnings("unused")
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
	 
	private boolean isStackSumLegal(ItemStack a, ItemStack b) {
	    // See if we can create a new item stack with the combined elements of a and b
	    if (a == null || b == null)
	        return true; // Treat null as an empty stack
	    else
	        return a.getAmount() + b.getAmount() <= a.getType().getMaxStackSize();
	}
	private boolean hasItems(ItemStack stack) {
        return stack != null && stack.getAmount() > 0;
    }

}
