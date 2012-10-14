package net.swagserv.andrew2060.heroesenchants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SoulgemIdentifyListener  implements Listener{ 
	@EventHandler(priority = EventPriority.MONITOR) 
	public void onPlayerInteract(PlayerItemHeldEvent event) {
		
		Player p = event.getPlayer();
		Inventory inv = p.getInventory();
		if(inv.getItem(event.getNewSlot()) == null) {
			return;
		}
		if(!(inv.getItem(event.getNewSlot()).getType() == Material.EMERALD)) {
			return;
		}
		ItemStack emerald = inv.getItem(event.getNewSlot());
		if(!emerald.containsEnchantment(Enchantment.DAMAGE_ALL)) {
			p.sendMessage("==Empty Soul Gem==");
			p.sendMessage("Kill someone to fill it!");
			return;
		}
		int enchLevel = emerald.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		switch (enchLevel) {
		case 1:
			p.sendMessage("==Petty Soul Gem==");
			p.sendMessage("Used to Create level 1 Enchants");
			break;
		case 2:
			p.sendMessage("==Lesser Soul Gem==");
			p.sendMessage("Used to Create level 2 Enchants");
			break;
		case 3:
			p.sendMessage("==Common Soul Gem==");
			p.sendMessage("Used to Create level 3 Enchants");
			break;
		case 4:
			p.sendMessage("==Greater Soul Gem==");
			p.sendMessage("Used to Create level 4 Enchants");
			break;
		case 5:
			p.sendMessage("==Grand Soul Gem==");
			p.sendMessage("Used to Create level 5 Enchants");
			break;
		case 10:
			p.sendMessage("==Ultimate Soul Gem==");
			p.sendMessage("Used to Create Legendary Enchants");
			break;
		}
		return;
	}

}
