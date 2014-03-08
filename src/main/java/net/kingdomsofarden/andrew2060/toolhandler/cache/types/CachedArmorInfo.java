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

public class CachedArmorInfo extends CachedItemInfo {
    private double quality;
    private String qualityFormat;
    private double magicResist;
    private double healBonus;
    private double protBonus;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;
    
    public CachedArmorInfo(ItemStack item, double quality, double magicResist, double healBonus, int protBonus) {
        this(item,quality,magicResist,healBonus,protBonus,new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedArmorInfo(ItemStack item, double quality, double magicResist, double healBonus, double protBonus, UUID[] mods) {
        this.qualityFormat = FormattingUtil.getWeaponQualityFormat(quality);
        this.quality = quality;
        this.setMagicResist(magicResist);
        this.setHealBonus(healBonus);
        this.setProtBonus(protBonus);
        this.setItem(item);
        this.setMods(mods);
        this.dF = new DecimalFormat("##.##");
    }
    public double getQuality() {
        return quality;
    }
    public void setQuality(double quality) throws ItemStackChangedException {
        this.quality = quality;
        String newFormat = FormattingUtil.getArmorQualityFormat(quality);
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
        String newFormat = FormattingUtil.getArmorQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            ItemStack written = this.forceWrite(true);
            if(written != this.item) {
                throw new ItemStackChangedException(written);
            }
        }
        return quality;
    }
    public double getMagicResist() {
        return magicResist;
    }
    public void setMagicResist(double magicResist) {
        this.magicResist = magicResist;
    }
    public double getHealBonus() {
        return healBonus;
    }
    public void setHealBonus(double healBonus) {
        this.healBonus = healBonus;
    }
    public double getProtBonus() {
        return protBonus;
    }
    public void setProtBonus(double bonus) {
        this.protBonus = bonus;
    }
    public ItemStack getItem() {
        return item;
    }
    public void setItem(ItemStack item) {
        this.item = item;
    }
    public UUID[] getMods() {
        return mods;
    }
    public void setMods(UUID[] mods) {
        this.mods = mods;
    }    
    public ItemStack forceWrite(boolean removeOldFromCache) {
        try {
            NbtUtil.writeAttributes(item, this);
        } catch (ItemStackChangedException e) {
            if(removeOldFromCache) {
                Bukkit.getScheduler().runTaskLater(ToolHandlerPlugin.instance, new Runnable() {
    
                    @Override
                    public void run() {
                        ToolHandlerPlugin.instance.getCacheManager().invalidateFromArmorCache(item);                   
                    }
                    
                }, 1);
            }
            return e.newStack;
        }
        return this.item;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dF.format(quality));
        sb.append(":");
        sb.append(dF.format(magicResist));
        sb.append(":");
        sb.append(dF.format(healBonus));
        sb.append(":");
        sb.append(protBonus);
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }
    public static CachedArmorInfo getDefault(ItemStack is) {
        return new CachedArmorInfo(is,0,0,0,0);
    }
    public static CachedArmorInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        double magicResist = Double.valueOf(parsed[1]);
        double healBonus = Double.valueOf(parsed[2]);
        double protBonus = Double.valueOf(parsed[3]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 4; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedArmorInfo(is,quality,magicResist,healBonus,protBonus,coll.toArray(new UUID[coll.size()]));
    }

}
