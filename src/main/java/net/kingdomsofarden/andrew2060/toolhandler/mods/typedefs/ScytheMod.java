package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.LinkedList;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.SkillUseEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ScytheMod extends ItemMod {
    
    
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
    private PotionEffectManager pEMan;
    private LinkedList<PotionEffect> applySelfOnDamage;
    private LinkedList<PotionEffect> applyTargetOnDamage;
    private LinkedList<PotionEffect> applySelfOnSkillUse;
    private double damageBoost;
    private int manaRestoration;
    private double spellLeech;
    
    
	public ScytheMod(UUID modUUID, String name, int weight, boolean requiresSlot, String... desc) {
	    super(modUUID);
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
        this.pEMan = ToolHandlerPlugin.instance.getPotionEffectHandler();
        this.applySelfOnDamage = new LinkedList<PotionEffect>();
        this.applyTargetOnDamage = new LinkedList<PotionEffect>();
        this.applySelfOnSkillUse = new LinkedList<PotionEffect>();
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
	public void executeOnSkillUse(SkillUseEvent event) {};
	public void executeOnSkillDamage(SkillDamageEvent event) {};
	public void executeOnWeaponDamage(WeaponDamageEvent event) {};
	
	public final void tickOnSkillUse(SkillUseEvent event) {
	    executeOnSkillUse(event);
	    this.pEMan.addPotionEffectStacking(this.applySelfOnSkillUse, event.getPlayer(), false);
	}
	
	public final void tickOnSkillDamage(SkillDamageEvent event) {
        executeOnSkillDamage(event);
        this.pEMan.addPotionEffectStacking(this.applySelfOnDamage, event.getDamager().getEntity(), false);
        if(event.getEntity() instanceof LivingEntity) {
            this.pEMan.addPotionEffectStacking(this.applyTargetOnDamage, (LivingEntity) event.getEntity(), false);
        }

    }
	
	public final void tickOnWeaponDamage(WeaponDamageEvent event) {
        executeOnWeaponDamage(event);
        this.pEMan.addPotionEffectStacking(this.applySelfOnDamage, event.getDamager().getEntity(), false);
        if(event.getEntity() instanceof LivingEntity) {
            this.pEMan.addPotionEffectStacking(this.applyTargetOnDamage, (LivingEntity) event.getEntity(), false);
        }
    }
    public double getDamageBoost() {
        return damageBoost;
    }
    public void setDamageBoost(double damageBoost) {
        this.damageBoost = damageBoost;
    }
    public int getManaRestoration() {
        return manaRestoration;
    }
    public void setManaRestoration(int manaRestoration) {
        this.manaRestoration = manaRestoration;
    }
    public double getSpellLeech() {
        return spellLeech;
    }
    public void setSpellLeech(double spellLeech) {
        this.spellLeech = spellLeech;
    }
	 
	
	
}
