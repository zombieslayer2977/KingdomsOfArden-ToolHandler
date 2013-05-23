package net.swagserv.andrew2060.heroestools;

import net.swagserv.andrew2060.heroestools.listeners.crafting.CraftingListener;
import net.swagserv.andrew2060.heroestools.listeners.crafting.ShiftClickListener;
import net.swagserv.andrew2060.heroestools.listeners.durability.ArmorDurabilityChangeListener;
import net.swagserv.andrew2060.heroestools.listeners.durability.ToolDurabilityChangeListener;
import net.swagserv.andrew2060.heroestools.listeners.durability.WeaponDurabilityChangeListener;
import net.swagserv.andrew2060.heroestools.listeners.lore.ArmorLoreListener;
import net.swagserv.andrew2060.heroestools.listeners.lore.WeaponLoreListener;
import net.swagserv.andrew2060.heroestools.mods.ModManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin{
	
	private ModManager modManager;
	
	public void onEnable() {
		//Register listeners used by plugin
		registerListeners();
		
		//Initialize Mod Manager 
		setModManager(new ModManager(this));
		
	}

	private void registerListeners() {
		//Durability Based Prot/Sharpness/Efficiency Listeners
		Bukkit.getPluginManager().registerEvents(new ArmorDurabilityChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new WeaponDurabilityChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new ToolDurabilityChangeListener(), this);
		
		//Crafting Listeners
		Bukkit.getPluginManager().registerEvents(new CraftingListener(), this);
		Bukkit.getPluginManager().registerEvents(new ShiftClickListener(this), this);
		
		//Lore Listeners
		Bukkit.getPluginManager().registerEvents(new ArmorLoreListener(), this);
		Bukkit.getPluginManager().registerEvents(new WeaponLoreListener(), this);		
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
	public void setModManager(ModManager modManager) {
		this.modManager = modManager;
	}
	

}
