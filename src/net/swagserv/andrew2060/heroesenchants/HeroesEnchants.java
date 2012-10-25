package net.swagserv.andrew2060.heroesenchants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroesEnchants extends JavaPlugin{
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new CraftingListener(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorProtListener(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorEnchantsListener(), this);
		Bukkit.getPluginManager().registerEvents(new WeaponSharpnessListener(), this);
		Bukkit.getPluginManager().registerEvents(new ToolEfficiencyListener(), this);
		//Register Chain Mail Recipes and Effect Listeners
		Bukkit.getPluginManager().registerEvents(new ChainMailEffectsListener(), this);
		ShapedRecipe chainHelm = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_HELMET, 1));
        chainHelm.shape("III", "L L", "   ")
        .setIngredient('I', Material.IRON_INGOT)
        .setIngredient('L', Material.LEATHER);
		ShapedRecipe chainChest = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1));
        chainChest.shape("L L", "III", "III")
    	.setIngredient('I', Material.IRON_INGOT)
    	.setIngredient('L', Material.LEATHER);
		ShapedRecipe chainLegs = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1));
        chainLegs.shape("LIL", "I I", "I I")
    	.setIngredient('I', Material.IRON_INGOT)
    	.setIngredient('L', Material.LEATHER);
		ShapedRecipe chainBoots = new ShapedRecipe(new ItemStack(Material.CHAINMAIL_BOOTS, 1));
        chainBoots.shape("   ", "L L", "I I")
    	.setIngredient('I', Material.IRON_INGOT)
    	.setIngredient('L', Material.LEATHER);
        Bukkit.addRecipe(chainHelm);
        Bukkit.addRecipe(chainChest);
        Bukkit.addRecipe(chainLegs);
        Bukkit.addRecipe(chainBoots);

	}
	

}
