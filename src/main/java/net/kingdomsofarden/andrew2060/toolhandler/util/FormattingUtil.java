package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;

public class FormattingUtil {
    public static DecimalFormat modDescriptorFormat = new DecimalFormat("+##0.00;-##0.00");
    public static DecimalFormat loreDescriptorFormat = new DecimalFormat("##0.00");
	public static String getWeaponToolQualityFormat(double quality) {
		if(quality > 0 && quality <= 20) {
			return ChatColor.RED + "Sharpened (Tier 1)";
		} else if (quality > 20 && quality <= 40) {
			return ChatColor.GOLD + "Honed (Tier 2)";
		} else if (quality > 40 && quality <= 60) {
			return ChatColor.YELLOW + "Keen (Tier 3)";
		} else if (quality > 60 && quality <= 80) {
			return ChatColor.GREEN + "Superior (Tier 4)";
		} else if (quality > 80) {
			return ChatColor.DARK_GREEN + "Legendary (Tier 5)";
		} else {
			return ChatColor.GRAY + "Basic (Tier 0)";
		}
	}
	public static String getArmorQualityFormat(double quality) {
        if(quality > 0 && quality <= 20) {
            return ChatColor.RED + "Augmented (Tier 1)";
        } else if (quality > 20 && quality <= 40) {
            return ChatColor.GOLD + "Reinforced (Tier 2)";
        } else if (quality > 40 && quality <= 60) {
            return ChatColor.YELLOW + "Fortified (Tier 3)";
        } else if (quality > 60 && quality <= 80) {
            return ChatColor.GREEN + "Bulwarked (Tier 4)";
        } else if (quality > 80) {
            return ChatColor.DARK_GREEN + "Legendary (Tier 5)";
        } else {
            return ChatColor.GRAY + "Basic (Tier 0)";
        }
    }
	public static String getScytheQualityFormat(double quality) {
        if(quality > 0 && quality <= 20) {
            return ChatColor.RED + "Empowered (Tier 1)";
        } else if (quality > 20 && quality <= 40) {
            return ChatColor.GOLD + "Imbued (Tier 2)";
        } else if (quality > 40 && quality <= 60) {
            return ChatColor.YELLOW + "Attuned (Tier 3)";
        } else if (quality > 60 && quality <= 80) {
            return ChatColor.GREEN + "Dominating (Tier 4)";
        } else if (quality > 80) {
            return ChatColor.DARK_GREEN + "Legendary (Tier 5)";
        } else {
            return ChatColor.GRAY + "Basic (0)";
        }
    }
	public static String getAttributeColor(double attribute) {
		return attribute > 0.00 ? ChatColor.GREEN + "" : attribute == Double.valueOf(0.00) ? ChatColor.GRAY + "" : ChatColor.RED + "";
	}
    public static String getAttribute(double attribute) {
        return getAttributeColor(attribute) + loreDescriptorFormat.format(attribute) + ChatColor.GRAY;
    }
}
