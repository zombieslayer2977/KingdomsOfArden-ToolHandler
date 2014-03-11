package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponLoreUtil {

    public static void write(CachedWeaponInfo cachedData, ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = new LinkedList<String>();
        lore.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        lore.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getWeaponToolQualityFormat(cachedData.getQuality()));
        lore.add(2,ChatColor.GRAY + "Bonus Damage: " + FormattingUtil.getAttribute(cachedData.getBonusDamage()));
        lore.add(3,ChatColor.GRAY + "Life Steal: " + FormattingUtil.getAttribute(cachedData.getLifeSteal()) + " Health/Hit");
        lore.add(4,ChatColor.GRAY + "Critical Strike Chance: " + FormattingUtil.getAttribute(cachedData.getCritChance()) + "%");
        lore.add(5,ChatColor.WHITE + "========Modifications========");
        int usedSlots = 0;
        int addedBlankSlots = 0;
        int baseBlankSlots = 0;
        for(UUID id : cachedData.getMods()) {
            
            WeaponMod mod = ToolHandlerPlugin.instance.getModManager().getWeaponMod(id);
            if(mod == null) {
                if(id.equals(EmptyModSlot.bonusId)) {
                    addedBlankSlots++;
                    continue;
                }
                if(id.equals(EmptyModSlot.baseId)) {
                    baseBlankSlots++;
                    usedSlots ++;
                }
                continue;
            }
            if(usedSlots > 1 || !mod.isSlotRequired()) {
                lore.add(ChatColor.MAGIC + "" + ChatColor.RESET + "" + ChatColor.GOLD + mod.getName());
            } else {
                lore.add(ChatColor.GOLD + mod.getName());
            }
            if(mod.getBonusDamage() != null && mod.getBonusDamage() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getBonusDamage()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getBonusDamage())
                        + ChatColor.GRAY + " Damage");
            }
            if(mod.getLifeSteal() != null && mod.getLifeSteal() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getLifeSteal()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getLifeSteal())
                        + ChatColor.GRAY + "% Life Steal");
            }
            if(mod.getCritChance() != null && mod.getCritChance() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getCritChance()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getCritChance())
                        + ChatColor.GRAY + "% Critical Hit Chance");
            }
            for(String s : mod.getDescription()) {
                lore.add(ChatColor.GRAY + "- " + s);
            }
            if(mod.isSlotRequired()) {
                usedSlots++; 
                continue;
            } else {
                continue;
            }
        }
        for(int i = 0; i < baseBlankSlots; i++) {
            lore.add(EmptyModSlot.baseDesc);
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            lore.add(EmptyModSlot.bonusDesc);
        }
        meta.setLore(lore);
        weapon.setItemMeta(meta);
    }
}
