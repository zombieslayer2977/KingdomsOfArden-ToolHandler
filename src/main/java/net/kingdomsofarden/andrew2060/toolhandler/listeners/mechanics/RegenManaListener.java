package net.kingdomsofarden.andrew2060.toolhandler.listeners.mechanics;

import net.kingdomsofarden.andrew2060.toolhandler.clienteffects.ClientEffectSender;
import net.kingdomsofarden.andrew2060.toolhandler.clienteffects.ClientEffectType;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import com.herocraftonline.heroes.api.events.ManaChangeEvent;

public class RegenManaListener implements Listener {
    public RegenManaListener() {
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRegainHealth(ManaChangeEvent event) {
        if(event.getFinalMana() > event.getInitialMana()) {
            ClientEffectSender.playClientEffect(event.getHero().getPlayer(), ClientEffectType.HAPPY_VILLAGER, new Vector(0.50,1.00,0.50), 1.5F, 10, true, 0.50);
        }
    }

}
