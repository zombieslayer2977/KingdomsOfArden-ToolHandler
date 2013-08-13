package net.kingdomsofarden.andrew2060.toolhandler.potions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class PotionTaskWrapper {
    private ConcurrentHashMap<PotionEffectType, List<BukkitTask>> potionTaskMap;
    public PotionTaskWrapper() {
        this.potionTaskMap = new ConcurrentHashMap<PotionEffectType, List<BukkitTask>>();
    }
    public void removePotionEffect(PotionEffectType type) {
        if(potionTaskMap.containsKey(type)) {
            for(BukkitTask task: potionTaskMap.get(type)) {
                task.cancel();
                potionTaskMap.get(type).remove(task);
            }
        }
    }
    public void addTask(PotionEffectType type, BukkitTask task) {
        if(potionTaskMap.containsKey(type)) {
            potionTaskMap.get(type).add(task);
            return;
        } else {
            List<BukkitTask> taskList = new ArrayList<BukkitTask>();
            taskList.add(task);
            potionTaskMap.put(type, taskList);
        }
    }
    public void removeTask(BukkitTask toRemove) {
        for(final PotionEffectType potionEffectType : potionTaskMap.keySet()) {
            for(final BukkitTask task : potionTaskMap.get(potionEffectType)) {
                if(task.equals(toRemove)) {
                    Bukkit.getScheduler().runTask(ToolHandlerPlugin.instance, new Runnable() {
                        
                        public void run() {
                            potionTaskMap.get(potionEffectType).remove(task);
                        }
                        
                    });
                    return;
                }
            }
        }
    }
    public void removeAllTasks() {
        for(final PotionEffectType potionEffectType : potionTaskMap.keySet()) {
            for(final BukkitTask task : potionTaskMap.get(potionEffectType)) {
                Bukkit.getScheduler().runTask(ToolHandlerPlugin.instance, new Runnable() {
                    
                    public void run() {
                        potionTaskMap.get(potionEffectType).remove(task);
                    }
                    
                });
                continue;
            }
        }
    }
    public void removeTask(int taskId) {
        for(final PotionEffectType potionEffectType : potionTaskMap.keySet()) {
            for(final BukkitTask task : potionTaskMap.get(potionEffectType)) {
                if(task.getTaskId() == taskId) {
                    Bukkit.getScheduler().runTask(ToolHandlerPlugin.instance, new Runnable() {
                        
                        public void run() {
                            potionTaskMap.get(potionEffectType).remove(task);
                        }
                        
                    });
                    return;
                }
            }
        }
    }
}
