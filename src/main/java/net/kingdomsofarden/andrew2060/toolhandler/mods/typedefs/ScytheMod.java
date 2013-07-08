package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.SkillUseEvent;

public abstract class ScytheMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	public ScytheMod(String name, String[] desc, int weight, boolean requiresSlot) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
	}
	public ScytheMod(String name, String desc, int weight, boolean requiresSlot) {
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
	public abstract void applyToScythe(ItemStack tool);
	public abstract void executeOnSkillUse(SkillUseEvent event);
	public abstract void executeOnSkillDamage(SkillDamageEvent event);
}
