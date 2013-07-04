package net.swagserv.andrew2060.toolhandler.tasks;

import net.swagserv.andrew2060.toolhandler.ToolHandlerPlugin;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionUpdateRunnable extends BukkitRunnable {
    private PotionEffect effect;
    private LivingEntity lE;
    private ToolHandlerPlugin plugin;

    public PotionUpdateRunnable(ToolHandlerPlugin plugin, PotionEffect effect, LivingEntity lE) {
        this.plugin = plugin;
        this.effect = effect;
        this.lE = lE;
    }

    @Override
    public void run() {
        plugin.getPotionEffectHandler().addPotionEffectStacking(effect, lE);      
        plugin.getPotionEffectHandler().removeTask(lE,this.getTaskId());
    }

    
}