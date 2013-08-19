package net.kingdomsofarden.andrew2060.toolhandler.listeners.fire;

import net.kingdomsofarden.andrew2060.toolhandler.effects.FireTickEffect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.api.events.CharacterDamageEvent;
import com.herocraftonline.heroes.characters.CharacterTemplate;

public class FireEffectListener implements Listener {
    
    private Heroes plugin;

    public FireEffectListener(Heroes plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        event.setCancelled(true);
        long fireTicks = event.getDuration()*20*1000;
        CharacterTemplate cT = null;
        LivingEntity combuster = null;
        if(event.getCombuster() instanceof LivingEntity) {
            combuster = (LivingEntity)event.getCombuster();
        }
        if(event.getCombuster() instanceof Projectile) {
            combuster = ((Projectile)event.getCombuster()).getShooter();
        }
        if(!(combuster == null)) {
            cT = plugin.getCharacterManager().getCharacter(combuster);
        }
        FireTickEffect effect = new FireTickEffect(fireTicks, cT);
        CharacterTemplate applyTo = plugin.getCharacterManager().getCharacter((LivingEntity) event.getEntity());
        applyTo.addEffect(effect); 
    }
    
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        event.setCancelled(true);
        long fireTicks = event.getDuration()*20*1000;
        CharacterTemplate applyTo = plugin.getCharacterManager().getCharacter((LivingEntity) event.getEntity());
        FireTickEffect effect = new FireTickEffect(fireTicks, null);
        applyTo.addEffect(effect);
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onFireDamage(CharacterDamageEvent event) {
        if(event.getCause() != DamageCause.FIRE_TICK) { 
            if(event.getEntity() instanceof LivingEntity) {
                CharacterTemplate cT = plugin.getCharacterManager().getCharacter((LivingEntity) event.getEntity());
                if(cT.hasEffect("FireTickEffect")) {
                    event.setDamage(0);;
                    cT.getEntity().setNoDamageTicks(0);
                    return;
                } else {
                    cT.addEffect(new FireTickEffect((long) (event.getEntity().getFireTicks()*1000*0.05),null));
                    cT.getEntity().setNoDamageTicks(0);
                    return;
                }
            }
        }
    }
}
