package net.kingdomsofarden.andrew2060.toolhandler.listeners.potions;

import java.util.Collection;

import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
        Player p = event.getPlayer();
        if(i.getType() == Material.POTION) {
            Potion pot = Potion.fromItemStack(i);
            Collection<PotionEffect> effects = pot.getEffects();
            pEMan.addPotionEffectStacking(effects, p, false);
        } else if(i.getType() == Material.GOLDEN_APPLE) {
            
            if(i.getData().getData() == 2) {    //Enchanted
                pEMan.addPotionEffectStacking(PotionEffectType.REGENERATION.createEffect(1200, 2), p, false);
            } else {
                pEMan.addPotionEffectStacking(PotionEffectType.REGENERATION.createEffect(1200, 1), p, false);
            }        
            return; //We have our own seperate handling for golden apples where we don't want to remove quite yet or cancel the event
            
        } else {
            return;
        }
       
        event.setCancelled(true);
        //Cancelling prevents item from being consumed, change/update ourselves
        int amount = i.getAmount() - 1;
        int handSlot = p.getInventory().getHeldItemSlot();
        if(amount == 0) {
            p.getInventory().clear(handSlot);
        } else {
            i.setAmount(amount);
        }
        p.updateInventory();
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent event) {
        event.setCancelled(true);
        Collection<PotionEffect> effects = event.getPotion().getEffects();
        for(LivingEntity lE : event.getAffectedEntities()) {
            pEMan.addPotionEffectStacking(effects, lE, false);
        }
        //Cancelling prevents splash from playing so we play it ourselves
        Location loc = event.getEntity().getLocation();
        loc.getWorld().playEffect(loc, Effect.POTION_BREAK, event.getPotion().getItem().getDurability());
    }
}
