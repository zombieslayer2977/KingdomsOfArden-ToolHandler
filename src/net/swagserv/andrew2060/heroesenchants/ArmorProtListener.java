package net.swagserv.andrew2060.heroesenchants;

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
				int maxDurability = 0;
				switch(helmet.getType()) {
				case DIAMOND_HELMET: 
					maxDurability = 364;
					break;
				case IRON_HELMET: 
					maxDurability = 166;
					break;
				case GOLD_HELMET: 
					maxDurability = 78;
					break;
				case CHAINMAIL_HELMET:
					maxDurability = 166;
					break;
				case LEATHER_HELMET:
					maxDurability = 56;
					break;
				default:
					Bukkit.getServer().broadcastMessage("Swagserv Heroes Armor Enchantment Handler: something is wrong, please contact andrew2060");
				}
				int durability = maxDurability - helmet.getDurability();
				if( durability > maxDurability * 1.68) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 1.17 > durability && durability > maxDurability*0.5) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(durability < maxDurability*1.0) {
					helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		//ChestPlate Protection Handling
		if(chestcheck) {
			if(chest.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = 0;
				switch(chest.getType()) {
				case DIAMOND_CHESTPLATE: 
					maxDurability = 529;
					break;
				case IRON_CHESTPLATE: 
					maxDurability = 242;
					break;
				case GOLD_CHESTPLATE: 
					maxDurability = 114;
					break;
				case CHAINMAIL_CHESTPLATE: 
					maxDurability = 242;
					break;
				case LEATHER_CHESTPLATE:
					maxDurability = 82;
					break;
				default:
					Bukkit.getServer().broadcastMessage("Swagserv Heroes Armor Enchantment Handler: something is wrong, please contact andrew2060");
				}
				int durability = maxDurability - chest.getDurability();
				if( durability > maxDurability * 1.68) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 1.17 > durability && durability > maxDurability*1.0) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					chest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(durability < maxDurability*1.0) {
					chest.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		//Leggings Protection Handling
		if(legscheck) {
			if(legs.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = 0;
				switch(legs.getType()) {
				case DIAMOND_LEGGINGS: 
					maxDurability = 496;
					break;
				case IRON_LEGGINGS: 
					maxDurability = 226;
					break;
				case GOLD_LEGGINGS: 
					maxDurability = 106;
					break;
				case CHAINMAIL_LEGGINGS:
					maxDurability = 226;
					break;
				case LEATHER_LEGGINGS:
					maxDurability = 76;
					break;
				default:
					Bukkit.getServer().broadcastMessage("Swagserv Heroes Armor Enchantment Handler: something is wrong, please contact andrew2060");
				}
				int durability = maxDurability - legs.getDurability();
				if( durability > maxDurability * 1.68) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 1.17 > durability && durability > maxDurability*1.0) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					legs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(durability < maxDurability*1.0) {
					legs.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		//Boots Protection Handling
		if(bootscheck) {
			if(boots.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int maxDurability = 0;
				switch(boots.getType()) {
				case DIAMOND_BOOTS: 
					maxDurability = 430;
					break;
				case IRON_BOOTS: 
					maxDurability = 196;
					break;
				case GOLD_BOOTS: 
					maxDurability = 92;
					break;
				case CHAINMAIL_BOOTS: 
					maxDurability = 196;
					break;
				case LEATHER_BOOTS:
					maxDurability = 66;
					break;
				default:
					Bukkit.getServer().broadcastMessage("Swagserv Heroes Armor Enchantment Handler: something is wrong, please contact andrew2060");
				}
				int durability = maxDurability - boots.getDurability();
				if( durability > maxDurability * 1.68) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
				}
				if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
				}
				if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
				}
				if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
				}
				if(maxDurability * 1.17 > durability && durability > maxDurability*1.0) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
					boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
				}
	            if(durability < maxDurability) {
					boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
	            }
			}
		}
		int protNumber = 0;
		if(helmetcheck) {
			if(helmet.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				protNumber = protNumber + helmet.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
			}
		}
		if(chestcheck) {
			if(chest.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				protNumber = protNumber + chest.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
			}
		}
		if(legscheck) {
			if(legs.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				protNumber = protNumber + legs.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
			}
		}
		if(bootscheck) {
			if(boots.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				protNumber = protNumber + boots.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
			}
		}
		event.setDamage((int) (event.getDamage()*(1-0.01*protNumber)));
		heroes.getCharacterManager().getHero(p).syncHealth();
	}
}
