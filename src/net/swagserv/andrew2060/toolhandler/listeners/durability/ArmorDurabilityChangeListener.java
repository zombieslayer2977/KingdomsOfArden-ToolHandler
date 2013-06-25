package net.swagserv.andrew2060.toolhandler.listeners.durability;

import net.swagserv.andrew2060.toolhandler.util.ImprovementUtil;

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

public class ArmorDurabilityChangeListener implements Listener{
    Heroes heroes = (Heroes) Bukkit.getPluginManager().getPlugin("Heroes");
    //Armor Handling
    @SuppressWarnings("unused") //Because it actually is used and eclipse is wrong
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
        switch(helmet.getType()) {
            case DIAMOND_HELMET: case IRON_HELMET: case CHAINMAIL_HELMET: case GOLD_HELMET: case LEATHER_HELMET: {
                break;
            }
            default: {
                helmetcheck = false;
            }
        }
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
            double quality = ImprovementUtil.reduceQuality(helmet, ImprovementUtil.getItemType(helmet));
            ImprovementUtil.applyEnchantmentLevel(helmet, Enchantment.PROTECTION_ENVIRONMENTAL, quality);
        }
        //ChestPlate Protection Handling
        if(chestcheck) {
            double quality = ImprovementUtil.reduceQuality(chest, ImprovementUtil.getItemType(chest));
            ImprovementUtil.applyEnchantmentLevel(chest, Enchantment.PROTECTION_ENVIRONMENTAL, quality);
        }
        //Leggings Protection Handling
        if(legscheck) {
            double quality = ImprovementUtil.reduceQuality(legs, ImprovementUtil.getItemType(legs));
            ImprovementUtil.applyEnchantmentLevel(legs, Enchantment.PROTECTION_ENVIRONMENTAL, quality); 
        }
        if(bootscheck) {
            double quality = ImprovementUtil.reduceQuality(boots, ImprovementUtil.getItemType(boots));
            ImprovementUtil.applyEnchantmentLevel(boots, Enchantment.PROTECTION_ENVIRONMENTAL, quality);
        }
    }
}
