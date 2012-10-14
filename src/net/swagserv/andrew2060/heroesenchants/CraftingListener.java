package net.swagserv.andrew2060.heroesenchants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftingListener implements Listener {
	@EventHandler(priority=EventPriority.MONITOR) 
	public void onItemCraft(CraftItemEvent event){
		if(event.isCancelled()) {
			return;
		}
		Material type = event.getCurrentItem().getType();
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
		case DIAMOND_PICKAXE: case IRON_PICKAXE: case GOLD_PICKAXE: case STONE_PICKAXE: case WOOD_PICKAXE: 
			((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your pick works quite well, but a blacksmith could increase its mining efficiency, thus allowing it to mine blocks faster.");
			break;
		default:
			break;
		}
		return;		
	}

}
