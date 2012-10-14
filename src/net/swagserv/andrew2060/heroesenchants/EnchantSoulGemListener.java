package net.swagserv.andrew2060.heroesenchants;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.Hero;

public class EnchantSoulGemListener implements Listener {

	/*private HeroesEnchants plugin;*/
	public Heroes heroes = (Heroes) Bukkit.getServer().getPluginManager().getPlugin("Heroes");

	public EnchantSoulGemListener(HeroesEnchants plugin) {
		/*this.plugin = plugin;*/
	}
	
	//Cancel vanilla enchanting
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEnchantItem(EnchantItemEvent event) {
		event.setCancelled(true);
		event.getEnchanter().sendMessage(ChatColor.RED + "Magical imbalance has utterly destroyed the enchanting table's ability to draw upon ambient magicka to enchant items. A soul gem (emerald) is required!");
		event.getEnchanter().sendMessage(ChatColor.RED + "If you are an enchanter and have a soul gem, use /skills enchanter to see enchanting options available to you!");
	}
	
	//Soul gem stacking handled in different plugin: http://dev.bukkit.org/server-mods/stackableitems/
	//Handle Soul Gem Filling
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player p = event.getEntity();
		EntityDamageEvent event2 = p.getLastDamageCause();
		if(!(event2.getCause() == DamageCause.ENTITY_ATTACK)) {
			return;
		}
		Player k = p.getKiller();
		if(k == null) {
			return;
		}
		if(!k.getInventory().contains(Material.EMERALD)) {
			k.sendMessage(ChatColor.AQUA + "You captured a soul, but have no empty soul gems (emeralds) to contain it!");
			return;
		}
		PlayerInventory pInv = k.getInventory();
		HashMap<Integer, ? extends ItemStack> hMapInv = pInv.all(Material.EMERALD);
		Integer[] invID = hMapInv.keySet().toArray(new Integer[0]);
		for(int x = 0; x < invID.length; x++) {
			ItemStack i = pInv.getItem(invID[x]);
			if(i.containsEnchantment(Enchantment.DAMAGE_ALL)) {
				continue;
			} else {
				Hero h = heroes.getCharacterManager().getHero(event.getEntity());
				int targetLevel = h.getLevel();
				if(0 < targetLevel && targetLevel < 10) {
					i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
					k.sendMessage(ChatColor.AQUA + "You Captured a Petty (lvl 1-9) Soul inside a Soul Gem!");
					return;
				}
				if(9 < targetLevel && targetLevel < 20) {
					i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
					k.sendMessage(ChatColor.AQUA + "You Captured a Lesser (lvl 10-19) Soul inside a Soul Gem!");
					return;
				}
				if(19 < targetLevel && targetLevel < 30) {
					i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
					k.sendMessage(ChatColor.AQUA + "You Captured a Common (lvl 20-29) Soul inside a Soul Gem!");
					return;
				}
				if(29 < targetLevel && targetLevel < 40) {
					i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
					k.sendMessage(ChatColor.AQUA + "You Captured a Greater (lvl 30-39) Soul inside a Soul Gem!");
					return;
				}
				if(39 < targetLevel && targetLevel < 50) {
					i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
					k.sendMessage(ChatColor.AQUA + "You Captured a Grand (lvl 40-49) Soul inside a Soul Gem!");
					return;
				}
				if(targetLevel == 50) {
					i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
					k.sendMessage(ChatColor.AQUA + "You Captured an Ultimate (lvl 50) Soul inside a Soul Gem!");
					return;
				}
				return;
			}			
		}
		k.sendMessage(ChatColor.RED + "Your Soul Gems are all Full!");
	}
	//Apply Sharpness I to all new crafted Swords and Power I to newly crafted bows
	@EventHandler(priority=EventPriority.HIGHEST) 
	public void onItemCraft(CraftItemEvent event){
		Material type = event.getCurrentItem().getType();
		switch(type) {
		case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD:
			((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your newly forged sword is not very sharp...you should take the sword to a blacksmith to sharpen it.");
			event.getCurrentItem().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			break;
		case BOW:
			((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your newly strung bow is not as taut as it could be...you should take the bow to a blacksmith to tighten it.");
			event.getCurrentItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			break;
		case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS:
			((Player)event.getWhoClicked()).sendMessage(ChatColor.GRAY + "Your armor provides decent protection, but the skill of a blacksmith could massively improve it.");
			event.getCurrentItem().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			break;
		default:
			break;
		}
		return;		
	}
	
}
	

