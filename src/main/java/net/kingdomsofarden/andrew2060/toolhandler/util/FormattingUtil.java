package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;

public class FormattingUtil {
    public static DecimalFormat modDescriptorFormat = new DecimalFormat("+###.00;-###.00");
    public static DecimalFormat loreDescriptorFormat = new DecimalFormat("###.00");
	public static String getWeaponQualityFormat(double quality) {
		if(quality > 0 && quality <= 20) {
			return ChatColor.RED + "Sharpened";
		} else if (quality > 20 && quality <= 40) {
			return ChatColor.GOLD + "Honed";
		} else if (quality > 40 && quality <= 60) {
			return ChatColor.YELLOW + "Keen";
		} else if (quality > 60 && quality <= 80) {
			return ChatColor.GREEN + "Superior";
		} else if (quality > 80) {
			return ChatColor.DARK_GREEN + "Lethal";
		} else {
			return ChatColor.GRAY + "Basic";
		}
	}
	public static String getArmorQualityFormat(double quality) {
        if(quality > 0 && quality <= 20) {
            return ChatColor.RED + "Strengthened";
        } else if (quality > 20 && quality <= 40) {
            return ChatColor.GOLD + "Reinforced";
        } else if (quality > 40 && quality <= 60) {
            return ChatColor.YELLOW + "Augmented";
        } else if (quality > 60 && quality <= 80) {
            return ChatColor.GREEN + "Fortified";
        } else if (quality > 80) {
            return ChatColor.DARK_GREEN + "Bulwarked";
        } else {
            return ChatColor.GRAY + "Basic";
        }
    }
	public static String getAttributeColor(double attribute) {
		return attribute > 0.00 ? ChatColor.GREEN + "" : attribute == Double.valueOf(0.00) ? ChatColor.GRAY + "" : ChatColor.RED + "";
	}
    public static String getAttribute(double attribute) {
        return getAttributeColor(attribute) + loreDescriptorFormat.format(attribute) + ChatColor.GRAY;
    }
}
