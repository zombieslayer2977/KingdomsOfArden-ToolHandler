package net.swagserv.andrew2060.toolhandler.listeners.mods;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.swagserv.andrew2060.toolhandler.ToolHandlerPlugin;

public class ModCombinerListener implements Listener {

	private ToolHandlerPlugin plugin;
	private HashMap<Block, Inventory> activeModChests;
	
	public ModCombinerListener(ToolHandlerPlugin toolHandlerPlugin) {
		this.plugin = toolHandlerPlugin;
		this.activeModChests = new HashMap<Block,Inventory>();
	}
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = true)
	public void onPluginDisable(PluginDisableEvent event) {
		if(event.getPlugin() == this.plugin)  {
			
		}
	}
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteractModCombiner(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		Block b = event.getClickedBlock();
		if(!(b.getType().equals(Material.ENCHANTMENT_TABLE) && b.isBlockPowered())) {
			return;
		}
		event.setCancelled(true);
		Player p = event.getPlayer();
		//TODO: Warning: UNSAFE: stuff inside disappears on server shutdown
		//TODO: perhaps have server shutdown iterate through the list of activeHiddenChests and spawn the contents on the ground
		if(activeModChests.containsKey(b)) {
			p.openInventory(activeModChests.get(b));
		} else {
			//Fill inventory with fake stuff for slot 3-9 so that you cannot place stuff in there
			Inventory inv = Bukkit.createInventory(null, 9, ChatColor.WHITE + "Item Modification Combiner");
			inv.setItem(2, new ItemStack(Material.WOOL));
			inv.setItem(3, new ItemStack(Material.WOOL));
			inv.setItem(4, new ItemStack(Material.WOOL));
			inv.setItem(5, new ItemStack(Material.WOOL));
			inv.setItem(6, new ItemStack(Material.WOOL));
			inv.setItem(7, new ItemStack(Material.WOOL));
			inv.setItem(8, new ItemStack(Material.WOOL));
			event.getPlayer().openInventory(inv);
			activeModChests.put(b, inv);
		}
		
	}
	public HashMap<Block, Inventory> getActiveModChests() {
		return this.activeModChests;
	}
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerLeftClick(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			return;
		}
		Block b = event.getClickedBlock();
		if(!(b.getType().equals(Material.ENCHANTMENT_TABLE) && b.isBlockPowered())) {
			return;
		}
		event.setCancelled(true);
		Player p = event.getPlayer();
		if(!activeModChests.containsKey(b)) {
			p.sendMessage("This item mod creator does not have anything in it!");
			return;
		}
		Inventory modInv = activeModChests.get(b);
		if(!modInv.contains(Material.EMERALD)) {
			p.sendMessage("This item mod creator does not have a soul gem to create a mod with!");
			return;
		}
		int emeraldSlot = modInv.first(Material.EMERALD);
		ItemStack soulGem = modInv.getItem(emeraldSlot);
		if(!soulGem.getItemMeta().hasDisplayName()) {
			p.sendMessage("This item mod creator does not have a soul gem to create a mod with!");
			return;
		}
		ItemStack tool = null;
		if(emeraldSlot == 1) {
			tool = modInv.getItem(0);
		} else {
			tool = modInv.getItem(1);
		}
		int code = addMod(tool,soulGem);
		if(code == 1) {
			if(soulGem.getAmount() > 1) {
				soulGem.setAmount(soulGem.getAmount()-1);
			} else {
				modInv.setItem(emeraldSlot, new ItemStack(Material.AIR));
			}
			p.sendMessage("Item Modification Successful");
			return;
		} else if(code == 0) {
			p.sendMessage("There are no open mod slots on this tool");
			return;
		} else if(code == -1) {
			p.sendMessage("This is not a valid tool to apply a mod to!");
			return;
		} else if(code == -2) {
			p.sendMessage("There can only be one tool in the item mod creator!");
			return;
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onInventoryInteract(InventoryClickEvent event) {
		Inventory inv = event.getView().getTopInventory();
		if(inv.getTitle().toLowerCase().contains("modification combiner")) {
			if(event.getSlot() > 1 && event.getSlot() < 9 && !event.getSlotType().equals(SlotType.QUICKBAR)) {
				event.setCancelled(true);
				((Player)event.getWhoClicked()).updateInventory();
				return;
			}
		}
		
	}
	public int addMod(ItemStack tool, ItemStack soulGem) {
		String name = ChatColor.stripColor(soulGem.getItemMeta().getDisplayName());
		name = name.toLowerCase();
		int weight = 1;
		if(name.contains("weak")) {
			weight = 2;
		} else if(name.contains("common")) {
			weight = 3;
		} else if(name.contains("strong")) {
			weight = 4;
		} else if(name.contains("major")) {
			weight = 5;
		} else if(name.contains("master")) {
			weight = 6;
		} else if(name.contains("legendary")) {
			weight = 7;
		}
		return plugin.getModManager().addMod(tool, weight);
	}

}
