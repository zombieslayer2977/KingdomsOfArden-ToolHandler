package net.swagserv.andrew2060.heroestools;

import net.swagserv.andrew2060.heroestools.durability.ArmorProtListener;
import net.swagserv.andrew2060.heroestools.durability.CraftingListener;
import net.swagserv.andrew2060.heroestools.durability.ToolEfficiencyListener;
import net.swagserv.andrew2060.heroestools.durability.WeaponSharpnessListener;
import net.swagserv.andrew2060.heroestools.enchantments.ArmorEnchantsListener;
import net.swagserv.andrew2060.heroestools.enchantments.ChainMailEffectsListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin{
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new CraftingListener(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorProtListener(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorEnchantsListener(), this);
		Bukkit.getPluginManager().registerEvents(new WeaponSharpnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new ToolEfficiencyListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChainMailEffectsListener(), this);

	}
	

}
