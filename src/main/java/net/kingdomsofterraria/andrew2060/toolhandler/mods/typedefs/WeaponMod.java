package net.kingdomsofterraria.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;

import net.kingdomsofterraria.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofterraria.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.Bukkit;
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

	public WeaponMod(String name, String[] desc, int weight, boolean requiresSlot) {
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
		this.pEMan = ((ToolHandlerPlugin)Bukkit.getPluginManager().getPlugin("Swagserv-ToolHandler")).getPotionEffectHandler();
		this.applyTargetOnDamage = new LinkedList<PotionEffect>();
		this.applySelfOnDamage = new LinkedList<PotionEffect>();
	}
	public WeaponMod(String name, String descr, int weight, boolean requiresSlot) {
		this(name, new String[] {descr}, weight, requiresSlot);
	}
	public abstract void applyToWeapon(ItemStack weapon);
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
        pEMan.addPotionEffectStacking(applyTargetOnDamage, (LivingEntity) event.getEntity());
        pEMan.addPotionEffectStacking(applySelfOnDamage, event.getDamager().getEntity());
	}
}
