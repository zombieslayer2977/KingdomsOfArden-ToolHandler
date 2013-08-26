package net.kingdomsofarden.andrew2060.toolhandler.listeners.mechanics;

import net.kingdomsofarden.andrew2060.toolhandler.clienteffects.ClientEffectSender;
import net.kingdomsofarden.andrew2060.toolhandler.clienteffects.ClientEffectType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.util.Vector;

import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;

public class RegenManaListener implements Listener {
    private CharacterManager cM;
    public RegenManaListener(CharacterManager cM) {
        this.cM = cM;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if(event.getEntity() instanceof Player) {
            if(event.getRegainReason() == RegainReason.MAGIC_REGEN) {
                Hero h = cM.getHero((Player)event.getEntity());
                int newMana = (int) (h.getMana() + h.getMaxMana() * 0.05);
                h.setMana(newMana > h.getMaxMana() ? h.getMaxMana() : newMana);
                if(h.getMana() == newMana) {
                    ClientEffectSender.playClientEffect(h.getPlayer(), ClientEffectType.HAPPY_VILLAGER, new Vector(0.50,1.00,0.50), 1.5F, 10, true, 0.50);
                }
            }
        }
    }

}
