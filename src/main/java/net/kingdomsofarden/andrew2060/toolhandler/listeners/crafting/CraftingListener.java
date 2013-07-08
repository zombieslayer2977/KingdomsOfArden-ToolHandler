package net.kingdomsofarden.andrew2060.toolhandler.listeners.crafting;

import net.kingdomsofarden.andrew2060.toolhandler.util.GeneralLoreUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CraftingListener implements Listener {
    @EventHandler(priority=EventPriority.MONITOR,ignoreCancelled = true) 
    public void onItemPreCraft(PrepareItemCraftEvent event) {
        if(event.isRepair()) {
            event.getInventory().setResult(null);
        } else {
            ItemStack result = event.getInventory().getResult();
            Material type = result.getType();
            GeneralLoreUtil.populateLoreDefaults(result);
            switch(type) {
                case DIAMOND_HOE: {
                    ItemMeta meta = result.getItemMeta();
                    meta.setDisplayName("Diamond Scythe");
                    result.setItemMeta(meta);
                    break;
                }
                case IRON_HOE: {
                    ItemMeta meta = result.getItemMeta();
                    meta.setDisplayName("Iron Scythe");
                    result.setItemMeta(meta);
                    break;
                }
                case GOLD_HOE: {
                    ItemMeta meta = result.getItemMeta();
                    meta.setDisplayName("Gold Scythe");
                    result.setItemMeta(meta);
                    break;
                }
                case STONE_HOE: {
                    ItemMeta meta = result.getItemMeta();
                    meta.setDisplayName("Stone Scythe");
                    result.setItemMeta(meta);
                    break;
                }
                case WOOD_HOE: {
                    ItemMeta meta = result.getItemMeta();
                    meta.setDisplayName("Wood Scythe");
                    result.setItemMeta(meta);
                    break;
                }
                default: {
                    break;
                }
            }
            event.getInventory().setResult(result);
        }            
    }
	@EventHandler(priority=EventPriority.MONITOR) 
	public void onItemCraft(CraftItemEvent event){
		if(event.isCancelled()) {
			return;
		}
		ItemStack created = event.getCurrentItem();
		Material type = created.getType();
		switch(type) {
			case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD:
				((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your newly forged sword is not very sharp...you should take the sword to a blacksmith to sharpen it.");
				break;
			case BOW:
				((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your newly strung bow is not as taut as it could be...you should take the bow to a blacksmith to tighten it.");
				break;
			case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS:
				((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your armor provides decent protection, but the skill of a blacksmith could massively improve it.");
				break;
			case DIAMOND_PICKAXE: case DIAMOND_AXE: case DIAMOND_HOE: case DIAMOND_SPADE: case IRON_PICKAXE: case IRON_AXE: case IRON_HOE: case IRON_SPADE: case GOLD_PICKAXE: case GOLD_AXE: case GOLD_HOE: case GOLD_SPADE: case STONE_PICKAXE: case STONE_AXE: case STONE_HOE: case STONE_SPADE: case WOOD_PICKAXE: case WOOD_AXE: case WOOD_HOE: case WOOD_SPADE: 
				((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your newly created tool works quite well, but a blacksmith could increase its mining efficiency, thus allowing it to mine blocks faster.");
				break;
			default:
				return;
		}
		return;		
	}

}
