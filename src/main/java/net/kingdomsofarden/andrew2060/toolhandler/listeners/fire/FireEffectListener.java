package net.kingdomsofarden.andrew2060.toolhandler.listeners.fire;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.effects.FireTickEffect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

import com.herocraftonline.heroes.characters.CharacterTemplate;

public class FireEffectListener implements Listener {

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        if(!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        event.setCancelled(true);
        long fireTicks = event.getDuration()*20;
        CharacterTemplate cT = null;
        Integer specialDamageCause = null;
        LivingEntity combuster = null;
        if(event.getCombuster() instanceof LivingEntity) {
            combuster = (LivingEntity)event.getCombuster();
        }
        if(event.getCombuster() instanceof Projectile) {
            combuster = ((Projectile)event.getCombuster()).getShooter();
        }
        if(!(combuster == null)) {
            cT = ToolHandlerPlugin.instance.heroesPlugin.getCharacterManager().getCharacter(combuster);
        }
        FireTickEffect effect = new FireTickEffect(fireTicks, cT, specialDamageCause);
        CharacterTemplate applyTo = ToolHandlerPlugin.instance.heroesPlugin.getCharacterManager().getCharacter((LivingEntity) event.getEntity());
        applyTo.addEffect(effect); 
        applyTo.getEntity().setFireTicks(0);
    }
    
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        
    }
}
