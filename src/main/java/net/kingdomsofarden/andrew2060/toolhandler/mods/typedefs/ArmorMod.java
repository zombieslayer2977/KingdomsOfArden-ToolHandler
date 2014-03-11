package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ArmorMod extends ItemMod {
    protected PotionEffectManager pEMan;
    private String[] desc;
    private String name;
    private int weight;
    private boolean requiresSlot;
    private LinkedList<PotionEffect> effectsOnTick;
    private LinkedList<PotionEffect> applySelfOnDamage;
    private LinkedList<PotionEffect> applyAttackerOnDamage;
    private Double magicResist;
    private Double knockbackResist;
    private Double protBonus;
    
    public ArmorMod(UUID modUUID, String name, int weight, boolean requiresSlot, String... desc) {
        super(modUUID);
        this.pEMan = ToolHandlerPlugin.instance.getPotionEffectHandler();
        this.name = name;
        this.desc = desc;
        this.weight = weight;
        this.requiresSlot = requiresSlot;
        this.effectsOnTick = new LinkedList<PotionEffect>();
        this.applySelfOnDamage = new LinkedList<PotionEffect>();
        this.applyAttackerOnDamage = new LinkedList<PotionEffect>();

        this.magicResist = null;
        this.knockbackResist = null;
        this.protBonus = null;
    }
    public ItemStack applyToArmor(ItemStack armor) {
        CachedArmorInfo cachedArmor = ToolHandlerPlugin.instance.getCacheManager().getCachedArmorInfo(armor);
        return cachedArmor.forceWrite();
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
    public abstract void executeOnArmorDamage(WeaponDamageEvent event);

    public abstract void executeOnTick(Player p);

    public void addTickEffect(PotionEffect effect) {
        effectsOnTick.add(effect);
    }
    public void addTickEffect(Collection<PotionEffect> effects) {
        for(PotionEffect effect: effects) {
            effectsOnTick.add(effect);
        }
    }
    //To prevent lag, only tick through players as opposed to all possible living entities
    public void onTick(Player p) {
        pEMan.addPotionEffectStacking(effectsOnTick, p, true);
        executeOnTick(p);
    }
    public Double getMagicResist() {
        return magicResist;
    }
    public void setMagicResist(Double magicResist) {
        this.magicResist = magicResist;
    }
    public Double getKnockbackResist() {
        return knockbackResist;
    }
    public void setKnockbackResist(Double knockbackResist) {
        this.knockbackResist = knockbackResist;
    }
    public Double getProtBonus() {
        return protBonus;
    }
    public void setProtBonus(Double protBonus) {
        this.protBonus = protBonus;
    }
    
    public void tickOnArmorDamage(WeaponDamageEvent event) {
        executeOnArmorDamage(event);
        pEMan.addPotionEffectStacking(applyAttackerOnDamage, event.getDamager().getEntity(), false);
        pEMan.addPotionEffectStacking(applySelfOnDamage, (LivingEntity) event.getEntity(), false);
    }
}
