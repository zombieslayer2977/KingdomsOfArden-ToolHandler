package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorLoreUtil {
	public static double getMagicResistRating(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return 0;
		}
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(armor);
            meta = armor.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		String doubleParse = ChatColor.stripColor(lore.get(2)).replaceAll("[^.0-9]","");
		Double magicalRating = Double.parseDouble(doubleParse)*0.01;
		return magicalRating;
	}

	public static double getHealingBonus(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return 0;
		}
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(armor);
            meta = armor.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		String doubleParse = ChatColor.stripColor(lore.get(3)).replaceAll("[^.0-9]","");
		Double healBonus = Double.parseDouble(doubleParse)*0.01;
		return healBonus;
	}

	public static int getProtBonus(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return 0;
		}
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(armor);
            meta = armor.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		String intParse = ChatColor.stripColor(lore.get(4)).replaceAll("[^.0-9]","");;
		int protBonus = Integer.parseInt(intParse);
		return protBonus;
	}
	public static void setMagicResistRating(double rating, ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(armor);
            meta = armor.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		lore.remove(2);
		lore.add(2, ChatColor.GRAY + "Magical Resistance Rating: " + FormattingUtil.getAttributeColor(rating) + GeneralLoreUtil.dF.format(rating) + ChatColor.GRAY + "%");
		meta.setLore(lore);
		armor.setItemMeta(meta); 
		return;
	}
	public static void setHealingBonus(double amount, ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(armor);
            meta = armor.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		lore.remove(3);
		lore.add(3,ChatColor.GRAY + "Healing Bonus: " + FormattingUtil.getAttributeColor(amount) + GeneralLoreUtil.dF.format(amount) + ChatColor.GRAY + "%");
		meta.setLore(lore);
		armor.setItemMeta(meta);
		return;
	}
	public static void setProtBonus(double bonusProt, ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(armor);
            meta = armor.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		lore.remove(4);
		lore.add(4, ChatColor.GRAY + "Additional Protection: " + FormattingUtil.getAttributeColor(bonusProt) + FormattingUtil.loreDescriptorFormat.format(bonusProt) + ChatColor.GRAY + " Damage/Hit");
		meta.setLore(lore);
		armor.setItemMeta(meta);
		return;
	}
	/**
	 * Updates this itemstack's lore after getting all previous values: note that you will have to get an updated copy
	 * of the ItemMeta from the weapon after running this function to update.
	 * @param armor
	 */
	public static void updateArmorLore(ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(armor);
			return;
		}
		List<String> lore = meta.getLore();
		if(lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
		    return;
		}
		double improvementQuality = 0.00D;
		double magicRes = 0;
		double healingBonus = 0;
		int additionalProt = 0;
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
			if(line.contains("Magical")) {
				try {
					magicRes = Double.parseDouble(line.replaceAll("[^.0-9]",""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Healing")) {
				try {
					healingBonus = Double.parseDouble(line.replaceAll("[^.0-9]","")); 
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Additional")) {
				try {
					additionalProt = Integer.parseInt(line.replaceAll("[^.0-9]",""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Modifications")) {
				reachedmodifications = true;
			}
		}
		String improvementText = FormattingUtil.getQualityColor(improvementQuality) + GeneralLoreUtil.dF.format(improvementQuality) + ChatColor.GRAY;
		improvementText = ChatColor.GRAY + "Improvement Quality: " + improvementQuality + "%";
		String magicResText = FormattingUtil.getAttributeColor(magicRes) + magicRes + ChatColor.GRAY;
		magicResText = ChatColor.GRAY + "Magical Resistance Rating: " + magicResText + "%";
		String healingBonusText =  FormattingUtil.getAttributeColor(healingBonus) + GeneralLoreUtil.dF.format(healingBonus) + ChatColor.GRAY;
		healingBonusText = ChatColor.GRAY + "Healing Bonus: " + healingBonusText + "%";
		String additionalProtText = FormattingUtil.getAttributeColor(additionalProt) + additionalProt + ChatColor.GRAY;
		additionalProtText = ChatColor.GRAY + "Additional Protection: " + additionalProtText + " Damage/Hit";
		List<String> loreUpdated = new ArrayList<String>();
		loreUpdated.add(ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
	    loreUpdated.add(improvementText);
		loreUpdated.add(magicResText);
		loreUpdated.add(healingBonusText);
		loreUpdated.add(additionalProtText);
		loreUpdated.add(ChatColor.WHITE + "========Modifications========");
		ModManager modMan = ((ToolHandlerPlugin) Bukkit.getPluginManager().getPlugin("KingdomsOfArden-ToolHandler")).getModManager();
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
                ArmorMod mod = modMan.getArmorMod(modName);
                for(String desc : mod.getDescription()) {
                    loreUpdated.add(ChatColor.GRAY + "- " + desc);
                }
            }
        }
		meta.setLore(loreUpdated);
		armor.setItemMeta(meta);
		return;
	}

    public static void addMagicResistRating(double d, ItemStack armor) {
        setMagicResistRating(getMagicResistRating(armor) + d, armor);
    }

    public static void addHealingBonus(double i, ItemStack armor) {
        setHealingBonus(getHealingBonus(armor) + i,armor);
    }
    
    public static void addProtBonus(Double protBonus, ItemStack armor) {
        setProtBonus(getProtBonus(armor) + protBonus,armor);
    }
}
