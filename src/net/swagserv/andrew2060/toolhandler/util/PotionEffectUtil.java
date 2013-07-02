package net.swagserv.andrew2060.toolhandler.util;

import java.util.Collection;

import net.swagserv.andrew2060.toolhandler.ToolHandlerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectUtil {
    
    private ToolHandlerPlugin plugin;

    public PotionEffectUtil(ToolHandlerPlugin plugin) {
        this.plugin = plugin;
    }

    public void addPotionEffectStacking(final PotionEffect effect, final Player p) {
        PotionEffectType searchType = effect.getType();
        Collection<PotionEffect> activeEffects = p.getActivePotionEffects();
        PotionEffect search = null;
        //Find the matching PotionEffect
        if(p.hasPotionEffect(searchType)) {
            for(PotionEffect pE : activeEffects) {
                if(pE.getType().equals(searchType)) {
                    search = pE;
                    break;
                } else {
                    continue;
                }
            }
        }
        //If the matching potion effect is not found/expired, then just apply the effect directly
        //If a similar effect already preexists we must determine how the effects will be stacked
        if(search == null) {
            p.addPotionEffect(effect);
            return;
        } else {
            //If the newly applied potion effect has a greater amplifier or equal amplifier to the prexisting potion effect
            if(effect.getAmplifier() >= search.getAmplifier()) {
                //If the newly applied effect also has a greater than or equal length remaining duration
                //Then we can just override the effect since the new effect would expire at a later time or at the same time
                //With equal or greater amplifier
                //If the newly applied effect has a shorter length we must schedule reinstatement of the longer potion effect
                if(effect.getDuration() >= search.getDuration()) {
                    p.addPotionEffect(effect,true);
                    return;
                } else {
                    int remainingDuration = search.getDuration() - effect.getDuration();
                    //First force apply the new effect immediately
                    p.addPotionEffect(effect,true);
                    //Make the remaining effect when the applied one expires
                    final PotionEffect remainder = searchType.createEffect(remainingDuration, search.getAmplifier());
                    //Schedule another attempt to apply the remaining duration when the time of the new effect expires
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

                        @Override
                        public void run() {
                            addPotionEffectStacking(remainder, p);
                        }
                        
                    },effect.getDuration());
                    return;
                }
            }
            //If the newly applied potion effect has a smaller amplifier
            if(effect.getAmplifier() < search.getAmplifier()) {
                //If the new effect has a greater duration we must apply new potion effect once current one expires
                //If the new effect has a shorter or equal duration we can just ignore it completely
                if(effect.getDuration() > search.getDuration()) {
                    int remainingDuration = effect.getDuration() - search.getDuration();
                    final PotionEffect remainder = searchType.createEffect(remainingDuration, effect.getAmplifier());
                    //Schedule another attempt to apply the lower amplitude potion effect once current one ends
                    Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {

                        @Override
                        public void run() {
                            addPotionEffectStacking(remainder,p);
                        }
                        
                    },effect.getDuration());
                    return;
                } else {
                    return;
                }
            }
        }
    }

}
