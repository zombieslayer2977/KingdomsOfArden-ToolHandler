package net.kingdomsofarden.andrew2060.toolhandler.listeners.potions;

import java.util.Collection;

import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectManagerListener implements Listener {
    private PotionEffectManager pEMan;
    public PotionEffectManagerListener(PotionEffectManager effectMan) {
        this.pEMan = effectMan;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        pEMan.untrackEntity(event.getEntity());
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        pEMan.untrackEntity(event.getEntity());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        pEMan.untrackEntity(event.getPlayer());
    }
    
    //The following listeners intercept potion consumption and apply the effects using
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true) 
    public void onItemConsume(PlayerItemConsumeEvent event) {
        ItemStack i = event.getItem();
        if(i.getType() == Material.POTION) {
            Potion pot = Potion.fromItemStack(i);
            Collection<PotionEffect> effects = pot.getEffects();
            pEMan.addPotionEffectStacking(effects, event.getPlayer());
        } else if(i.getType() == Material.GOLDEN_APPLE) {
            
            if(i.getItemMeta().hasEnchants()) {    //Enchanted
                pEMan.addPotionEffectStacking(PotionEffectType.REGENERATION.createEffect(600, 2), event.getPlayer());
            } else {
                pEMan.addPotionEffectStacking(PotionEffectType.REGENERATION.createEffect(200, 1), event.getPlayer());
            }        
        } else {
            return;
        }
       
        event.setCancelled(true);
        //Cancelling prevents item from being consumed, change/update ourselves
        i.setType(Material.AIR);
        i.setAmount(0);
        event.getPlayer().updateInventory();
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent event) {
        event.setCancelled(true);
        Collection<PotionEffect> effects = event.getPotion().getEffects();
        for(LivingEntity lE : event.getAffectedEntities()) {
            pEMan.addPotionEffectStacking(effects, lE);
        }
        //Cancelling prevents splash from playing so we play it ourselves
        Location loc = event.getEntity().getLocation();
        loc.getWorld().playEffect(loc, Effect.POTION_BREAK, event.getPotion().getItem().getDurability());
    }
}
