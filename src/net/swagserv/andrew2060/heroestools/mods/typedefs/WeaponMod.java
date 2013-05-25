package net.swagserv.andrew2060.heroestools.mods.typedefs;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class WeaponMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;

	public WeaponMod(String name, String[] desc, int weight, boolean requiresSlot) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
	}
	public WeaponMod(String name, String descr, int weight, boolean requiresSlot) {
		this(name, new String[] {descr}, weight, requiresSlot);
	}
	public abstract void applyToWeapon(ItemStack weapon);
	public abstract void executeOnWeaponDamage(WeaponDamageEvent event);
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
	
}
