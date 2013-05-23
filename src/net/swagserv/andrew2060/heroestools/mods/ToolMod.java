package net.swagserv.andrew2060.heroestools.mods;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public abstract class ToolMod {
	private String desc;
	private String name;
	private int weight;
	public ToolMod(String name, String desc, int weight) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
	}
	public abstract void applyToTool(ItemStack tool);
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
