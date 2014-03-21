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

    public CachedWeaponInfo(ItemStack item, double quality) {
        this(item, quality, new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedWeaponInfo(ItemStack item, double quality, UUID[] mods) {
        super(ToolHandlerPlugin.instance,item);
        this.qualityFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        this.quality = quality;
        this.bonusDamage = 0.00;
        this.lifeSteal = 0.00;
        this.critChance = 0.00;
        this.mods = mods;
        for(UUID id : this.mods) {
            WeaponMod mod = plugin.getModManager().getWeaponMod(id);
            if(mod != null) {
                this.bonusDamage += mod.getBonusDamage();
                this.lifeSteal += mod.getLifeSteal();
                this.critChance += mod.getCritChance();
            }
        }
        this.dF = new DecimalFormat("##.##");
    }

    public double getQuality() {
        return quality;
    }

    public ItemStack setQuality(double quality) {
        this.quality = quality;
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.qualityFormat = newFormat;
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
        case WOOD:
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
            this.qualityFormat = newFormat;
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
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 1; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedWeaponInfo(is,quality,coll.toArray(new UUID[coll.size()]));
    }

    public static CachedWeaponInfo getDefault(ItemStack is) {
        return new CachedWeaponInfo(is,0.00);
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
        boolean replaced = false;
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.baseId) || this.mods[i].equals(EmptyModSlot.bonusId)) {
                this.mods[i] = mod.modUUID;
                replaced = true;
                break;
            }
        }
        if(!replaced) {
            return null;
        }
        this.bonusDamage = 0.00;
        this.lifeSteal = 0.00;
        this.critChance = 0.00;
        for(UUID id : this.mods) {
            WeaponMod foundMod = plugin.getModManager().getWeaponMod(id);
            if(foundMod != null) {
                this.bonusDamage += foundMod.getBonusDamage();
                this.lifeSteal += foundMod.getLifeSteal();
                this.critChance += foundMod.getCritChance();
            }
        }
        return this.forceWrite();
    }

    /**
     * Adds a mod slot to the cached weapon object
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack)
     */
    public ItemStack addModSlot() {
        this.mods = Arrays.copyOf(this.mods, this.mods.length + 1);
        this.mods[this.mods.length-1] = EmptyModSlot.bonusId;
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
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }
}
