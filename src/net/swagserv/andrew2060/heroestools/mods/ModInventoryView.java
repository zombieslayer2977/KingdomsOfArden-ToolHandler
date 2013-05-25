package net.swagserv.andrew2060.heroestools.mods;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class ModInventoryView extends InventoryView {

	@Override
	public Inventory getBottomInventory() {
		return null;
	}

	@Override
	public HumanEntity getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory getTopInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InventoryType getType() {
		return InventoryType.ANVIL;
	}

}
