package net.swagserv.andrew2060.toolhandler.util;

import org.bukkit.ChatColor;

public class FormattingUtil {
	public static String getQualityColor(double quality) {
		if(quality > 0 && quality <= 20) {
			return ChatColor.RED + "";
		} else if (quality > 20 && quality <= 40) {
			return ChatColor.GOLD + "";
		} else if (quality > 40 && quality <= 60) {
			return ChatColor.YELLOW + "";
		} else if (quality > 60 && quality <= 80) {
			return ChatColor.GREEN + "";
		} else if (quality > 80) {
			return ChatColor.DARK_GREEN + "";
		} else {
			return ChatColor.GRAY + "";
		}
	}
	public static String getAttributeColor(double attribute) {
		if(attribute < 0) {
			return ChatColor.RED + "";
		} else if(attribute > 0) {
			return ChatColor.GREEN + "";
		} else {
			return ChatColor.GRAY + "";
		}
	}
}
