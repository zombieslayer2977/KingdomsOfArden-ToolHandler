package net.swagserv.andrew2060.toolhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.swagserv.andrew2060.toolhandler.listeners.crafting.CraftingListener;
import net.swagserv.andrew2060.toolhandler.listeners.crafting.ShiftClickListener;
import net.swagserv.andrew2060.toolhandler.listeners.durability.ArmorDurabilityChangeListener;
import net.swagserv.andrew2060.toolhandler.listeners.durability.ToolDurabilityChangeListener;
import net.swagserv.andrew2060.toolhandler.listeners.durability.WeaponDurabilityChangeListener;
import net.swagserv.andrew2060.toolhandler.listeners.lore.ArmorLoreListener;
import net.swagserv.andrew2060.toolhandler.listeners.lore.WeaponLoreListener;
import net.swagserv.andrew2060.toolhandler.listeners.mods.ModCraftingListener;
import net.swagserv.andrew2060.toolhandler.listeners.mods.ModListener;
import net.swagserv.andrew2060.toolhandler.mods.ModManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolHandlerPlugin extends JavaPlugin{
	
	private ModManager modManager;
	private ArmorDurabilityChangeListener armorQualityListener;
	private WeaponDurabilityChangeListener weaponQualityListener;
	private ToolDurabilityChangeListener toolQualityListener;
	
	private CraftingListener craftingListener;
	private ShiftClickListener craftingShiftClickListener;
	
	private ArmorLoreListener armorLoreListener;
	private WeaponLoreListener weapLoreListener;
	
	private ModListener modListener;
	private ModCraftingListener modCraftListener;
	
	private Random rand;

	public void onEnable() {
		//Initialize Listeners
		this.armorQualityListener = new ArmorDurabilityChangeListener();
		this.weaponQualityListener = new WeaponDurabilityChangeListener();
		this.toolQualityListener = new ToolDurabilityChangeListener();
		
		this.craftingListener = new CraftingListener();
		this.craftingShiftClickListener = new ShiftClickListener(this);
		
		this.armorLoreListener = new ArmorLoreListener();
		this.weapLoreListener = new WeaponLoreListener();
		
		this.modListener = new ModListener(this);
		this.modCraftListener = new ModCraftingListener(this);
		
		this.rand = new Random();
		
		//Register listeners used by plugin
		registerListeners();
		
		//Initialize Mod Manager 
		setModManager(new ModManager(this));
		
	}

	private void registerListeners() {
		//Durability Based Prot/Sharpness/Efficiency Listeners
		Bukkit.getPluginManager().registerEvents(this.armorQualityListener, this);
		Bukkit.getPluginManager().registerEvents(this.weaponQualityListener, this);
		Bukkit.getPluginManager().registerEvents(this.toolQualityListener, this);
		
		//Crafting Listeners
		Bukkit.getPluginManager().registerEvents(this.craftingListener, this);
		Bukkit.getPluginManager().registerEvents(this.craftingShiftClickListener, this);
		
		//Lore Listeners
		Bukkit.getPluginManager().registerEvents(this.armorLoreListener, this);
		Bukkit.getPluginManager().registerEvents(this.weapLoreListener, this);		
		
		//Modification Listeners
		Bukkit.getPluginManager().registerEvents(this.modListener, this);
		Bukkit.getPluginManager().registerEvents(this.modCraftListener, this);

	}
	@Override
	public void onDisable() {
		HashMap<Block, Inventory> modChests = modCraftListener.getActiveModChests();
		Iterator<Block> entryIterator = modChests.keySet().iterator();
		while(entryIterator.hasNext()) {
			Block b = entryIterator.next();
			Inventory inv = modChests.get(b);
			Location loc = b.getLocation();
			loc.getWorld().dropItemNaturally(loc, inv.getItem(1));
			loc.getWorld().dropItemNaturally(loc, inv.getItem(2));
		}
	}
	
	/**
	 * Gets the mod manager, which handles mod loading/storage
	 * @return the Mod Manager
	 */
	public ModManager getModManager() {
		return modManager;
	}

	/**
	 * @param modManager the modManager to set
	 */
	private void setModManager(ModManager modManager) {
		this.modManager = modManager;
	}

	/**
	 * @return the Random Number Generator used by this plugin
	 */
	public Random getRand() {
		return rand;
	}
	

}
