package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;
import net.kingdomsofarden.andrew2060.toolhandler.util.WeaponLoreUtil;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class WeaponMod {
    protected PotionEffectManager pEMan;
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	private LinkedList<PotionEffect> applyTargetOnDamage;
	private LinkedList<PotionEffect> applySelfOnDamage;
	private Double bonusDamage;
	private Double lifeSteal;
	private Double critChance;

	public WeaponMod(String name, int weight, boolean requiresSlot, String... desc) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
		this.pEMan = ToolHandlerPlugin.instance.getPotionEffectHandler();
		this.applyTargetOnDamage = new LinkedList<PotionEffect>();
		this.applySelfOnDamage = new LinkedList<PotionEffect>();
		this.bonusDamage = null;
		this.lifeSteal = null;
		this.critChance = null;
	}
	public void applyToWeapon(ItemStack weapon) {
	    if(bonusDamage != null && bonusDamage > Double.valueOf(0.00)) {
	        WeaponLoreUtil.setBonusDamage(WeaponLoreUtil.getBonusDamage(weapon)+bonusDamage, weapon);
	    }
	    if(lifeSteal != null && lifeSteal > Double.valueOf(0.00)) {
            WeaponLoreUtil.setLifeSteal(WeaponLoreUtil.getLifeSteal(weapon)+lifeSteal, weapon);
        }
	    if(critChance != null && critChance > Double.valueOf(0.00)) {
            WeaponLoreUtil.setCritChance(WeaponLoreUtil.getCritChance(weapon)+critChance, weapon);
        }
	}
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
	public void addPotionEffectsTarget(PotionEffect effect) {
	    this.applyTargetOnDamage.addFirst(effect);
	}
	public void addPotionEffectsTarget(Collection<PotionEffect> effects) {
        for(PotionEffect effect : effects) {
            addPotionEffectsTarget(effect);
        }
    }
	public void addPotionEffectsSelf(PotionEffect effect) {
        this.applySelfOnDamage.addFirst(effect);	    
	}
	public void addPotionEffectsSelf(Collection<PotionEffect> effects) {
        for(PotionEffect effect : effects) {
            addPotionEffectsSelf(effect);
        }
    }
	public void tickMod(WeaponDamageEvent event) {
	    executeOnWeaponDamage(event);
        pEMan.addPotionEffectStacking(applyTargetOnDamage, (LivingEntity) event.getEntity(), false);
        pEMan.addPotionEffectStacking(applySelfOnDamage, event.getDamager().getEntity(), false);
	}
    public Double getBonusDamage() {
        return bonusDamage;
    }
    public void setBonusDamage(Double bonusDamage) {
        this.bonusDamage = bonusDamage;
    }
    public Double getLifeSteal() {
        return lifeSteal;
    }
    public void setLifeSteal(Double lifeSteal) {
        this.lifeSteal = lifeSteal;
    }
    public Double getCritChance() {
        return critChance;
    }
    public void setCritChance(Double critChance) {
        this.critChance = critChance;
    }
	
}
