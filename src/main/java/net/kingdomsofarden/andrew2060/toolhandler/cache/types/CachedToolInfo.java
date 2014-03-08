package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedToolInfo extends CachedItemInfo {
    
    private double quality;
    private String qualityFormat;
    private double trueDamage;
    private double bashChance;
    private double decimateChance;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;    

    
    public CachedToolInfo(ItemStack item, double quality, double trueDamage, double bashChance, double decimatingStrikeChance) {
        this(item,quality,trueDamage,bashChance,decimatingStrikeChance,new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedToolInfo(ItemStack item, double quality, double trueDamage, double bashChance, double decimatingStrikeChance, UUID[] mods) {
        this.qualityFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        this.quality = quality;
        this.setTrueDamage(trueDamage);
        this.setBashChance(bashChance);
        this.setDecimatingStrikeChance(decimatingStrikeChance);
        this.item = item;
        this.mods = mods;
        this.dF = new DecimalFormat("##.##");
    }
    
    @Override
    public double getQuality() {
        return quality;
    }

    @Override
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

    @Override
    public double reduceQuality() throws ItemStackChangedException {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dF.format(quality));
        sb.append(":");
        sb.append(dF.format(trueDamage));
        sb.append(":");
        sb.append(dF.format(bashChance));
        sb.append(":");
        sb.append(decimateChance);
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }

    @Override
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
    public ItemStack addModSlot() {
        this.mods = Arrays.copyOf(this.mods, this.mods.length + 1);
        this.mods[this.mods.length] = EmptyModSlot.bonusId;
        return forceWrite(true);
    }

    @Override
    public ItemStack addMod(ItemMod mod) {
        if(!(mod instanceof ToolMod)) {
            throw new IllegalArgumentException("This is not a tool mod!");
        }
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.baseId) || this.mods[i].equals(EmptyModSlot.bonusId)) {
                this.mods[i] = mod.modUUID;
                return this.forceWrite(true);
            }
        }
        return null;
    }
    public UUID[] getMods() {
        return this.mods;
    }
    public double getTrueDamage() {
        return trueDamage;
    }
    public void setTrueDamage(double trueDamage) {
        this.trueDamage = trueDamage;
    }
    public double getBashChance() {
        return bashChance;
    }
    public void setBashChance(double bashChance) {
        this.bashChance = bashChance;
    }
    public double getDecimatingStrikeChance() {
        return decimateChance;
    }
    public void setDecimatingStrikeChance(double decimatingStrikeChance) {
        this.decimateChance = decimatingStrikeChance;
    }
    public static CachedToolInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        double trueDamage = Double.valueOf(parsed[1]);
        double bashChance = Double.valueOf(parsed[2]);
        double decimateChance = Double.valueOf(parsed[3]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 4; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedToolInfo(is,quality,trueDamage,bashChance,decimateChance,coll.toArray(new UUID[coll.size()]));
    }
    public static CachedToolInfo getDefault(ItemStack is) {
        return new CachedToolInfo(is,0,0,0,0);
    }

}
