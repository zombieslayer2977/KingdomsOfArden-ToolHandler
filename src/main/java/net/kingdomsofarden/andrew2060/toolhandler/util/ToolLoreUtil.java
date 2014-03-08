package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedToolInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolLoreUtil {
    
    /**
     * @deprecated
     * @param tool
     */
    public static int getBonusTrueDamage(ItemStack tool) {
        ItemMeta meta = tool.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(tool);
            meta = tool.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(tool);
            meta = tool.getItemMeta();
            lore = meta.getLore();
        }
        String intParse = ChatColor.stripColor(lore.get(2)).replaceAll("[^.0-9]", "");
        int bonus = Integer.parseInt(intParse);
        return bonus;
    }
    /**
     * @deprecated
     * @param tool
     */
    public static void updateToolLore(ItemStack tool) {
        ItemMeta meta = tool.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(tool);
            return;
        }
        List<String> lore = meta.getLore();
        if(lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            return;
        }
        List<String> modifications = new LinkedList<String>();
        double improvementQuality = 0.00D;
        int bonusDamage = 0;
        double bashChance = 0.00;
        double decimateChance = 0.00;
        //Once this is true, assumes all lore past is modification lore
        boolean reachedmodifications = false;
        for(String line : lore) {
            if(reachedmodifications) {
                modifications.add(line);
            }
            line = ChatColor.stripColor(line);
            if(line.contains("Quality")) {
                try {
                    improvementQuality = Double.parseDouble(line.replaceAll("[^.0-9]", ""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("True")) {
                try {
                    bonusDamage = Integer.parseInt(line.replaceAll("[^.0-9]", ""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Bash")) {
                try {
                    bashChance = Double.parseDouble(line.replaceAll("[^.0-9]", ""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Decimating")) {
                try {
                    decimateChance = Double.parseDouble(line.replaceAll("[^.0-9]", ""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Modifications")) {
                reachedmodifications = true;
            }
        }
        String improvementText = FormattingUtil.getWeaponToolQualityFormat(improvementQuality);
        improvementText = ChatColor.GRAY + "Improvement Quality: " + improvementQuality + "%";
        String truedmgText = FormattingUtil.getAttributeColor(bonusDamage) + bonusDamage + ChatColor.GRAY;
        truedmgText = ChatColor.GRAY + "True Damage: " + truedmgText;
        String bashText = FormattingUtil.getAttributeColor(bashChance) + FormattingUtil.loreDescriptorFormat.format(bashChance) + ChatColor.GRAY;
        bashText = ChatColor.GRAY + "Bash Attack Chance: " + bashText + "%";
        String decimationText = FormattingUtil.getAttributeColor(decimateChance) + FormattingUtil.loreDescriptorFormat.format(decimateChance) + ChatColor.GRAY;
        decimationText = ChatColor.GRAY + "Bash Attack Chance: " + decimationText + "%";
        List<String> loreUpdated = new LinkedList<String>();
        loreUpdated.add(ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        loreUpdated.add(improvementText);
        loreUpdated.add(truedmgText);
        loreUpdated.add(bashText);
        loreUpdated.add(decimationText);
        loreUpdated.add(ChatColor.WHITE + "========Modifications========");
        ModManager modMan = ToolHandlerPlugin.instance.getModManager();
        for(String toAdd : modifications ) {
            if(!toAdd.contains(ChatColor.GOLD +"")) {
                if(toAdd.contains(ChatColor.DARK_GRAY + "")) {
                    loreUpdated.add(toAdd);
                }
                continue;
            } else {
                loreUpdated.add(toAdd);
                toAdd = ChatColor.stripColor(toAdd);
                String modName = toAdd.replace(" ", "");
                ToolMod mod = modMan.getToolMod(modName);
                for(String desc : mod.getDescription()) {
                    loreUpdated.add(ChatColor.GRAY + "- " + desc);
                }
            }
        }
        meta.setLore(loreUpdated);
        tool.setItemMeta(meta);
        return;
        
    }
    public static void write(CachedToolInfo data, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new LinkedList<String>();
        lore.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        lore.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getArmorQualityFormat(data.getQuality()));
        lore.add(2,ChatColor.GRAY + "True Damage: " + FormattingUtil.getAttribute(data.getTrueDamage()));
        lore.add(3,ChatColor.GRAY + "Bash Attack Chance: " + FormattingUtil.getAttribute(data.getBashChance()) + "%");
        lore.add(4,ChatColor.GRAY + "Decimating Strike Chance: " + FormattingUtil.getAttribute(data.getDecimatingStrikeChance()) + "%");
        lore.add(5,ChatColor.WHITE + "========Modifications========");
        int usedSlots = 0;
        int addedBlankSlots = 0;
        int baseBlankSlots = 0;
        for(UUID id : data.getMods()) {
            ToolMod mod = ToolHandlerPlugin.instance.getModManager().getToolMod(id);
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
            if(mod.getTrueDamage() != null && mod.getTrueDamage() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getTrueDamage()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getTrueDamage())
                        + ChatColor.GRAY + " True Damage");
            }
            if(mod.getBashChance() != null && mod.getBashChance() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getBashChance()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getBashChance())
                        + ChatColor.GRAY + "% Bash Attack Chance");
            }
            if(mod.getDecimateChance() != null && mod.getDecimateChance() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getDecimateChance()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getDecimateChance())
                        + ChatColor.GRAY + "% Decimating Attack Chance");
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
