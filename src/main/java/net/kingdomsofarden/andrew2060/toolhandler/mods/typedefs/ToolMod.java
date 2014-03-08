package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ToolMod extends ItemMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	public ToolMod(UUID modUUID, String name, int weight, boolean requiresSlot, String... desc) {
	    super(modUUID);
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
