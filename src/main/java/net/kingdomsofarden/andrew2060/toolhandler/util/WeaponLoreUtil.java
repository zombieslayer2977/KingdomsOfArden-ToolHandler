package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponLoreUtil {
    public static final DecimalFormat dF = new DecimalFormat("##.##");
    /**
     * @deprecated use cache
     */
    public static int getBonusDamage(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
        String intParse = ChatColor.stripColor(lore.get(2)).replaceAll("[^.0-9]","");
        int bonus = Integer.parseInt(intParse);
        return bonus;
    }
    /**
     * @deprecated use cache
     */
    public static double getLifeSteal(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
        String doubleParse = ChatColor.stripColor(lore.get(3)).replaceAll("[^.0-9]","");
        double ls = Double.parseDouble(doubleParse);
        return ls;
    }
    /**
     * @deprecated use cache
     */
    public static double getCritChance(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
        }
        String doubleParse = ChatColor.stripColor(lore.get(4)).replaceAll("[^.0-9]","");
        double crit = Double.parseDouble(doubleParse);
        return crit;
    }
    /**
     * @deprecated use write
     */
    public static void setBonusDamage(double bonus, ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
        lore.remove(2);
        lore.add(2, ChatColor.GRAY + "Bonus Damage: " + FormattingUtil.getAttributeColor(bonus) + FormattingUtil.loreDescriptorFormat.format(bonus));
        meta.setLore(lore);
        weapon.setItemMeta(meta);
        return;
    }
    /**
     * @deprecated use write
     */
    public static void setLifeSteal(double lifesteal, ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
        lore.remove(3);
        lore.add(3,ChatColor.GRAY + "Life Steal: " + FormattingUtil.getAttributeColor(lifesteal) + FormattingUtil.loreDescriptorFormat.format(lifesteal) + ChatColor.GRAY + " %");
        meta.setLore(lore);
        weapon.setItemMeta(meta);
        return;
    }
    /**
     * @deprecated use write
     */
    public static void setCritChance(double bonusCrit, ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
        List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
        lore.remove(4);
        lore.add(4, ChatColor.GRAY + "Critical Strike Chance: " + FormattingUtil.getAttributeColor(bonusCrit) + FormattingUtil.loreDescriptorFormat.format(bonusCrit) + ChatColor.GRAY + "%");
        meta.setLore(lore);
        weapon.setItemMeta(meta);
        return;
    }
    /**
     * @deprecated - use NBT caching instead
     * Updates this itemstack's lore after getting all previous values: note that you will have to get an updated copy
     * of the ItemMeta from the weapon after running this function to update.
     * @param weapon
     */
    public static void updateWeaponLore(ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            return;
        }
        List<String> lore = meta.getLore();
        if(lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            return;
        }
        double improvementQuality = 0.00D;
        int bonusdmg = 0;
        int lifesteal = 0;
        double critchance = 0;
        List<String> modifications = new LinkedList<String>();
        //Once this is true, assumes all lore past is modification lore
        boolean reachedmodifications = false;
        for(String line : lore) {
            if(reachedmodifications) {
                modifications.add(line);
            }
            line = ChatColor.stripColor(line);
            if(line.contains("Quality")) {
                try {
                    improvementQuality = Double.parseDouble(line.replaceAll("[^.0-9]",""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Damage")) {
                try {
                    bonusdmg = Integer.parseInt(line.replaceAll("[^.0-9]",""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Life Steal")) {
                try {
                    lifesteal = Integer.parseInt(line.replaceAll("[^.0-9]",""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Critical")) {
                try {
                    critchance = Double.parseDouble(line.replaceAll("[^.0-9]",""));
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Modifications")) {
                reachedmodifications = true;
            }
        }
        String improvementtext = FormattingUtil.getWeaponQualityFormat(improvementQuality);
        improvementtext = ChatColor.GRAY + "Improvement Quality: " + improvementQuality + "%";
        String bonusdmgtext = FormattingUtil.getAttributeColor(bonusdmg) + bonusdmg + ChatColor.GRAY;
        bonusdmgtext = ChatColor.GRAY + "Bonus Damage: " + bonusdmgtext;
        String lifestealtext =  FormattingUtil.getAttributeColor(lifesteal) + FormattingUtil.loreDescriptorFormat.format(lifesteal) + ChatColor.GRAY;
        lifestealtext = ChatColor.GRAY + "Life Steal: " + lifestealtext + " %";
        String crittext = FormattingUtil.getAttributeColor(critchance) + FormattingUtil.loreDescriptorFormat.format(critchance) + ChatColor.GRAY;
        crittext = ChatColor.GRAY + "Critical Strike Chance: " + crittext + "%";
        List<String> loreUpdated = new ArrayList<String>();
        loreUpdated.add(ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        loreUpdated.add(improvementtext);
        loreUpdated.add(bonusdmgtext);
        loreUpdated.add(lifestealtext);
        loreUpdated.add(crittext);
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
                WeaponMod mod = modMan.getWeaponMod(modName);
                for(String desc : mod.getDescription()) {
                    loreUpdated.add(ChatColor.GRAY + "- " + desc);
                }
            }
        }
        meta.setLore(loreUpdated);
        weapon.setItemMeta(meta);
        return;
    }
    public static void write(CachedWeaponInfo cachedData, ItemStack weapon) {
        ItemMeta meta = weapon.getItemMeta();
        List<String> lore = new LinkedList<String>();
        lore.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        lore.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getWeaponQualityFormat(cachedData.getQuality()));
        lore.add(2,ChatColor.GRAY + "Bonus Damage: " + FormattingUtil.getAttribute(cachedData.getBonusDamage()));
        lore.add(3,ChatColor.GRAY + "Life Steal: " + FormattingUtil.getAttribute(cachedData.getLifeSteal()) + " Health/Hit");
        lore.add(4,ChatColor.GRAY + "Critical Strike Chance: " + FormattingUtil.getAttribute(cachedData.getCritChance()) + "%");
        lore.add(5,ChatColor.WHITE + "========Modifications========");
        int i = 0;
        for(UUID id : cachedData.getMods()) {
            WeaponMod mod = ToolHandlerPlugin.instance.getModManager().getWeaponMod(id);
            if(mod == null) {
                continue;
            }
            if(i > 1 || !mod.isSlotRequired()) {
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
                        + ChatColor.GRAY + " Life Steal");
            }
            if(mod.getCritChance() != null && mod.getCritChance() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getCritChance()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getCritChance())
                        + ChatColor.GRAY + " Critical Hit Chance");
            }
            for(int descriptionIterator = 0; descriptionIterator < mod.getDescription().length; descriptionIterator++) {
                lore.add(ChatColor.GRAY + "- " + mod.getDescription()[descriptionIterator]);
            }
            //TODO: Add blank slot options
            if(mod.isSlotRequired()) {
                i++; 
                continue;
            } else {
                continue;
            }
        }
        if(i <= 1) {
            for(int j = 0; j <= 1 - i; j++) {
                lore.add(ChatColor.DARK_GRAY + "[Empty Slot]");
            }
        }
        meta.setLore(lore);
        weapon.setItemMeta(meta);
    }
}
