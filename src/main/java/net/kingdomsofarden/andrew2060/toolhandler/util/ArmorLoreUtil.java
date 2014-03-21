package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorLoreUtil {
    
    public static void write(CachedArmorInfo data, ItemStack item) {
        ToolHandlerPlugin plugin = ToolHandlerPlugin.instance;
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new LinkedList<String>();
        double magicResist = 0.00;
        double kbResist = 0.00;
        double protBonus = 0.00;
        switch(item.getType()) { //All chainmail has +10% base magic resist
        
        case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: {
            magicResist += 10;
            break;
        }
        default: {
            break;
        }
        
        }
        
        for(UUID id : data.getMods()) {
            ArmorMod mod = plugin.getModManager().getArmorMod(id);
            if(mod != null) {
                magicResist += mod.getMagicResist();
                kbResist += mod.getKnockbackResist();
                protBonus += mod.getProtBonus();
            }
        }
        lore.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        lore.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getArmorQualityFormat(data.getQuality()));
        lore.add(2,ChatColor.GRAY + "Magical Resistance Rating: " + FormattingUtil.getAttribute(magicResist) + "%");
        lore.add(3,ChatColor.GRAY + "Knockback Resistance Rating: " + FormattingUtil.getAttribute(kbResist) + "%");
        lore.add(4,ChatColor.GRAY + "Additional Protection: " + FormattingUtil.getAttribute(protBonus) + "%");
        lore.add(5,ChatColor.WHITE + "========Modifications========");
        int usedSlots = 0;
        int addedBlankSlots = 0;
        int baseBlankSlots = 0;
        for(UUID id : data.getMods()) {
            ArmorMod mod = plugin.getModManager().getArmorMod(id);
            if(mod == null) {
                if(id.equals(EmptyModSlot.bonusId)) {
                    addedBlankSlots++;
                    continue;
                }
                if(id.equals(EmptyModSlot.baseId)) {
                    baseBlankSlots++;
                }
                continue;
            }
            if(usedSlots > 1 || !mod.isSlotRequired()) {
                lore.add(ChatColor.MAGIC + "" + ChatColor.RESET + "" + ChatColor.GOLD + mod.getName());
            } else {
                lore.add(ChatColor.GOLD + mod.getName());
            }
            if(mod.getMagicResist() != null && mod.getMagicResist() != Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getMagicResist()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getMagicResist())
                        + ChatColor.GRAY + "% Magic Resist");
            }
            if(mod.getKnockbackResist() != null && mod.getKnockbackResist() != Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getKnockbackResist()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getKnockbackResist())
                        + ChatColor.GRAY + "% Knockback Resist");
            }
            if(mod.getProtBonus() != null && mod.getProtBonus() != Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getProtBonus()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getProtBonus())
                        + ChatColor.GRAY + "% Bonus Protection");
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
        item.setItemMeta(meta);
    }
}
