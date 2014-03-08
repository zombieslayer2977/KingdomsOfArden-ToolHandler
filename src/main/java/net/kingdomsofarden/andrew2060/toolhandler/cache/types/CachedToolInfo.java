package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import org.bukkit.inventory.ItemStack;

/**
 * Placeholder - not implemented yet
 * @author Andrew
 *
 */

@SuppressWarnings("unused")
public class CachedToolInfo extends CachedItemInfo {
    
    private double quality;
    private String qualityFormat;
    private double trueDamage;
    private double bashChance;
    private double decimatingStrikeChance;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;    

    @Override
    public double getQuality() {
        return 0;
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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack forceWrite(boolean removeOldFromCache) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getNumBonusSlots() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ItemStack addModSlot() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack addMod(ItemMod mod) {
        // TODO Auto-generated method stub
        return null;
    }

}
