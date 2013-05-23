package net.swagserv.andrew2060.heroestools.mods;
/**
 * Abstract class defining a basic ArmorMod: individual mods will not be included with the plugin
 * but will rather be loaded in via. the ModLoader  
 * @author Andrew
 *
 */
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class ArmorMod {
	private String desc;
	private String name;
	private int weight;
	public ArmorMod(String name, String desc, int weight) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
	}
	public abstract void applyToArmor(ItemStack armor);
	public abstract void execute(Event event);
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
