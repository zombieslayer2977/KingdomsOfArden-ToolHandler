package net.swagserv.andrew2060.toolhandler.listeners.lore;

import net.swagserv.andrew2060.toolhandler.util.ArmorLoreUtil;
import net.swagserv.andrew2060.toolhandler.util.GeneralLoreUtil;
import net.swagserv.andrew2060.toolhandler.util.ImprovementUtil;

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

public class ArmorLoreListener implements Listener {
	//Armor Magic Protection Handling
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onSkillDamage(SkillDamageEvent event) {
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
		double damagereduction = 0;
		if(helmetcheck) {
			damagereduction += ArmorLoreUtil.getMagicResistRating(helmet);
		}
		if(chestcheck) {
			damagereduction += ArmorLoreUtil.getMagicResistRating(chest);
		}
		if(legscheck) {
			damagereduction += ArmorLoreUtil.getMagicResistRating(legs);
		}
		if(bootscheck) {
			damagereduction += ArmorLoreUtil.getMagicResistRating(boots);
		}
		double multiplier = 1 - damagereduction;
		event.setDamage((int) (event.getDamage() * multiplier));
	}
	//Armor Healing Bonus Handling
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onHeroRegainHealth(HeroRegainHealthEvent event) {
		boolean helmetcheck = true;
		boolean chestcheck = true;
		boolean legscheck = true;
		boolean bootscheck = true;
		Player p = event.getHero().getPlayer();
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
		double healBonus = 0;
		try {
			if(helmetcheck) {
				healBonus += ArmorLoreUtil.getHealingBonus(helmet);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			try {
				double helmetQuality = ImprovementUtil.getQuality(helmet);
				GeneralLoreUtil.populateLoreDefaults(helmet);
				ImprovementUtil.setQuality(helmet, helmetQuality);
			} catch (NullPointerException nE){
				GeneralLoreUtil.populateLoreDefaults(helmet);
			}
		}
		try {
			if(chestcheck) {
				healBonus += ArmorLoreUtil.getHealingBonus(chest);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			try {
				double chestQuality = ImprovementUtil.getQuality(chest);
				GeneralLoreUtil.populateLoreDefaults(chest);
				ImprovementUtil.setQuality(chest, chestQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLoreDefaults(chest);
			}
		}
		try {
			if(legscheck) {
				healBonus += ArmorLoreUtil.getHealingBonus(legs);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			try {
				double legsQuality = ImprovementUtil.getQuality(legs);
				GeneralLoreUtil.populateLoreDefaults(legs);
				ImprovementUtil.setQuality(legs, legsQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLoreDefaults(legs);
			}
		}
		try {
			if(bootscheck) {
				healBonus += ArmorLoreUtil.getHealingBonus(boots);
			} 
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
				try {
					double bootsQuality = ImprovementUtil.getQuality(boots);
					GeneralLoreUtil.populateLoreDefaults(boots);
					ImprovementUtil.setQuality(boots, bootsQuality);
				} catch (NullPointerException nE) {
					GeneralLoreUtil.populateLoreDefaults(boots);
				}
		}
		double multiplier = 1 + healBonus;
		event.setAmount((int) (event.getAmount()*multiplier));
	}
	//Armor Bonus Damage Protection Handling
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeaponDamageIncoming(WeaponDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		boolean helmetcheck = true;
		boolean chestcheck = true;
		boolean legscheck = true;
		boolean bootscheck = true;
		Player p = (Player) event.getEntity();
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
		int protBonus = 0;
		try {
			if(helmetcheck) {
				protBonus += ArmorLoreUtil.getProtBonus(helmet);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			try {
				double helmetQuality = ImprovementUtil.getQuality(helmet);
				GeneralLoreUtil.populateLoreDefaults(helmet);
				ImprovementUtil.setQuality(helmet, helmetQuality);
			} catch (NullPointerException nE){
				GeneralLoreUtil.populateLoreDefaults(helmet);
			}
		}
		try {
			if(chestcheck) {
				protBonus += ArmorLoreUtil.getProtBonus(chest);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			try {
				double chestQuality = ImprovementUtil.getQuality(chest);
				GeneralLoreUtil.populateLoreDefaults(chest);
				ImprovementUtil.setQuality(chest, chestQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLoreDefaults(chest);
			}
		}
		try {
			if(legscheck) {
				protBonus += ArmorLoreUtil.getProtBonus(legs);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			try {
				double legsQuality = ImprovementUtil.getQuality(legs);
				GeneralLoreUtil.populateLoreDefaults(legs);
				ImprovementUtil.setQuality(legs, legsQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLoreDefaults(legs);
			}
		}
		try {
			if(bootscheck) {
				protBonus += ArmorLoreUtil.getProtBonus(boots);
			} 
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
				try {
					double bootsQuality = ImprovementUtil.getQuality(boots);
					GeneralLoreUtil.populateLoreDefaults(boots);
					ImprovementUtil.setQuality(boots, bootsQuality);
				} catch (NullPointerException nE) {
					GeneralLoreUtil.populateLoreDefaults(boots);
				}
		}
		event.setDamage(event.getDamage()-protBonus);
	}
}
