package net.swagserv.andrew2060.heroestools.listeners.mods;

import java.util.List;

import net.swagserv.andrew2060.heroestools.ToolHandlerPlugin;
import net.swagserv.andrew2060.heroestools.mods.ModManager;
import net.swagserv.andrew2060.heroestools.mods.WeaponMod;
import net.swagserv.andrew2060.heroestools.util.ModUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class WeaponModListener implements Listener {
	private ToolHandlerPlugin plugin;

	public WeaponModListener(ToolHandlerPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onWeaponDamage(WeaponDamageEvent event) {
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		Player p = ((Hero) event.getDamager()).getPlayer();
		ItemStack i = p.getItemInHand();
		switch(i.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return;
			}
		}
		List<String> mods = ModUtil.getWeaponMods(i);
		ModManager modManager = plugin.getModManager();
		for(int x = 0; x < mods.size(); x++) {
			WeaponMod mod = modManager.getWeaponMod(mods.get(x));
			if(mod != null) {
				mod.executeOnWeaponDamage(event);
			}
		}
	}
}
