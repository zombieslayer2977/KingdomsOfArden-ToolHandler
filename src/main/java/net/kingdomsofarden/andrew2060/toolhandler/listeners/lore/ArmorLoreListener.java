package net.kingdomsofarden.andrew2060.toolhandler.listeners.lore;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.CacheManager;
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
    
    private ToolHandlerPlugin plugin;
    public ArmorLoreListener(ToolHandlerPlugin plugin) {
        this.plugin = plugin;
    }
    
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
        CacheManager man = plugin.getCacheManager();
        double damagereduction = 0;
        if(helmetcheck) {
            damagereduction += man.getCachedArmorInfo(helmet).getMagicResist()*0.01;
        }
        if(chestcheck) {
            damagereduction += man.getCachedArmorInfo(chest).getMagicResist()*0.01;
        }
        if(legscheck) {
            damagereduction += man.getCachedArmorInfo(legs).getMagicResist()*0.01;
        } 
        if(bootscheck) {
            damagereduction += man.getCachedArmorInfo(boots).getMagicResist()*0.01;
        }

        double multiplier = 1 - damagereduction;
        event.setDamage(event.getDamage() * multiplier);
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
        CacheManager man = plugin.getCacheManager();
        double healBonus = 0;
        if(helmetcheck) {
            healBonus += man.getCachedArmorInfo(helmet).getHealBonus()*0.01;
        }

        if(chestcheck) {
            healBonus += man.getCachedArmorInfo(chest).getHealBonus()*0.01;
        }

        if(legscheck) {
            healBonus += man.getCachedArmorInfo(legs).getHealBonus()*0.01;
        }

        if(bootscheck) {
            healBonus += man.getCachedArmorInfo(boots).getHealBonus()*0.01;
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
        CacheManager man = plugin.getCacheManager();
        int protBonus = 0;
        if(helmetcheck) {
            protBonus += man.getCachedArmorInfo(helmet).getProtBonus();
        }
        if(chestcheck) {
            protBonus += man.getCachedArmorInfo(chest).getProtBonus();
        }
        if(legscheck) {
            protBonus += man.getCachedArmorInfo(legs).getProtBonus();
        }
        if(bootscheck) {
            protBonus += man.getCachedArmorInfo(boots).getProtBonus();
        } 
        event.setDamage(event.getDamage()-protBonus);
    }
}
