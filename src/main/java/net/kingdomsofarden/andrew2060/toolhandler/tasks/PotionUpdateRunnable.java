package net.kingdomsofarden.andrew2060.toolhandler.tasks;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionUpdateRunnable extends BukkitRunnable {
    private PotionEffect effect;
    private LivingEntity lE;
    private ToolHandlerPlugin plugin;
    private Boolean hiddenEffect;

    public PotionUpdateRunnable(ToolHandlerPlugin plugin, PotionEffect effect, LivingEntity lE, Boolean hidden) {
        this.plugin = plugin;
        this.effect = effect;
        this.lE = lE;
        this.hiddenEffect = hidden;
    }
    
    
    @Override
    public void run() {
        plugin.getPotionEffectHandler().addPotionEffectStacking(effect, lE, hiddenEffect);      
        plugin.getPotionEffectHandler().removeTask(lE,this.getTaskId());
    }

    
}