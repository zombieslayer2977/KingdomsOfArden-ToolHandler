package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ArmorMod {
    protected PotionEffectManager pEMan;
    private String[] desc;
    private String name;
    private int weight;
    private boolean requiresSlot;
    private LinkedList<PotionEffect> effectsOnTick;
    private Double magicResist;
    private Double healingBonus;
    private Double protBonus;
    public UUID modUUID;
    
    public ArmorMod(UUID modUUID, String name, int weight, boolean requiresSlot, String... desc) {
        this.modUUID = modUUID;
        this.pEMan = ToolHandlerPlugin.instance.getPotionEffectHandler();
        this.name = name;
        this.desc = desc;
        this.weight = weight;
        this.requiresSlot = requiresSlot;
        this.effectsOnTick = new LinkedList<PotionEffect>();
        this.magicResist = null;
        this.healingBonus = null;
        this.protBonus = null;
    }
    public void applyToArmor(ItemStack armor) {
        CachedArmorInfo cachedArmor = ToolHandlerPlugin.instance.getCacheManager().getCachedArmorInfo(armor);
        cachedArmor.setMagicResist(cachedArmor.getMagicResist() + this.magicResist);
        cachedArmor.setHealBonus(cachedArmor.getHealBonus() + this.healingBonus);
        cachedArmor.setProtBonus(cachedArmor.getProtBonus() + this.protBonus);
        cachedArmor.forceWrite(true);
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
    public Double getHealingBonus() {
        return healingBonus;
    }
    public void setHealingBonus(Double healingBonus) {
        this.healingBonus = healingBonus;
    }
    public Double getProtBonus() {
        return protBonus;
    }
    public void setProtBonus(Double protBonus) {
        this.protBonus = protBonus;
    }
}
