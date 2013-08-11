package net.kingdomsofarden.andrew2060.toolhandler.potions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class PotionTaskWrapper {
    public PotionTaskWrapper() {
        this.potionTaskMap = new HashMap<PotionEffectType, List<BukkitTask>>();
    }
    private HashMap<PotionEffectType, List<BukkitTask>> potionTaskMap;
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
        for(PotionEffectType potionEffectType : potionTaskMap.keySet()) {
            for(BukkitTask task : potionTaskMap.get(potionEffectType)) {
                if(task.equals(toRemove)) {
                    potionTaskMap.get(potionEffectType).remove(task);
                    return;
                }
            }
        }
    }
    public void removeAllTasks() {
        for(PotionEffectType potionEffectType : potionTaskMap.keySet()) {
            for(BukkitTask task : potionTaskMap.get(potionEffectType)) {  
                potionTaskMap.get(potionEffectType).remove(task);
                return;
            }
        }
    }
    public void removeTask(int taskId) {
        for(PotionEffectType potionEffectType : potionTaskMap.keySet()) {
            for(BukkitTask task : potionTaskMap.get(potionEffectType)) {
                if(task.getTaskId() == taskId) {
                    potionTaskMap.get(potionEffectType).remove(task);
                    return;
                }
            }
        }
    }
}
