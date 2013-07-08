package net.kingdomsofterraria.andrew2060.toolhandler.listeners.lore;

import net.kingdomsofterraria.andrew2060.toolhandler.util.ArmorLoreUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;
import com.herocraftonline.heroes.api.events.SkillDamageEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

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
        event.setDamage((int)event.getDamage() * multiplier);
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
        event.setAmount((event.getAmount()*multiplier));
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
        event.setDamage(event.getDamage()-protBonus);
    }
}
