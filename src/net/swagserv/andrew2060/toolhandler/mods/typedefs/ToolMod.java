package net.swagserv.andrew2060.toolhandler.mods.typedefs;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ToolMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	public ToolMod(String name, String[] desc, int weight, boolean requiresSlot) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
	}
	public ToolMod(String name, String desc, int weight, boolean requiresSlot) {
		this(name, new String[] {desc}, weight, requiresSlot);
	}
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
	public abstract void executeOnBlockBreak(BlockBreakEvent event);
	public abstract void applyToTool(ItemStack tool);
	public abstract void executeOnWeaponDamage(WeaponDamageEvent event);
}
