package net.kingdomsofarden.andrew2060.toolhandler.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.bukkit.inventory.ItemStack;

import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class CacheManager {
    private Cache<ItemStack,CachedWeaponInfo> weaponCache;
    private Cache<ItemStack,CachedArmorInfo> armorCache;

    public CacheManager() {
        weaponCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .weakKeys()
                .expireAfterAccess(3,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<ItemStack, CachedWeaponInfo>() {

                    @Override
                    public CachedWeaponInfo load(ItemStack is) throws ItemStackChangedException {
                        String parseable = NbtUtil.getAttributes(is);
                        if(parseable == null) {
                            //This should never be called, it is merely here as a just in case
                            return CachedWeaponInfo.getDefault(is);
                        } else {
                            return CachedWeaponInfo.fromString(is, parseable);
                        }
                    }
                    
                });
        armorCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .weakKeys()
                .expireAfterAccess(3,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<ItemStack, CachedArmorInfo>() {

                    @Override
                    public CachedArmorInfo load(ItemStack is) throws ItemStackChangedException {
                        String parseable = NbtUtil.getAttributes(is);
                        if(parseable == null) {
                            //This should never be called, it is merely here as a just in case
                            return CachedArmorInfo.getDefault(is);
                        } else {
                            return CachedArmorInfo.fromString(is, parseable);
                        }
                    }
                    
                });
    }
    
    public class CacheItemRemovalListener implements RemovalListener<ItemStack,CachedItemInfo> {

        @Override
        public void onRemoval(RemovalNotification<ItemStack, CachedItemInfo> removal) {
            switch(removal.getCause()) {
            
            case EXPIRED:
            case COLLECTED: {
                removal.getValue().forceWrite(false);
                return;
            }
            default: {
                return;
            }
            
            }
        }
        
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
    
    public CachedArmorInfo getCachedArmorInfo(ItemStack is) {
        
        try {
            return armorCache.get(is);
        } catch (ExecutionException e) {
            if(e.getCause() instanceof ItemStackChangedException) {
                ItemStackChangedException changeException = (ItemStackChangedException)e.getCause();
                return getCachedArmorInfo(changeException.newStack);
            }
            e.printStackTrace();
            return null;
        }
        
    }
    
    public void invalidateFromWeaponCache(ItemStack is) {
        weaponCache.invalidate(is);
    }

    public void invalidateFromArmorCache(ItemStack is) {
        armorCache.invalidate(is);
    }

}
