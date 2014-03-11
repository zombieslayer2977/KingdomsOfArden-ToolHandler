package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedWeaponInfo extends CachedItemInfo {

    private double quality;
    private String qualityFormat;
    private double bonusDamage;
    private double lifeSteal;
    private double critChance;
    private UUID[] mods;
    private DecimalFormat dF;

    public CachedWeaponInfo(ItemStack item, double quality, double bonusDamage,double lifeSteal, double critChance) {
        this(item,quality,bonusDamage,lifeSteal,critChance,new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedWeaponInfo(ItemStack item, double quality, double bonusDamage,double lifeSteal, double critChance, UUID[] mods) {
        super(ToolHandlerPlugin.instance,item);
        this.qualityFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        this.quality = quality;
        this.bonusDamage = bonusDamage;
        this.lifeSteal = lifeSteal;
        this.critChance = critChance;
        this.mods = mods;
        this.dF = new DecimalFormat("##.##");
    }

    public double getQuality() {
        return quality;
    }

    public ItemStack setQuality(double quality) {
        this.quality = quality;
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.forceWrite();
        }
        return item;
    }

    public final ItemStack reduceQuality() { 
        int unbreakinglevel = item.getEnchantmentLevel(Enchantment.DURABILITY)+1;
        switch(ImprovementUtil.getItemType(item)) {
        case DIAMOND: 
            quality -= 0.1/unbreakinglevel;
            break;
        case IRON_INGOT:
            quality -= 0.2/unbreakinglevel;
            break;
        case GOLD_INGOT:
            quality -= 1.0/unbreakinglevel;
            break;
        case LEATHER: 
            quality -= 0.8/unbreakinglevel;
            break;
        case STONE: 
            quality -= 0.5/unbreakinglevel;
            break;
        case BOW:
            quality -= 0.5/unbreakinglevel;
            break;
        default: 
            System.err.println("Material Sent to Reduce Quality is Invalid");
            System.err.println("-" + item.toString());
            return item;
        }
        if(quality < 0) {
            quality = 0;
        }
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.forceWrite();
        }
        return item;
    }

    public double getBonusDamage() {
        return bonusDamage;
    }


    public double getLifeSteal() {
        return lifeSteal;
    }

    public double getCritChance() {
        return critChance;
    }

    public ItemStack getItem() {
        return item;
    }

    public static CachedWeaponInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        double bonusDamage = Double.valueOf(parsed[1]);
        double lifeSteal = Double.valueOf(parsed[2]);
        double critChance = Double.valueOf(parsed[3]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 4; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedWeaponInfo(is,quality,bonusDamage,lifeSteal,critChance,coll.toArray(new UUID[coll.size()]));
    }

    public static CachedWeaponInfo getDefault(ItemStack is) {
        return new CachedWeaponInfo(is,0.00,0.00,0.00,0.00);
    }

    public UUID[] getMods() {
        return mods;
    }

    /** 
     * Attempts to add a mod to the cached weapon object
     * @param mod Mod to add
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack), null if no space
     */
    public ItemStack addMod(ItemMod mod) {
        if(!(mod instanceof WeaponMod)) {
            throw new IllegalArgumentException("This is not a weapon mod!");
        }
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i] == EmptyModSlot.baseId || this.mods[i] == EmptyModSlot.bonusId) {
                this.mods[i] = mod.modUUID;
                return this.forceWrite();
            }
        }
        return null;
    }

    /**
     * Adds a mod slot to the cached weapon object
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack)
     */
    public ItemStack addModSlot() {
        this.mods = Arrays.copyOf(this.mods, this.mods.length + 1);
        this.mods[this.mods.length] = EmptyModSlot.bonusId;
        return this.forceWrite();
    }

    public int getNumBonusSlots() {
        int bonusSlots = 0;
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.bonusId)) {
                bonusSlots++;
            }
        }
        return bonusSlots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dF.format(quality));
        sb.append(":");
        sb.append(dF.format(bonusDamage));
        sb.append(":");
        sb.append(dF.format(lifeSteal));
        sb.append(":");
        sb.append(dF.format(critChance));
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }
}
