package net.kingdomsofarden.andrew2060.toolhandler.cache;

import java.util.concurrent.TimeUnit;

import org.bukkit.inventory.ItemStack;

import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedScytheInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedToolInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import net.minecraft.util.com.google.common.cache.RemovalListener;
import net.minecraft.util.com.google.common.cache.RemovalNotification;

public class CacheManager {
    private LoadingCache<CacheKey, CachedWeaponInfo> weaponCache;
    private LoadingCache<CacheKey, CachedArmorInfo> armorCache;
    private LoadingCache<CacheKey, CachedToolInfo> toolCache;
    private LoadingCache<CacheKey, CachedScytheInfo> scytheCache;

    public CacheManager() {
        weaponCache = CacheBuilder.newBuilder()
                .expireAfterAccess(10,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<CacheKey, CachedWeaponInfo>() {

                    @Override
                    public CachedWeaponInfo load(CacheKey is) {
                        String parseable = NbtUtil.getAttributes(is.getItem());
                        if(parseable == null) {
                            //This should never be called, it is merely here as a just in case
                            return CachedWeaponInfo.getDefault(is.getItem());
                        } else {
                            return CachedWeaponInfo.fromString(is.getItem(), parseable);
                        }
                    }

                });
        armorCache = CacheBuilder.newBuilder()
                .expireAfterAccess(10,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<CacheKey, CachedArmorInfo>() {

                    @Override
                    public CachedArmorInfo load(CacheKey is) {
                        String parseable = NbtUtil.getAttributes(is.getItem());
                        if(parseable == null) {
                            //This should never be called, it is merely here as a just in case
                            return CachedArmorInfo.getDefault(is.getItem());
                        } else {
                            return CachedArmorInfo.fromString(is.getItem(), parseable);
                        }
                    }

                });
        toolCache = CacheBuilder.newBuilder()
                .expireAfterAccess(10,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<CacheKey, CachedToolInfo>() {

                    @Override
                    public CachedToolInfo load(CacheKey is) {
                        String parseable = NbtUtil.getAttributes(is.getItem());
                        if(parseable == null) {
                            return CachedToolInfo.getDefault(is.getItem());
                        } else {
                            return CachedToolInfo.fromString(is.getItem(), parseable);
                        }
                    }

                });
        scytheCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .expireAfterAccess(10,TimeUnit.MINUTES)
                .removalListener(new CacheItemRemovalListener())
                .build(new CacheLoader<CacheKey, CachedScytheInfo>() {

                    @Override
                    public CachedScytheInfo load(CacheKey is) {
                        String parseable = NbtUtil.getAttributes(is.getItem());
                        if(parseable == null) {
                            return CachedScytheInfo.getDefault(is.getItem());
                        } else {
                            return CachedScytheInfo.fromString(is.getItem(), parseable);
                        }
                    }

                });
    }

    public class CacheItemRemovalListener implements RemovalListener<CacheKey,CachedItemInfo> {

        @Override
        public void onRemoval(RemovalNotification<CacheKey, CachedItemInfo> removal) {
            removal.getValue().forceWrite(false);
        }

    }

    public CachedWeaponInfo getCachedWeaponInfo(ItemStack is) {
        short durability = is.getDurability();
        is.setDurability((short) 0);
        CachedWeaponInfo cached = weaponCache.getUnchecked(new CacheKey(is));
        while(cached.isInvalidated()) {
            cached = getCachedWeaponInfo(cached.getItem());
        }
        is.setDurability(durability);
        return cached;
    }

    public CachedArmorInfo getCachedArmorInfo(ItemStack is) {
        short durability = is.getDurability();
        CachedArmorInfo cached = armorCache.getUnchecked(new CacheKey(is));
        if(cached.isInvalidated()) {
            cached = getCachedArmorInfo(cached.getItem());
        }
        is.setDurability(durability);
        return cached;
    }


    public CachedToolInfo getCachedToolInfo(ItemStack is) {
        short durability = is.getDurability();
        CachedToolInfo cached = toolCache.getUnchecked(new CacheKey(is));
        if(cached.isInvalidated()) {
            cached = getCachedToolInfo(cached.getItem());
        }
        is.setDurability(durability);
        return cached;
    }

    public CachedScytheInfo getCachedScytheInfo(ItemStack is) {
        short durability = is.getDurability();
        CachedScytheInfo cached = scytheCache.getUnchecked(new CacheKey(is));
        if(cached.isInvalidated()) {
            cached = getCachedScytheInfo(cached.getItem());
        }
        is.setDurability(durability);
        return cached;
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

    private class CacheKey {
        private final ItemStack item;
        public CacheKey(ItemStack item) {
            this.item = item;
        }
        public ItemStack getItem() {
            return item;
        }

        @Override
        public int hashCode() {
            return this.item.hashCode();
        }

        @Override
        public boolean equals(Object cmp) {
            CacheKey obj = (CacheKey)cmp;
            if (this.item == obj.getItem()) {
                return true;
            }
            return this.item.equals(obj.getItem());
        }
    }
}
