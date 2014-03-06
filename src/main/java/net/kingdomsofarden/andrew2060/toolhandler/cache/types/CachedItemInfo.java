package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import org.bukkit.inventory.ItemStack;

import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

public abstract class CachedItemInfo {
    
    
    public abstract double getQuality();
    public abstract void setQuality(double quality) throws ItemStackChangedException;
    public abstract double reduceQuality() throws ItemStackChangedException;

    
    
    @Override
    public abstract String toString();
    public abstract ItemStack forceWrite(boolean removeOldFromCache);
    
}
