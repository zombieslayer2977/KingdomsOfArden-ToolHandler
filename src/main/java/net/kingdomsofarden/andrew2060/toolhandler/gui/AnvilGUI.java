package net.kingdomsofarden.andrew2060.toolhandler.gui;

import java.util.ArrayList;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilGUI {

    private static ArrayList<Integer> inputOutputSlots;
    private static int repairSign;
    private static int improveSign;
    private static int salvageSign;
    private static int[] confirmButtons;
    static {
        inputOutputSlots = new ArrayList<Integer>();
        for(Integer i :  new Integer[] {19,22,25,28,31,34}) {
            inputOutputSlots.add(i);
        }
        repairSign = 10;
        improveSign = 13;
        salvageSign = 16;
        confirmButtons = new int[] {37,40,43};
    }
    
    public static ArrayList<Integer> getInputSlots() {
        return inputOutputSlots;
    }
    
    public static Inventory constructAnvilGui() {
        Inventory inv = Bukkit.createInventory(new AnvilInventoryHolder(), 54, "Anvil");
        //Fill Inventory with sticks w/ no name
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta stickMeta = stick.getItemMeta();
        stickMeta.setDisplayName(" ");
        stick.setItemMeta(stickMeta); 
        for(int i = 0; i<inv.getSize() ; i++) {
            inv.setItem(i, stick);
        }
        //Insert Empty Spaces/Input Slots
        for(Integer i : inputOutputSlots) {
            inv.setItem(i, null);
        }
        //Create confirmation buttons
        ItemStack confirmButton = new ItemStack(Material.WOOL,1,(short) 5);
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        confirmButtonMeta.setDisplayName("Combine");
        confirmButton.setItemMeta(confirmButtonMeta);
        for(int i : confirmButtons) {
            inv.setItem(i,confirmButton);
        }
        //Create Titling for Repairing
        ItemStack repairTitle = new ItemStack(Material.ANVIL);
        ItemMeta repairTitleMeta = repairTitle.getItemMeta();
        repairTitleMeta.setDisplayName("Item Repair");
        LinkedList<String> repairTitleLore = new LinkedList<String>();
        repairTitleLore.add(ChatColor.GRAY + "Repair Your Items Below");
        repairTitleLore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Increases the durability of your item");
        repairTitleLore.add(ChatColor.AQUA + "Must be a level 1 Blacksmith or greater");
        repairTitleLore.add(ChatColor.GRAY + "Slot 1: Item to repair");
        repairTitleLore.add(ChatColor.GRAY + "Slot 2: Repair materials");
        repairTitleMeta.setLore(repairTitleLore);
        repairTitle.setItemMeta(repairTitleMeta);
        inv.setItem(repairSign, repairTitle);
        
        //Create Titling for improving
        ItemStack improvingTitle = new ItemStack(Material.FIRE);
        ItemMeta improvingTitleMeta = improvingTitle.getItemMeta();
        improvingTitleMeta.setDisplayName("Item Improvement");
        LinkedList<String> improveTitleLore = new LinkedList<String>();
        improveTitleLore.add(ChatColor.GRAY + "Improve Your Items Below!");
        improveTitleLore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Increases the improvement quality of your item");
        improveTitleLore.add(ChatColor.AQUA + "Must be a level 5 Blacksmith or greater");
        improveTitleLore.add(ChatColor.GRAY + "Slot 1: Item to improve");
        improveTitleLore.add(ChatColor.GRAY + "Slot 2: Improvement materials");
        improvingTitleMeta.setLore(improveTitleLore);
        improvingTitle.setItemMeta(improvingTitleMeta);
        inv.setItem(improveSign, improvingTitle);

        //Create Titling for Salvaging
        ItemStack salvagingTitle = new ItemStack(Material.GOLD_INGOT);
        ItemMeta salvagingTitleMeta = salvagingTitle.getItemMeta();
        salvagingTitleMeta.setDisplayName("Item Salvage");
        LinkedList<String> salvageTitleLore = new LinkedList<String>();
        salvageTitleLore.add(ChatColor.GRAY + "Salvage Your Items Below!");
        salvageTitleLore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Salvage a gold tier or better item");
        salvageTitleLore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Recovers up to 80% of resources depending on blacksmith level");
        salvageTitleLore.add(ChatColor.GRAY.toString() + ChatColor.ITALIC + "and item condition.");
        salvageTitleLore.add(ChatColor.AQUA + "Must be a level 15 Blacksmith or greater");
        salvageTitleLore.add(ChatColor.GRAY + "Slot 1: Item to salvage");
        salvageTitleLore.add(ChatColor.GRAY + "Slot 2: Salvaged output");
        salvagingTitleMeta.setLore(salvageTitleLore);
        salvagingTitle.setItemMeta(salvagingTitleMeta);
        inv.setItem(salvageSign, salvagingTitle);
        return inv;
    }
    
    public static class AnvilInventoryHolder implements InventoryHolder {
        
        @Override
        public Inventory getInventory() {
            return null;
        }
        
    }
    
}
