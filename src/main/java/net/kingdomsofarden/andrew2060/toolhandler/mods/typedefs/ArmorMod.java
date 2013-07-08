package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ArmorMod {
    protected PotionEffectManager pEMan;
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	private LinkedList<PotionEffect> effectsOnTick;
	public ArmorMod(String name, String[] desc, int weight, boolean requiresSlot) {
	    this.pEMan = ((ToolHandlerPlugin)Bukkit.getPluginManager().getPlugin("Swagserv-ToolHandler")).getPotionEffectHandler();
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
		this.effectsOnTick = new LinkedList<PotionEffect>();
	}
	public ArmorMod(String name, String desc, int weight, boolean requiresSlot) {
		this(name, new String[] {desc}, weight, requiresSlot);
	}
	public abstract void applyToArmor(ItemStack armor);
	public String getName() {
		return name;
	}
	public String[] getDescription() {
		return desc;
	}
	public int getWeight() {
		return weight;
	}
	public boolean isSlotRequired() {
		return requiresSlot;
	}
	public abstract void executeOnArmorDamage(WeaponDamageEvent event);
   
	public abstract void executeOnTick(Player p);
	
	public void addTickEffect(PotionEffect effect) {
	    effectsOnTick.add(effect);
	}
	public void addTickEffect(Collection<PotionEffect> effects) {
	    for(PotionEffect effect: effects) {
	        effectsOnTick.add(effect);
	    }
    }
	//To prevent lag, only tick through players as opposed to all possible living entities
	public void onTick(Player p) {
	    pEMan.addPotionEffectStacking(effectsOnTick, p);
	    executeOnTick(p);
	}
}
