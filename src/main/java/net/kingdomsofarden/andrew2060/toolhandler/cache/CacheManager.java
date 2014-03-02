package net.kingdomsofarden.andrew2060.toolhandler.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.bukkit.inventory.ItemStack;

import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class CacheManager {
    private Cache<ItemStack,CachedWeaponInfo> weaponCache;

    public CacheManager() {
        weaponCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .weakKeys()
                .expireAfterAccess(3,TimeUnit.MINUTES)
                .build(new CacheLoader<ItemStack, CachedWeaponInfo>() {

                    @Override
                    public CachedWeaponInfo load(ItemStack is) throws ItemStackChangedException {
                        String parseable = NbtUtil.getWeaponAttributes(is);
                        if(parseable == null) {
                            //This should never be called, it is merely here as a just in case
                            return CachedWeaponInfo.getDefault(is);
                        } else {
                            return CachedWeaponInfo.fromString(is, parseable);
                        }
                    }
                    
                });
    }
    
    public CachedWeaponInfo getCachedWeaponInfo(ItemStack is) {
        
        try {
            return weaponCache.get(is);
        } catch (ExecutionException e) {
            if(e.getCause() instanceof ItemStackChangedException) {
                ItemStackChangedException changeException = (ItemStackChangedException)e.getCause();
                return getCachedWeaponInfo(changeException.newStack);
            }
            e.printStackTrace();
            return null;
        }
        
    }
    
    public void invalidateFromWeaponCache(ItemStack is) {
        weaponCache.invalidate(is);
    }

}
