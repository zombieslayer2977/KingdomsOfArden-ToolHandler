package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedScytheInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedToolInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.AttributeStorage;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.Attributes;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.Attributes.Attribute;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.Attributes.AttributeType;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



public class NbtUtil {
    
    private static UUID itemTracker = UUID.fromString("93b69e80-b191-11e3-a5e2-0800200c9a66");

    public static ItemStack writeAttributes(ItemStack write, CachedItemInfo data) {
        ItemStack item = write;
        String itemName = item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : null;
        UUID id = getItemId(item);
        if(id == null) {
            item = setItemId(UUID.randomUUID(),item);
        }
        String cachedData = data.toString(); 
        AttributeStorage storage = AttributeStorage.newTarget(item, ToolHandlerPlugin.identifier);
        storage.setData(ToolHandlerPlugin.version.toString() + "|||" + cachedData);
        ItemStack written = storage.getTarget();
        switch(item.getType()) {
        
        case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
            WeaponLoreUtil.write((CachedWeaponInfo)data, written);
            break;
        }
        case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
            ArmorLoreUtil.write((CachedArmorInfo)data, written);
            break;
        }
        case DIAMOND_AXE: case DIAMOND_PICKAXE: case DIAMOND_SPADE: case IRON_AXE: case IRON_PICKAXE: case IRON_SPADE: case GOLD_AXE: case GOLD_PICKAXE: case GOLD_SPADE: case STONE_AXE: case STONE_PICKAXE: case STONE_SPADE: case WOOD_AXE: case WOOD_PICKAXE: case WOOD_SPADE: {
            ToolLoreUtil.write((CachedToolInfo)data,written);
            break;
        }
        case DIAMOND_HOE: case IRON_HOE: case GOLD_HOE: case STONE_HOE: case WOOD_HOE: {
            ScytheLoreUtil.write((CachedScytheInfo)data, written);
            break;
        }
        default: {
            break;
        }
        
        }
        if(itemName != null) {
            ItemMeta meta = written.getItemMeta();
            meta.setDisplayName(itemName);
            written.setItemMeta(meta);
        }
        return written;
    }

    public static ItemStack writeKnockbackResist(ItemStack item, double resist) {
        Attributes attrib = new Attributes(item);
        attrib.add(Attribute.newBuilder().name("ToolHandler_KnockbackResist").type(AttributeType.GENERIC_KNOCKBACK_RESISTANCE).amount(resist).build());
        ItemStack written = attrib.getStack();
        return written;
    }
    
    public static String getAttributes(ItemStack item) {
        AttributeStorage storage = AttributeStorage.newTarget(item, ToolHandlerPlugin.identifier);
        String cachedDeserialized = storage.getData();
        String[] parsed = cachedDeserialized == null ? null : cachedDeserialized.split("\\|\\|\\|");
        String uuidRepresentation = cachedDeserialized == null ? null : parsed[0];
        String data = null;
        try {
            UUID id = cachedDeserialized == null ? null : UUID.fromString(uuidRepresentation);
            if((!(id == null)) && id.equals(ToolHandlerPlugin.version)) {
                data = cachedDeserialized == null ? null : parsed[1];
            } else {
                data = SerializationUtil.deserializeFromLore(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        }
        //Plugin tag is not present - rebuild   
        if(data == null) {
            data = SerializationUtil.deserializeFromLore(item);
            if(data == null) {
                throw new IllegalArgumentException("Item " + item.getType().toString() + " is not a valid item!");
            }
        }
        return data;
    }
    
    public static UUID getItemId(ItemStack item) {
        AttributeStorage storage = AttributeStorage.newTarget(item, itemTracker);
        String data = storage.getData();
        return data == null ? null : UUID.fromString(data);
    }
    
    public static ItemStack setItemId(UUID id, ItemStack item) {
        AttributeStorage storage = AttributeStorage.newTarget(item, itemTracker);
        storage.setData(id.toString());
        return storage.getTarget();
    }
    
}

