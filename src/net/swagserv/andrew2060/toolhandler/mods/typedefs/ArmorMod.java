package net.swagserv.andrew2060.toolhandler.mods.typedefs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ArmorMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	public ArmorMod(String name, String[] desc, int weight, boolean requiresSlot) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
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
   
	public abstract void applyOnTick(Player p);
}
