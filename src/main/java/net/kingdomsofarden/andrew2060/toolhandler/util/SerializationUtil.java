package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;

public class SerializationUtil {

    /**
     * Updates the lore on the ItemStack if it is not existent and deserializes it into a format parseable for creation of a cache object<br>
     * @param item
     * @return deserialized string from which a cached representation of the object can be created
     */
    public static String deserializeFromLore(ItemStack item) {
        switch(item.getType()) {

        case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
            return deserializeWeapon(item);
        }
        case DIAMOND_PICKAXE: case DIAMOND_AXE: case DIAMOND_SPADE: case IRON_PICKAXE: case IRON_AXE: case IRON_SPADE: case GOLD_PICKAXE: case GOLD_AXE: case GOLD_SPADE: case STONE_PICKAXE: case STONE_AXE: case STONE_SPADE: case WOOD_PICKAXE: case WOOD_AXE: case WOOD_SPADE: {
            return deserializeTool(item);
        }
        case DIAMOND_HOE: case IRON_HOE: case GOLD_HOE: case STONE_HOE: case WOOD_HOE: {
            return deserializeScythe(item);
        }
        case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
            return deserializeArmor(item);
        }
        default: {
            return null;
        }

        }
    }

    private static String deserializeTool(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(item);
            return "0.00:0.00:0.00:0.00:" + EmptyModSlot.baseId.toString() + ":" + EmptyModSlot.baseId.toString();
        }
        List<String> lore = meta.getLore();

        double improvementQuality = 0.00D;
        double trueDamage = 0.00D;
        double bashChance = 0.00D;
        double decimateChance = 0.00D;

        List<String> modifications = new LinkedList<String>();
        
        //Once this is true, assumes all lore past is modification lore
        boolean reachedmodifications = false;
        for(String line : lore) {
            if(reachedmodifications) {
                modifications.add(line);
            }
            line = ChatColor.stripColor(line);
            if(line.contains("Quality")) {
                boolean textBased = line.contains("("); //If it contains a parenthetical open - it is in formatted text form
                try {
                    improvementQuality = Double.parseDouble(line.replaceAll("[^.0-9]",""));
                    if(textBased) {
                        improvementQuality *= 20.00D;
                    }
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Modifications")) {
                reachedmodifications = true;
            }
        }
        ModManager modMan = ToolHandlerPlugin.instance.getModManager();
        List<UUID> mods = new LinkedList<UUID>();
        int baseBlankSlots = 0;
        int addedBlankSlots = 0;
        for(String parseableModName : modifications) {
            if(!parseableModName.contains(ChatColor.GOLD +"")) {
                if(parseableModName.contains(ChatColor.DARK_GRAY + "")) {
                    if(parseableModName.contains(ChatColor.MAGIC + "")) {
                        addedBlankSlots++;
                    } else {
                        baseBlankSlots++;
                    }
                }
                continue;
            } else {
                ToolMod mod = modMan.getToolMod(ChatColor.stripColor(parseableModName).replace(" ", ""));
                if(mod != null) {
                    mods.add(mod.modUUID);
                    trueDamage += mod.getTrueDamage();
                    bashChance += mod.getBashChance();
                    decimateChance += mod.getDecimateChance();
                } else {
                    addedBlankSlots++; //Mod no longer exists, add slot back
                }
            }
        }

        List<String> loreUpdated = new ArrayList<String>();
        loreUpdated.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        loreUpdated.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getArmorQualityFormat(improvementQuality));
        loreUpdated.add(2,ChatColor.GRAY + "True Damage: " + FormattingUtil.getAttribute(trueDamage));
        loreUpdated.add(3,ChatColor.GRAY + "Bash Attack Chance: " + FormattingUtil.getAttribute(bashChance) + "%");
        loreUpdated.add(4,ChatColor.GRAY + "Decimating Strike Chance: " + FormattingUtil.getAttribute(decimateChance) + "%");
        loreUpdated.add(5,ChatColor.WHITE + "========Modifications========");
        int usedSlots = 0;
        for(UUID modID :  mods) {
            ToolMod mod = modMan.getToolMod(modID);
            if(usedSlots > 1 || !mod.isSlotRequired()) {
                loreUpdated.add(ChatColor.MAGIC + "" + ChatColor.RESET + "" + ChatColor.GOLD + mod.getName());
            } else {
                loreUpdated.add(ChatColor.GOLD + mod.getName());
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
            loreUpdated.add(EmptyModSlot.baseDesc);
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.bonusDesc);
        }
        meta.setLore(loreUpdated);
        item.setItemMeta(meta);
        DecimalFormat dF = new DecimalFormat("##.##");
        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append(dF.format(improvementQuality));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(trueDamage));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(bashChance));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(decimateChance));
        for(UUID modID : mods) {
            dataBuilder.append(":");
            dataBuilder.append(modID.toString());
        }
        for(int i = 0; i < baseBlankSlots; i++) {
            dataBuilder.append(":");
            dataBuilder.append(EmptyModSlot.baseId.toString());
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.bonusId.toString());
        }
        return dataBuilder.toString();
    }

    private static String deserializeScythe(ItemStack item) {
        // TODO Auto-generated method stub
        return null;
    }

    private static String deserializeArmor(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(item);
            return "0.00:0.00:0.00:0.00:" + EmptyModSlot.baseId.toString() + ":" + EmptyModSlot.baseId.toString();
        }
        List<String> lore = meta.getLore();

        double improvementQuality = 0.00D;
        double magicResist = 0.00D;
        double knockbackResist = 0.00D;
        double protBonus = 0.00D;
        switch(item.getType()) { //All chainmail has +10% base magic resist
        
        case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: {
            magicResist += 10;
            break;
        }
        default: {
            break;
        }
        
        }
        List<String> modifications = new LinkedList<String>();
        
        //Once this is true, assumes all lore past is modification lore
        boolean reachedmodifications = false;
        for(String line : lore) {
            if(reachedmodifications) {
                modifications.add(line);
            }
            line = ChatColor.stripColor(line);
            if(line.contains("Quality")) {
                boolean textBased = line.contains("("); //If it contains a parenthetical open - it is in formatted text form
                try {
                    improvementQuality = Double.parseDouble(line.replaceAll("[^.0-9]",""));
                    if(textBased) {
                        improvementQuality *= 20.00D;
                    }
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Modifications")) {
                reachedmodifications = true;
            }
        }
        ModManager modMan = ToolHandlerPlugin.instance.getModManager();
        List<UUID> mods = new LinkedList<UUID>();
        int baseBlankSlots = 0;
        int addedBlankSlots = 0;
        for(String parseableModName : modifications) {
            if(!parseableModName.contains(ChatColor.GOLD +"")) {
                if(parseableModName.contains(ChatColor.DARK_GRAY + "")) {
                    if(parseableModName.contains(ChatColor.MAGIC + "")) {
                        addedBlankSlots++;
                    } else {
                        baseBlankSlots++;
                    }
                }
                continue;
            } else {
                ArmorMod mod = modMan.getArmorMod(ChatColor.stripColor(parseableModName).replace(" ", ""));
                if(mod != null) {
                    mods.add(mod.modUUID);
                    magicResist += mod.getMagicResist();
                    knockbackResist += mod.getKnockbackResist();
                    protBonus += mod.getProtBonus();
                } else {
                    addedBlankSlots++; //Mod no longer exists, add slot back
                }
            }
        }

        List<String> loreUpdated = new ArrayList<String>();
        loreUpdated.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        loreUpdated.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getArmorQualityFormat(improvementQuality));
        loreUpdated.add(2,ChatColor.GRAY + "Magic Resistance Rating: " + FormattingUtil.getAttribute(magicResist) + "%");
        loreUpdated.add(3,ChatColor.GRAY + "Knockback Resistance: " + FormattingUtil.getAttribute(knockbackResist) + "%");
        loreUpdated.add(4,ChatColor.GRAY + "Additional Protection: " + FormattingUtil.getAttribute(protBonus) + "%");
        loreUpdated.add(5,ChatColor.WHITE + "========Modifications========");
        int usedSlots = 0;
        for(UUID modID :  mods) {
            ArmorMod mod = modMan.getArmorMod(modID);
            if(usedSlots > 1 || !mod.isSlotRequired()) {
                loreUpdated.add(ChatColor.MAGIC + "" + ChatColor.RESET + "" + ChatColor.GOLD + mod.getName());
            } else {
                loreUpdated.add(ChatColor.GOLD + mod.getName());
            }
            if(mod.getMagicResist() != null && mod.getMagicResist() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getMagicResist()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getMagicResist())
                        + ChatColor.GRAY + "% Magic Resist");
            }
            if(mod.getKnockbackResist() != null && mod.getKnockbackResist() > Double.valueOf(0.00)) {
                lore.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getKnockbackResist()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getKnockbackResist())
                        + ChatColor.GRAY + "% Knockback Resist");
            }
            if(mod.getProtBonus() != null && mod.getProtBonus() > Double.valueOf(0.00)) {
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
            loreUpdated.add(EmptyModSlot.baseDesc);
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.bonusDesc);
        }
        meta.setLore(loreUpdated);
        item.setItemMeta(meta);
        DecimalFormat dF = new DecimalFormat("##.##");
        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append(dF.format(improvementQuality));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(magicResist));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(knockbackResist));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(protBonus));
        for(UUID modID : mods) {
            dataBuilder.append(":");
            dataBuilder.append(modID.toString());
        }
        for(int i = 0; i < baseBlankSlots; i++) {
            dataBuilder.append(":");
            dataBuilder.append(EmptyModSlot.baseId.toString());
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.bonusId.toString());
        }
        return dataBuilder.toString();
    }

    private static String deserializeWeapon(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(item);
            return "0.00:0.00:0.00:0.00:" + EmptyModSlot.baseId.toString() + ":" + EmptyModSlot.baseId.toString();
        }
        List<String> lore = meta.getLore();

        double improvementQuality = 0.00D;
        double bonusDmg = 0.00D;
        double lifeSteal = 0.00D;
        double critChance = 0.00D;
        List<String> modifications = new LinkedList<String>();
        //Once this is true, assumes all lore past is modification lore
        boolean reachedmodifications = false;
        for(String line : lore) {
            if(reachedmodifications) {
                modifications.add(line);
            }
            line = ChatColor.stripColor(line);
            if(line.contains("Quality")) {
                boolean textBased = line.contains("("); //If it contains a parenthetical open - it is in formatted text form
                try {
                    improvementQuality = Double.parseDouble(line.replaceAll("[^.0-9]",""));
                    if(textBased) {
                        improvementQuality *= 20.00D;
                    }
                } catch (NumberFormatException e) {
                }
            }
            if(line.contains("Modifications")) {
                reachedmodifications = true;
            }
        }
        ModManager modMan = ToolHandlerPlugin.instance.getModManager();
        List<UUID> mods = new LinkedList<UUID>();
        int baseBlankSlots = 0;
        int addedBlankSlots = 0;
        for(String parseableModName : modifications) {
            if(!parseableModName.contains(ChatColor.GOLD +"")) {
                if(parseableModName.contains(ChatColor.DARK_GRAY + "")) {
                    if(parseableModName.contains(ChatColor.MAGIC + "")) {
                        addedBlankSlots++;
                    } else {
                        baseBlankSlots++;
                    }
                }
                continue;
            } else {
                WeaponMod mod = modMan.getWeaponMod(ChatColor.stripColor(parseableModName).replace(" ", ""));
                if(mod != null) {
                    mods.add(mod.modUUID);
                    bonusDmg += mod.getBonusDamage();
                    lifeSteal += mod.getLifeSteal();
                    critChance += mod.getCritChance();
                } else {
                    addedBlankSlots++; //Mod no longer exists, add slot back
                }
            }
        }
        List<String> loreUpdated = new ArrayList<String>();
        loreUpdated.add(0,ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
        loreUpdated.add(1,ChatColor.GRAY + "Improvement Quality: " + FormattingUtil.getWeaponToolQualityFormat(improvementQuality));
        loreUpdated.add(2,ChatColor.GRAY + "Bonus Damage: " + FormattingUtil.getAttribute(bonusDmg));
        loreUpdated.add(3,ChatColor.GRAY + "Life Steal: " + FormattingUtil.getAttribute(lifeSteal) + "%");
        loreUpdated.add(4,ChatColor.GRAY + "Critical Strike Chance: " + FormattingUtil.getAttribute(critChance) + "%");
        loreUpdated.add(5,ChatColor.WHITE + "========Modifications========");
        int usedSlots = 0;
        for(UUID modID :  mods) {
            WeaponMod mod = modMan.getWeaponMod(modID);
            if(usedSlots > 1 || !mod.isSlotRequired()) {
                loreUpdated.add(ChatColor.MAGIC + "" + ChatColor.RESET + "" + ChatColor.GOLD + mod.getName());
            } else {
                loreUpdated.add(ChatColor.GOLD + mod.getName());
            }
            if(mod.getBonusDamage() != null && mod.getBonusDamage() > Double.valueOf(0.00)) {
                loreUpdated.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getBonusDamage()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getBonusDamage())
                        + ChatColor.GRAY + "% Damage");
            }
            if(mod.getLifeSteal() != null && mod.getLifeSteal() > Double.valueOf(0.00)) {
                loreUpdated.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getLifeSteal()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getLifeSteal())
                        + ChatColor.GRAY + "% Life Steal");
            }
            if(mod.getCritChance() != null && mod.getCritChance() > Double.valueOf(0.00)) {
                loreUpdated.add(ChatColor.GRAY + "- " 
                        + FormattingUtil.getAttributeColor(mod.getCritChance()) 
                        + FormattingUtil.modDescriptorFormat.format(mod.getCritChance())
                        + ChatColor.GRAY + "% Critical Hit Chance");
            }
            for(String s : mod.getDescription()) {
                loreUpdated.add(ChatColor.GRAY + "- " + s);
            }
            if(mod.isSlotRequired()) {
                usedSlots++; 
                continue;
            } else {
                continue;
            }
        }
        for(int i = 0; i < baseBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.baseDesc);
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.bonusDesc);
        }
        meta.setLore(loreUpdated);
        item.setItemMeta(meta);
        DecimalFormat dF = new DecimalFormat("##.##");
        StringBuilder dataBuilder = new StringBuilder();
        dataBuilder.append(dF.format(improvementQuality));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(bonusDmg));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(lifeSteal));
        dataBuilder.append(":");
        dataBuilder.append(dF.format(critChance));
        for(UUID modID : mods) {
            dataBuilder.append(":");
            dataBuilder.append(modID.toString());
        }
        for(int i = 0; i < baseBlankSlots; i++) {
            dataBuilder.append(":");
            dataBuilder.append(EmptyModSlot.baseId.toString());
        }
        for(int i = 0; i < addedBlankSlots; i++) {
            loreUpdated.add(EmptyModSlot.bonusId.toString());
        }
        return dataBuilder.toString();
    }
}
