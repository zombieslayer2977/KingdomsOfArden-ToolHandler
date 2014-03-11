package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class WeaponMod extends ItemMod {
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

    public WeaponMod(UUID modUUID, String name, int weight, boolean requiresSlot, String... desc) {
        super(modUUID);
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
    public ItemStack applyToWeapon(ItemStack weapon) {
        CachedWeaponInfo cachedWeapon = ToolHandlerPlugin.instance.getCacheManager().getCachedWeaponInfo(weapon);
        return cachedWeapon.forceWrite();
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
    public void tickOnWeaponDamage(WeaponDamageEvent event) {
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
