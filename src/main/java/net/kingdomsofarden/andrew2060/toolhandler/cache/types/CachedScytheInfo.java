package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;

import org.bukkit.inventory.ItemStack;

public class CachedScytheInfo extends CachedItemInfo {

    public CachedScytheInfo(ItemStack item) {
        super(ToolHandlerPlugin.instance, item);
        // TODO Auto-generated constructor stub
    }

    @Override
    public double getQuality() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ItemStack setQuality(double quality) {
        return null;
        // TODO Auto-generated method stub
        
    }

    @Override
    public ItemStack reduceQuality() {
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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    public static CachedScytheInfo getDefault(ItemStack is) {
        // TODO Auto-generated method stub
        return null;
    }

    public static CachedScytheInfo fromString(ItemStack is, String parseable) {
        // TODO Auto-generated method stub
        return null;
    }


}
