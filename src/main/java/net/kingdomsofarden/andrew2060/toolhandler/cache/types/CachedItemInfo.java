package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import org.bukkit.inventory.ItemStack;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;

public abstract class CachedItemInfo {
  
    protected boolean invalidated;
    protected ItemStack item;
    protected ToolHandlerPlugin plugin;

    public CachedItemInfo(ToolHandlerPlugin plugin, ItemStack item) {
        this.invalidated = false;
        this.item = item;
        this.plugin = plugin;
    }
    public boolean isInvalidated() {
        return this.invalidated;
    }

    public abstract double getQuality();
    public abstract ItemStack setQuality(double quality);
    public abstract ItemStack reduceQuality();
    public abstract int getNumBonusSlots();
    public abstract ItemStack addModSlot();
    public abstract ItemStack addMod(ItemMod mod);

    @Override
    public abstract String toString();

    public ItemStack forceWrite() {
        if(this.invalidated) {
            this.item = plugin.getCacheManager().getCachedInfo(this.item).forceWrite();
            return this.item;
        }
        ItemStack retValue = NbtUtil.writeAttributes(item, this);
        if(retValue != item) {
            this.item = retValue;
            this.invalidated = true;
        }
        return this.item;
    }


}
