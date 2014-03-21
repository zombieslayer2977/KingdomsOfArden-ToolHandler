package net.kingdomsofarden.andrew2060.toolhandler.cache;

import java.util.concurrent.TimeUnit;

import org.bukkit.inventory.ItemStack;

import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedScytheInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedToolInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class CacheManager {
    private Cache<ItemStack,CachedWeaponInfo> weaponCache;
    private Cache<ItemStack,CachedArmorInfo> armorCache;
    private Cache<ItemStack, CachedToolInfo> toolCache;
    private Cache<ItemStack, CachedScytheInfo> scytheCache;

    public CacheManager() {
        weaponCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .weakKeys()
                .expireAfterAccess(3,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<ItemStack, CachedWeaponInfo>() {

                    @Override
                    public CachedWeaponInfo load(ItemStack is) {
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
                    public CachedArmorInfo load(ItemStack is) {
                        String parseable = NbtUtil.getAttributes(is);
                        if(parseable == null) {
                            //This should never be called, it is merely here as a just in case
                            return CachedArmorInfo.getDefault(is);
                        } else {
                            return CachedArmorInfo.fromString(is, parseable);
                        }
                    }

                });
        toolCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .weakKeys()
                .expireAfterAccess(3,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<ItemStack, CachedToolInfo>() {

                    @Override
                    public CachedToolInfo load(ItemStack is) {
                        String parseable = NbtUtil.getAttributes(is);
                        if(parseable == null) {
                            return CachedToolInfo.getDefault(is);
                        } else {
                            return CachedToolInfo.fromString(is, parseable);
                        }
                    }

                });
        scytheCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .weakKeys()
                .expireAfterAccess(3,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<ItemStack, CachedScytheInfo>() {

                    @Override
                    public CachedScytheInfo load(ItemStack is) {
                        String parseable = NbtUtil.getAttributes(is);
                        if(parseable == null) {
                            return CachedScytheInfo.getDefault(is);
                        } else {
                            return CachedScytheInfo.fromString(is, parseable);
                        }
                    }

                });
    }

    public class CacheItemRemovalListener implements RemovalListener<ItemStack,CachedItemInfo> {

        @Override
        public void onRemoval(RemovalNotification<ItemStack, CachedItemInfo> removal) {
            switch(removal.getCause()) {

            case EXPIRED: {
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
        return weaponCache.getUnchecked(is);
    }

    public CachedArmorInfo getCachedArmorInfo(ItemStack is) {
        return armorCache.getUnchecked(is);
    }


    public CachedToolInfo getCachedToolInfo(ItemStack is) {
        return toolCache.getUnchecked(is);
    }
    
    public CachedScytheInfo getCachedScytheInfo(ItemStack is) {
        return scytheCache.getUnchecked(is);
    }

    public CachedItemInfo getCachedInfo(ItemStack item) {

        switch(item.getType()) {

        case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
            return getCachedWeaponInfo(item);
        }
        case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
            return getCachedArmorInfo(item);
        }
        case DIAMOND_AXE: case DIAMOND_PICKAXE: case DIAMOND_SPADE: case IRON_AXE: case IRON_PICKAXE: case IRON_SPADE: case GOLD_AXE: case GOLD_PICKAXE: case GOLD_SPADE: case STONE_AXE: case STONE_PICKAXE: case STONE_SPADE: case WOOD_AXE: case WOOD_PICKAXE: case WOOD_SPADE: {
            return getCachedToolInfo(item);
        }
        case DIAMOND_HOE: case IRON_HOE: case GOLD_HOE: case STONE_HOE: case WOOD_HOE: {
            return getCachedScytheInfo(item);
        }
        default: {
            return null;
        }

        }
    }



}
