package net.swagserv.andrew2060.heroestools.listeners.lore;

import net.swagserv.andrew2060.heroestools.util.ArmorLoreUtil;
import net.swagserv.andrew2060.heroestools.util.GeneralLoreUtil;
import net.swagserv.andrew2060.heroestools.util.ImprovementUtil;

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
		if(helmetcheck) {
			healBonus += ArmorLoreUtil.getHealingBonus(helmet);
		}
		if(chestcheck) {
			healBonus += ArmorLoreUtil.getHealingBonus(chest);
		}
		if(legscheck) {
			healBonus += ArmorLoreUtil.getHealingBonus(legs);
		}
		if(bootscheck) {
			healBonus += ArmorLoreUtil.getHealingBonus(boots);
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
		//TODO: Update Case - Remove try/catch in future
		try {
			if(helmetcheck) {
				protBonus += ArmorLoreUtil.getProtBonus(helmet);
			}
			if(chestcheck) {
				protBonus += ArmorLoreUtil.getProtBonus(chest);
			}
			if(legscheck) {
				protBonus += ArmorLoreUtil.getProtBonus(legs);
			}
			if(bootscheck) {
				protBonus += ArmorLoreUtil.getProtBonus(boots);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			p.sendMessage("Your weapon's statistics are from an old version and have been updated");
			try {
				double helmetQuality = ImprovementUtil.getQuality(helmet);
				GeneralLoreUtil.populateLore(helmet);
				ImprovementUtil.setQuality(helmet, helmetQuality);
			} catch (NullPointerException nE){
				GeneralLoreUtil.populateLore(helmet);
			}
			try {
				double chestQuality = ImprovementUtil.getQuality(chest);
				GeneralLoreUtil.populateLore(chest);
				ImprovementUtil.setQuality(chest, chestQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLore(chest);
			}
			try {
				double legsQuality = ImprovementUtil.getQuality(legs);
				GeneralLoreUtil.populateLore(legs);
				ImprovementUtil.setQuality(legs, legsQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLore(legs);
			}
			try {
				double bootsQuality = ImprovementUtil.getQuality(boots);
				GeneralLoreUtil.populateLore(boots);
				ImprovementUtil.setQuality(boots, bootsQuality);
			} catch (NullPointerException nE) {
				GeneralLoreUtil.populateLore(boots);
			}
		}
		event.setDamage(event.getDamage()-protBonus);
	}
	//Armor Thorn Enchantment handling
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void onWeaponDamageIncoming2(WeaponDamageEvent event) {
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
