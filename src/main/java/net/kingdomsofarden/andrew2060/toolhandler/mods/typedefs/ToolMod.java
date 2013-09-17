package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ToolMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	public ToolMod(String name, int weight, boolean requiresSlot, String... desc) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
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
