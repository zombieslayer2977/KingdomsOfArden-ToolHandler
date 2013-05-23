package net.swagserv.andrew2060.heroestools.mods;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class WeaponMod {
	private String desc;
	private String name;
	private int weight;

	public WeaponMod(String name, String desc, int weight) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
	}
	public abstract void applyToWeapon(ItemStack weapon);
	public abstract void executeOnWeaponDamage(WeaponDamageEvent event);
	public String getName() {
		return name;
	}
	public String getDescription() {
		return desc;
	}
	public int getWeight() {
		return weight;
	}
}
