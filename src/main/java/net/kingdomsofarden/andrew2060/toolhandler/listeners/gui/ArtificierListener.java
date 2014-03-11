package net.kingdomsofarden.andrew2060.toolhandler.listeners.gui;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.util.Util;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.gui.ArtificierGUI;
import net.kingdomsofarden.andrew2060.toolhandler.util.ModUtil;

public class ArtificierListener implements Listener {
    private ToolHandlerPlugin plugin;
    private HashMap<Location, Inventory> activeModChests;
    public ArtificierListener(ToolHandlerPlugin toolHandlerPlugin) {
        this.plugin = toolHandlerPlugin;
        this.activeModChests = new HashMap<Location,Inventory>();
    }
    
    @SuppressWarnings("deprecation")
    public void updateInventory(Player p) {
        p.updateInventory();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        if(inv.getHolder() instanceof ArtificierGUI.ArtificierInventoryHolder) {
            if(!ArtificierGUI.getInputSlots().contains(event.getSlot())) {
                if(event.getRawSlot() < 54) {
                    event.setCancelled(true);
                    Player p = (Player) event.getWhoClicked();
                    updateInventory(p);
                    switch(event.getSlot()) {

                    case 19: {  //Mod Installer Slot
                        ItemStack soulGem = inv.getItem(10);
                        ItemStack item = inv.getItem(11);
                        //Validate
                        if(soulGem == null) {
                            p.sendMessage(ChatColor.GRAY + "The soul gem slot is Empty!");
                            break;
                        }
                        if(item == null) {
                            p.sendMessage(ChatColor.GRAY + "The item slot is Empty!");
                            break;
                        }
                        if(!soulGem.getType().equals(Material.EMERALD)) {
                            p.sendMessage(ChatColor.GRAY + "The item in the soul gem slot is not a soul gem!");
                            break;
                        }
                        if(!soulGem.getItemMeta().hasDisplayName()) {
                            p.sendMessage(ChatColor.GRAY + "The soul gem in the soul gem slot is uncharged!");
                            break;
                        }
                        if(!(Util.isArmor(item.getType()) || Util.isWeapon(item.getType()))) {
                            p.sendMessage(ChatColor.GRAY + "This is not a valid item type to attempt to install a mod to!");
                            break;
                        }
                        //Combine
                        ItemStack inserted = null;
                        try {
                            inserted = addMod(item,soulGem);
                        } catch (IllegalArgumentException e) {
                            p.sendMessage(ChatColor.GRAY + e.getMessage());
                        }
                        if(inserted != null) {
                            if(soulGem.getAmount() > 1) {
                                soulGem.setAmount(soulGem.getAmount()-1);
                            } else {
                                inv.setItem(10, new ItemStack(Material.AIR));
                            }
                            inv.setItem(11, inserted);
                            p.sendMessage(ChatColor.GRAY + "Item Modification Successful");
                        } else {
                            p.sendMessage(ChatColor.GRAY + "No free mod slots to insert a new mod!");
                        }
                        break;
                    }
                    case 23: {  //Mod Slot Creator
                        ItemStack soulGem = inv.getItem(14);
                        ItemStack item = inv.getItem(15);
                        ItemStack essenceOfEnchanting = inv.getItem(16);

                        //Validate
                        if(soulGem == null) {
                            p.sendMessage(ChatColor.GRAY + "The soul gem slot is Empty!");
                            break;
                        }
                        if(item == null) {
                            p.sendMessage(ChatColor.GRAY + "The item slot is Empty!");
                            break;
                        }
                        if(essenceOfEnchanting == null) {
                            p.sendMessage(ChatColor.GRAY + "The essence of enchanting slot is Empty!");
                            break;
                        }
                        if(!soulGem.getType().equals(Material.EMERALD)) {
                            p.sendMessage(ChatColor.GRAY + "The item in the soul gem slot is not a soul gem!");
                            break;
                        }
                        if(!soulGem.getItemMeta().hasDisplayName()) {
                            p.sendMessage(ChatColor.GRAY + "The soul gem in the soul gem slot is uncharged!");
                            break;
                        }
                        if(!(Util.isArmor(item.getType()) || Util.isWeapon(item.getType()))) {
                            p.sendMessage(ChatColor.GRAY + "This is not a valid item type to attempt to install a mod to!");
                            break;
                        }
                        if(!essenceOfEnchanting.getType().equals(Material.EXP_BOTTLE)) {
                            p.sendMessage(ChatColor.GRAY + "The item in the essence of enchanting slot is not an essence of enchanting!");
                            break;
                        }
                        if(!(essenceOfEnchanting.getAmount() == 64)) {
                            p.sendMessage(ChatColor.GRAY + "Creating new mod slots require 64 essence of enchanting bottles!");
                            break;
                        }
                        if(soulGem.getAmount() > 1) {
                            soulGem.setAmount(soulGem.getAmount()-1);
                        } else {
                            inv.clear(14);
                        }
                        inv.setItem(16, new ItemStack(Material.AIR));
                        ItemStack newSlotItem = createNewModSlot(soulGem,item);
                        if(newSlotItem == null) {
                            inv.setItem(15, new ItemStack(Material.AIR));
                            p.sendMessage(ChatColor.GRAY + "Item upgrade unsuccessful, item broke!");
                            break;
                        } else {
                            inv.setItem(15, newSlotItem);
                            p.sendMessage(ChatColor.GRAY + "Item upgrade successful!");
                            break;
                        }
                    }
                    case 50: {  //Soul Gem Combiner
                        break;
                    }
                    }
                    updateInventory(p); //Prevent nasty dupes
                }

                return;
            }
        }
    }


    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractModCombiner(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Block b = event.getClickedBlock();
        if(!(b.getType().equals(Material.ENCHANTMENT_TABLE) && b.getRelative(BlockFace.DOWN).getType().equals(Material.ENDER_CHEST))) {
            return;
        }
        event.setCancelled(true);
        Player p = event.getPlayer();
        Location loc = b.getLocation();
        if(activeModChests.containsKey(loc)) {
            p.openInventory(activeModChests.get(loc));
        } else {
            Inventory inv = ArtificierGUI.getInventoryInstance();
            activeModChests.put(loc, inv);
            p.openInventory(inv);
        }

    }
    public HashMap<Location, Inventory> getActiveArtificierTables() {
        return this.activeModChests;
    }
    private ItemStack addMod(ItemStack tool, ItemStack soulGem) {
        String name = ChatColor.stripColor(soulGem.getItemMeta().getDisplayName());
        name = name.toLowerCase();
        int weight = 1;
        if(name.contains("weak")) {
            weight = 2;
        } else if(name.contains("common")) {
            weight = 3;
        } else if(name.contains("strong")) {
            weight = 4;
        } else if(name.contains("major")) {
            weight = 5;
        } else if(name.contains("master")) {
            weight = 6;
        } else if(name.contains("legendary")) {
            weight = 7;
        }
        return plugin.getModManager().addMod(tool, weight);
    }
    
    private ItemStack createNewModSlot(ItemStack soulGem, ItemStack item) {
        String name = ChatColor.stripColor(soulGem.getItemMeta().getDisplayName());
        name = name.toLowerCase();
        double modifier = 1D;
        if(name.contains("weak")) {
            modifier = 2D;
        } else if(name.contains("common")) {
            modifier = 3D;
        } else if(name.contains("strong")) {
            modifier = 4D;
        } else if(name.contains("major")) {
            modifier = 5D;
        } else if(name.contains("master")) {
            modifier = 6D;
        } else if(name.contains("legendary")) {
            modifier = 7D;
        }
        double multiply = modifier/7.0D;
        return ModUtil.addModSlot(item,multiply);
    }
}
