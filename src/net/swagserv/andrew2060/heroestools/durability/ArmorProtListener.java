package net.swagserv.andrew2060.heroestools.durability;

import net.swagserv.andrew2060.heroestools.util.Util;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public class ArmorProtListener implements Listener{
	Heroes heroes = (Heroes) Bukkit.getPluginManager().getPlugin("Heroes");
	//Armor Handling
	@EventHandler(priority=EventPriority.HIGH)
	public void onIncomingDamage(WeaponDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		boolean helmetcheck = true;
		boolean chestcheck = true;
		boolean legscheck = true;
		boolean bootscheck = true;
		Player p = (Player)event.getEntity();
		PlayerInventory inv = p.getInventory();
		ItemStack helmet = inv.getHelmet();
		if(helmet == null) {
			helmetcheck = false;
		}
		ItemStack chest = inv.getChestplate();
		if(chest == null) {
			chestcheck = false;
		}
		ItemStack legs = inv.getLeggings();
		if(legs == null) {
			legscheck = false;
		}
		ItemStack boots = inv.getBoots();
		if(boots == null) {
			bootscheck = false;
		}
		//Helmet durability and protection scaling check
		if(helmetcheck) {
			if(helmet.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = Util.getArmorMaxDurability(helmet.getType());
				int durability = maxDurability - helmet.getDurability();
				if( durability > maxDurability * 0.84) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 0.68 > durability && durability > maxDurability * 0.53) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 0.53 > durability && durability > maxDurability * 0.37) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 0.37 > durability && durability > maxDurability*0.16) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(maxDurability * 0.16 > durability) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		//ChestPlate Protection Handling
		if(chestcheck) {
			if(chest.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = Util.getArmorMaxDurability(chest.getType());
				int durability = maxDurability - chest.getDurability();
				if( durability > maxDurability * 0.84) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 0.68 > durability && durability > maxDurability * 0.53) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 0.53 > durability && durability > maxDurability * 0.37) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 0.37 > durability && durability > maxDurability * 0.16) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(maxDurability * 0.16 > durability) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		//Leggings Protection Handling
		if(legscheck) {
			if(legs.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = Util.getArmorMaxDurability(legs.getType());
				int durability = maxDurability - legs.getDurability();
				if( durability > maxDurability * 0.84) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 0.68 > durability && durability > maxDurability * 0.53) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 0.53 > durability && durability > maxDurability * 0.37) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 0.37 > durability && durability > maxDurability * 0.16) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(maxDurability * 0.16 > durability) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		if(bootscheck) {
			if(boots.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = Util.getArmorMaxDurability(boots.getType());
				int durability = maxDurability - boots.getDurability();
				if( durability > maxDurability * 0.84) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 0.68 > durability && durability > maxDurability * 0.53) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 0.53 > durability && durability > maxDurability * 0.37) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 0.37 > durability && durability > maxDurability * 0.16 ) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(maxDurability * 0.16 > durability) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
	}
}
