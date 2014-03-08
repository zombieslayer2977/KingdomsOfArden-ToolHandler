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
	private Double trueDamage;
	private Double bashChance;
	private Double decimateChance;
	
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

    public Double getTrueDamage() {
        return trueDamage;
    }

    public void setTrueDamage(double trueDamage) {
        this.trueDamage = trueDamage;
    }

    public Double getBashChance() {
        return bashChance;
    }

    public void setBashChance(double bashChance) {
        this.bashChance = bashChance;
    }

    public Double getDecimateChance() {
        return decimateChance;
    }

    public void setDecimateChance(double decimateChance) {
        this.decimateChance = decimateChance;
    }
}
