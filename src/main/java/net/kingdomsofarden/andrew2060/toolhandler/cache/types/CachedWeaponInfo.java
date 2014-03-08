package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedWeaponInfo extends CachedItemInfo {
    
    private double quality;
    private String qualityFormat;
    private double bonusDamage;
    private double lifeSteal;
    private double critChance;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;
    
    public CachedWeaponInfo(ItemStack item, double quality, double bonusDamage,double lifeSteal, double critChance) {
        this(item,quality,bonusDamage,lifeSteal,critChance,new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedWeaponInfo(ItemStack item, double quality, double bonusDamage,double lifeSteal, double critChance, UUID[] mods) {
        this.qualityFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        this.quality = quality;
        this.setBonusDamage(bonusDamage);
        this.setLifeSteal(lifeSteal);
        this.setCritChance(critChance);
        this.setItem(item);
        this.setMods(mods);
        this.dF = new DecimalFormat("##.##");
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) throws ItemStackChangedException {
        this.quality = quality;
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            ItemStack written = this.forceWrite(true);
            if(written != this.item) {
                throw new ItemStackChangedException(written);
            }
        }
    }

    public final double reduceQuality() throws ItemStackChangedException { 
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
            return quality;
        }
        if(quality < 0) {
            quality = 0;
        }
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            ItemStack written = this.forceWrite(true);
            if(written != this.item) {
                throw new ItemStackChangedException(written);
            }
        }
        return quality;
    }
    
    public double getBonusDamage() {
        return bonusDamage;
    }

    public void setBonusDamage(double bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public double getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(double lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public double getCritChance() {
        return critChance;
    }

    public void setCritChance(double critChance) {
        this.critChance = critChance;
    }

    public ItemStack getItem() {
        return item;
    }

    private void setItem(ItemStack item) {
        this.item = item;
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
    
    private void setMods(UUID[] mods) {
        this.mods = mods;
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
    
    public ItemStack forceWrite(boolean removeOldFromCache) {
        try {
            NbtUtil.writeAttributes(item, this);
        } catch (ItemStackChangedException e) {
            if(removeOldFromCache) {
                Bukkit.getScheduler().runTaskLater(ToolHandlerPlugin.instance, new Runnable() {
    
                    @Override
                    public void run() {
                        ToolHandlerPlugin.instance.getCacheManager().invalidateFromWeaponCache(item);                   
                    }
                    
                }, 1);
            }
            return e.newStack;
        }
        return this.item;
    }
    
    
    
}
