package net.swagserv.andrew2060.heroestools.enchantments;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;
import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.skill.Skill;

public class ArmorEnchantsListener implements Listener {
	//Silk Touch Armor Handling
	@EventHandler
	public void onHealthRegenerate (HeroRegainHealthEvent event) {
		if(event.isCancelled()) {
			return;
		}
		boolean helmetcheck = true;
		boolean chestcheck = true;
		boolean legscheck = true;
		boolean bootscheck = true;
		PlayerInventory inv = event.getHero().getPlayer().getInventory();
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
		int healthregen = 0;
		if(helmetcheck) {
			if(helmet.containsEnchantment(Enchantment.SILK_TOUCH)) {
				healthregen = healthregen + 1;
			}		
		}
		if(chestcheck) {
			if(chest.containsEnchantment(Enchantment.SILK_TOUCH)) {
				healthregen = healthregen + 1;
			}		
		}
		if(legscheck) {
			if(legs.containsEnchantment(Enchantment.SILK_TOUCH)) {
				healthregen = healthregen + 1;
			}		
		}
		if(bootscheck) {
			if(boots.containsEnchantment(Enchantment.SILK_TOUCH)) {
				healthregen = healthregen + 1;
			}		
		}
		event.setAmount(event.getAmount() + healthregen*2);	
		Random random = new Random();
		if(random.nextInt(5000) < 1) { //0.02% chance of losing enchantments every time a healing action is taken
			if(helmetcheck) {
				if(helmet.containsEnchantment(Enchantment.SILK_TOUCH)) {
					helmet.removeEnchantment(Enchantment.SILK_TOUCH);
				}		
			}
			if(chestcheck) {
				if(chest.containsEnchantment(Enchantment.SILK_TOUCH)) {
					chest.removeEnchantment(Enchantment.SILK_TOUCH);
				}		
			}
			if(legscheck) {
				if(legs.containsEnchantment(Enchantment.SILK_TOUCH)) {
					legs.removeEnchantment(Enchantment.SILK_TOUCH);
				}		
			}
			if(bootscheck) {
				if(boots.containsEnchantment(Enchantment.SILK_TOUCH)) {
					boots.removeEnchantment(Enchantment.SILK_TOUCH);
				}		
			}
			Player p = event.getHero().getPlayer();
			p.sendMessage(ChatColor.GRAY + "The silk touch enchantment on your armor ran out of charge!");
			p.getWorld().playEffect(p.getLocation(), Effect.EXTINGUISH, 5);
		}
		return;
	}
	//Armor Efficiency Handling
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSkillDamage(SkillDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		boolean helmetcheck = true;
		boolean chestcheck = true;
		boolean legscheck = true;
		boolean bootscheck = true;
		Player p = ((Hero) event.getDamager()).getPlayer();
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
		int damagereduction = 0;
		Random rand = new Random();
		if(helmetcheck) {
			if(helmet.containsEnchantment(Enchantment.DIG_SPEED)) {
				damagereduction = damagereduction + helmet.getEnchantmentLevel(Enchantment.DIG_SPEED);
				if(rand.nextInt(500) < 1) {
					helmet.removeEnchantment(Enchantment.DIG_SPEED);
					p.sendMessage(ChatColor.GRAY + "The spell protection enchantment on your helmet has been exhausted!");
				}
			}		
		}
		if(chestcheck) {
			if(chest.containsEnchantment(Enchantment.DIG_SPEED)) {
				damagereduction = damagereduction + chest.getEnchantmentLevel(Enchantment.DIG_SPEED);
				if(rand.nextInt(500) < 1) {
					chest.removeEnchantment(Enchantment.DIG_SPEED);
					p.sendMessage(ChatColor.GRAY + "The spell protection enchantment on your chestplate has been exhausted!");
				}
			}		
		}
		if(legscheck) {
			if(legs.containsEnchantment(Enchantment.DIG_SPEED)) {
				damagereduction = damagereduction + legs.getEnchantmentLevel(Enchantment.DIG_SPEED);
				if(rand.nextInt(500) < 1) {
					legs.removeEnchantment(Enchantment.DIG_SPEED);
					p.sendMessage(ChatColor.GRAY + "The spell protection enchantment on your leggings has been exhausted!");
				}
			}		
		}
		if(bootscheck) {
			if(boots.containsEnchantment(Enchantment.DIG_SPEED)) {
				damagereduction = damagereduction + boots.getEnchantmentLevel(Enchantment.DIG_SPEED);
				if(rand.nextInt(500) < 1) {
					boots.removeEnchantment(Enchantment.DIG_SPEED);
					p.sendMessage(ChatColor.GRAY + "The spell protection enchantment on your boots has been exhausted!");
				}
			}		
		}
		double multiplier = 1 - damagereduction*0.01;
		event.setDamage((int) (event.getDamage() * multiplier));
	}
	//Armor thorn enchantment handling
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void onWeaponDamageIncoming(WeaponDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		PlayerInventory pI = ((Player)event.getEntity()).getInventory();
		ItemStack chest = pI.getChestplate();
		if( chest == null) {
			return;
		}
		if(chest.containsEnchantment(Enchantment.THORNS)) {
			double multiplier = chest.getEnchantmentLevel(Enchantment.THORNS) * 0.5 * 0.01;
			Skill.damageEntity(event.getDamager().getEntity(), ((LivingEntity)event.getEntity()), (int) (event.getDamage()*multiplier), DamageCause.MAGIC);
		}
		
	}
}
