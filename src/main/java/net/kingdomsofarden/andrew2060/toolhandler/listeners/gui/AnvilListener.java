package net.kingdomsofarden.andrew2060.toolhandler.listeners.gui;

import java.util.HashMap;
import java.util.Random;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.gui.AnvilGUI;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.characters.Hero;

public class AnvilListener implements Listener {
    private HashMap<Location,Inventory> activeAnvilInventories;
    private ToolHandlerPlugin plugin;

    public AnvilListener(ToolHandlerPlugin plugin) {
        this.plugin = plugin;
        this.activeAnvilInventories = new HashMap<Location,Inventory>();
    }
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAnvilInteract(PlayerInteractEvent event) {
        if(!(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ANVIL)) {
            return;
        }
        event.setCancelled(true);
        Location clickedLoc = event.getClickedBlock().getLocation();
        if(activeAnvilInventories.containsKey(clickedLoc)) {
            event.getPlayer().openInventory(activeAnvilInventories.get(clickedLoc));
        } else {
            Inventory anvil = AnvilGUI.constructAnvilGui();
            activeAnvilInventories.put(clickedLoc, anvil);
            event.getPlayer().openInventory(anvil);
        }
    }

    @SuppressWarnings("deprecation")   //Not much we can do, Bukkit requires this
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onInventoryInteract(InventoryClickEvent event) {
        Inventory inv = event.getView().getTopInventory();
        if(!(inv.getHolder() instanceof AnvilGUI.AnvilInventoryHolder)) {
            return;
        }
        if(!(AnvilGUI.getInputSlots().contains(event.getSlot())) && event.getRawSlot() < 54) {
            event.setCancelled(true);
            Player p = (Player) event.getWhoClicked();
            p.updateInventory();
            Hero h = plugin.heroesPlugin.getCharacterManager().getHero(p);
            switch(event.getSlot()) {

            case 37: {
                if(!h.hasEffect("Repair")) {
                    p.sendMessage(ChatColor.GRAY + "You must be a level 1 Blacksmith to repair!");
                    return;
                } else {
                    repair(inv.getItem(19), inv.getItem(28), event.getView().getTopInventory(), p);
                }
                return;
            }

            case 40: {
                if(!h.hasEffect("Forge")) {
                    p.sendMessage(ChatColor.GRAY + "You must be a level 5 Blacksmith to improve items!");
                    return;
                } else {
                    improve(inv.getItem(22), inv.getItem(31), event.getView().getTopInventory(), p);
                }
                return;
            }

            case 43: {
                if(!h.hasEffect("Salvage")) {
                    p.sendMessage(ChatColor.GRAY + "You must be a level 15 Blacksmith to salvage items!");
                    return;
                } else {
                    salvage(inv.getItem(25), event.getView().getTopInventory(), p);
                }
                return;
            }
            default: {
                return;
            }

            }
        }
    }
    @SuppressWarnings("deprecation")
    private void repair(ItemStack repair, ItemStack mat, Inventory anvilGUI, Player player) {
        if(repair == null || mat == null) {
            return;
        }
        int maxDurability = 0;
        Material requiredImprove = Material.DIAMOND;
        int fullRepair = 8;

        switch(repair.getType()) {
        case DIAMOND_SWORD:
        case DIAMOND_SPADE:
        case DIAMOND_HOE:
            maxDurability = 1562;
            fullRepair = 2;
            break;
        case DIAMOND_PICKAXE:
        case DIAMOND_AXE:
            maxDurability = 1562;
            fullRepair = 3;
            break;
        case DIAMOND_HELMET:
            maxDurability = 364;
            fullRepair = 5;
            break;
        case DIAMOND_CHESTPLATE:
            maxDurability = 529;
            fullRepair = 8;
            break;
        case DIAMOND_LEGGINGS:
            maxDurability = 496;
            fullRepair = 7;
            break;
        case DIAMOND_BOOTS:
            maxDurability = 430;
            fullRepair = 4;
            break;
        case IRON_SWORD:
        case IRON_SPADE:
        case IRON_HOE:
            maxDurability = 251;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 2;
            break;
        case IRON_PICKAXE:
        case IRON_AXE:
            maxDurability = 251;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 3;
            break;
        case IRON_HELMET:
            maxDurability = 166;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 5;
            break;
        case IRON_CHESTPLATE:
            maxDurability = 242;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 8;
            break;
        case IRON_LEGGINGS:
            maxDurability = 226;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 7;
            break;
        case IRON_BOOTS:
            maxDurability = 196;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 4;
            break;
        case CHAINMAIL_HELMET:
            maxDurability = 166;
            requiredImprove = Material.LEATHER;
            fullRepair = 5;
            break;
        case CHAINMAIL_CHESTPLATE:
            maxDurability = 242;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 8;
            break;
        case CHAINMAIL_LEGGINGS:
            maxDurability = 226;
            requiredImprove = Material.IRON_INGOT;
            fullRepair = 7;
            break;
        case CHAINMAIL_BOOTS:
            maxDurability = 196;
            requiredImprove = Material.LEATHER;
            fullRepair = 4;
            break;
        case GOLD_SWORD:    
        case GOLD_SPADE:
        case GOLD_HOE:
            maxDurability = 33;
            requiredImprove = Material.GOLD_INGOT;
            fullRepair = 2;
            break;
        case GOLD_PICKAXE:
        case GOLD_AXE:
            maxDurability = 33;
            requiredImprove = Material.GOLD_INGOT;
            fullRepair = 3;
            break;
        case GOLD_HELMET:
            maxDurability = 78;
            requiredImprove = Material.GOLD_INGOT;
            fullRepair = 5;
            break;
        case GOLD_CHESTPLATE:
            maxDurability = 114;
            requiredImprove = Material.GOLD_INGOT;
            fullRepair = 8;
            break;
        case GOLD_LEGGINGS:
            maxDurability = 106;
            requiredImprove = Material.GOLD_INGOT;
            fullRepair = 7;
            break;
        case GOLD_BOOTS:
            maxDurability = 92;
            requiredImprove = Material.GOLD_INGOT;
            fullRepair = 4;
            break;
        case STONE_SWORD:
        case STONE_HOE:
        case STONE_SPADE:
            maxDurability = 132;
            requiredImprove = Material.COBBLESTONE;
            fullRepair = 2;
            break;
        case STONE_PICKAXE:             
        case STONE_AXE:
            maxDurability = 132;
            requiredImprove = Material.COBBLESTONE;
            fullRepair = 3;
            break;
        case LEATHER_HELMET:
            maxDurability = 56;
            requiredImprove = Material.LEATHER;
            fullRepair = 5;
            break;
        case LEATHER_CHESTPLATE:
            maxDurability = 82;
            requiredImprove = Material.LEATHER;
            fullRepair = 8;
            break;
        case LEATHER_LEGGINGS:
            maxDurability = 76;
            requiredImprove = Material.LEATHER;
            fullRepair = 7;
            break;
        case LEATHER_BOOTS:
            maxDurability = 66;
            requiredImprove = Material.LEATHER;
            fullRepair = 4;
            break;
        case WOOD_SWORD:
        case WOOD_HOE:
        case WOOD_SPADE:
            maxDurability = 60;
            requiredImprove = Material.WOOD;
            fullRepair = 2;
            break;
        case WOOD_PICKAXE:
        case WOOD_AXE:
            maxDurability = 60;
            requiredImprove = Material.WOOD;
            fullRepair = 3;
            break;
        case BOW:
            maxDurability = 385;
            requiredImprove = Material.STRING;
            fullRepair = 3;
            break;
        default:
            player.sendMessage(ChatColor.GRAY + "This is not a valid tool or armor type to repair!");
            return;

        }
        if (mat.getType() != requiredImprove) {
            String commonName;
            switch(requiredImprove) {
            case DIAMOND:
                commonName = "diamond ore";
                break;
            case GOLD_INGOT:
                commonName = "gold ingot";
                break;
            case IRON_INGOT:
                commonName = "iron ingot";
                break;
            case LEATHER:
                commonName = "leather";
                break;
            case WOOD:
                commonName = "wood plank";
                break;
            case COBBLESTONE:
                commonName = "cobblestone";
                break;
            case STRING:
                commonName = "string";
                break;
            default: 
                commonName = "something broke here, go get andrew2060";
                break;
            }
            player.sendMessage(ChatColor.GRAY + "You need " + ChatColor.AQUA + commonName + ChatColor.GRAY + " to repair this item");
            return;
        }

        if(repair.getDurability() == 0) {
            player.sendMessage(ChatColor.GRAY + "This Item is already at Max Durability!");
            return;
        }
        if (mat.getAmount() > 1) {
            mat.setAmount(mat.getAmount() - 1);
        }
        else {
            anvilGUI.clear(28);
        }
        double multiplier = 1.00/fullRepair;
        double durabilityRestored = maxDurability*multiplier;
        short finalDurability = (short)((int)(Math.ceil(repair.getDurability()) - durabilityRestored));
        repair.setDurability(finalDurability >= 0 ? finalDurability : 0);
        Random randGen = new Random();
        double rand = randGen.nextInt(10000)*0.01;
        Hero h = plugin.heroesPlugin.getCharacterManager().getHero(player);
        double breakChance = Math.pow(h.getLevel(h.getSecondClass()), -1) * (1.0D/3.0D) * 100;
        /*Optional, add exp based on the item used to repair, remove the subsequent comment block if you want to use it*/
        if (h.getLevel(h.getSecondClass()) <= 75) {
            int exp = 0;
            switch (requiredImprove) {
            case DIAMOND:
                exp = 40;
                break;
            case IRON_INGOT:
                exp = 20;
                break;
            case GOLD_INGOT:
                exp = 12;
                break;
            case WOOD:
                exp = 4;
                break;
            case COBBLESTONE:
                exp = 8;
                break;
            case LEATHER:
                exp = 8;
                break;
            default:
                exp = 0;
            }

            h.addExp(exp, h.getSecondClass(), h.getPlayer().getLocation());
        }

        if(rand <= breakChance) {
            player.sendMessage(ChatColor.GRAY + "Repairing Failed! Your item broke");
            anvilGUI.remove(repair);
        } else {
            player.sendMessage(ChatColor.GRAY + "Repair Successful");
        }
        player.updateInventory();
    }

    @SuppressWarnings("deprecation")
    private void improve(ItemStack improve, ItemStack mat, Inventory anvilGUI, Player player) {
        if(improve == null || mat == null) {
            return;
        }
        Hero h = plugin.heroesPlugin.getCharacterManager().getHero(player);
        Material requiredImprove = Material.IRON_INGOT;
        String commonName = null;
        int t = 20;
        switch(improve.getType()) {
        case DIAMOND_SWORD:
            t = 1;
            break;
        case DIAMOND_PICKAXE:
        case DIAMOND_HOE:
        case DIAMOND_AXE:
        case DIAMOND_SPADE:
            t = 4;
            break;
        case DIAMOND_HELMET:
        case DIAMOND_CHESTPLATE:
        case DIAMOND_LEGGINGS:
        case DIAMOND_BOOTS:
            t = 3;
            break;
        case IRON_SWORD:
            t = 1;
            requiredImprove = Material.GOLD_INGOT;
            break;
        case IRON_PICKAXE:
        case IRON_HOE:
        case IRON_AXE:
        case IRON_SPADE:
            t = 4;
            requiredImprove = Material.GOLD_INGOT;
            break;
        case IRON_HELMET:
            t = 3;
            requiredImprove = Material.GOLD_INGOT;
            break;
        case IRON_CHESTPLATE:
            t = 3;
            requiredImprove = Material.GOLD_INGOT;
            break;
        case IRON_LEGGINGS:
            t = 3;
            requiredImprove = Material.GOLD_INGOT;
            break;
        case IRON_BOOTS:
            t = 3;
            requiredImprove = Material.GOLD_INGOT;
            break;
        case CHAINMAIL_HELMET:
            t = 3;
            requiredImprove = Material.LEATHER;
            break;
        case CHAINMAIL_CHESTPLATE:
            t = 3;
            requiredImprove = Material.IRON_INGOT;
            break;
        case CHAINMAIL_LEGGINGS:
            t = 3;
            requiredImprove = Material.IRON_INGOT;
            break;
        case CHAINMAIL_BOOTS:
            t = 3;
            requiredImprove = Material.LEATHER;
            break;
        case GOLD_SWORD:    
            t = 1;
            requiredImprove = Material.FLINT;
            break;
        case GOLD_PICKAXE:
        case GOLD_HOE:
        case GOLD_AXE:
        case GOLD_SPADE:
            t = 4;
            requiredImprove = Material.FLINT;
            break;
        case GOLD_HELMET:
            t = 3;
            requiredImprove = Material.FLINT;
            break;
        case GOLD_CHESTPLATE:
            t = 3;
            requiredImprove = Material.FLINT;
            break;
        case GOLD_LEGGINGS:
            t = 3;
            requiredImprove = Material.FLINT;
            break;
        case GOLD_BOOTS:
            t = 3;
            requiredImprove = Material.FLINT;
            break;
        case STONE_SWORD:
            t = 1;
            requiredImprove = Material.LEATHER;
            break;
        case STONE_PICKAXE:             
        case STONE_HOE:
        case STONE_AXE:
        case STONE_SPADE:
            t = 4;
            requiredImprove = Material.LEATHER;
            break;
        case LEATHER_HELMET:
            t = 3;
            requiredImprove = Material.WOOD;
            break;
        case LEATHER_CHESTPLATE:
            t = 3;
            requiredImprove = Material.WOOD;
            break;
        case LEATHER_LEGGINGS:
            t = 3;
            requiredImprove = Material.WOOD;
            break;
        case LEATHER_BOOTS:
            t = 3;
            requiredImprove = Material.WOOD;
            break;
        case WOOD_SWORD:
            t = 1;
            requiredImprove = Material.LEATHER;
            break;
        case WOOD_PICKAXE:
        case WOOD_HOE:
        case WOOD_AXE:
        case WOOD_SPADE:
            t = 4;
            requiredImprove = Material.WOOD;
            break;
        case BOW:
            t = 2;
            requiredImprove = Material.FLINT;
            break;
        default:
            player.sendMessage(ChatColor.GRAY + "This is not a valid tool or armor type to improve!");
            return;
        }
        if (mat.getType() != requiredImprove) {
            switch (requiredImprove) {
            case IRON_INGOT:
                commonName = "iron ingots";
                break;
            case GOLD_INGOT:
                commonName = "gold ingots";
                break;
            case FLINT:
                commonName = "flint";
                break;
            case WOOD:
                commonName = "wood planks";
                break;
            case LEATHER:
                commonName = "leather";
                break;
            default:
                commonName = "something broke here, go get andrew2060";
                break;
            }
            player.sendMessage(ChatColor.GRAY + "You need " + ChatColor.AQUA + commonName + ChatColor.GRAY + " to improve this item");
            return;
        }
        int level = h.getLevel(h.getSecondClass());
        double threshold = 0.00;
        if(level < 10) {
            threshold = 20.00;
        } else if (level < 20) {
            threshold = 40.00;
        } else if (level < 30) {
            threshold = 60.00;
        } else if (level < 40) {
            threshold = 80.00;
        } else if (level >= 40) {
            threshold = 100.00;
        }
        double quality = ImprovementUtil.getQuality(improve);
        if(quality >= threshold && threshold != 100) {
            player.sendMessage(ChatColor.GRAY + "You lack sufficient blacksmithing experience to improve this item further!");
            return;
        } else if (quality >= 100) {
            player.sendMessage(ChatColor.GRAY + "This item cannot be improved to a higher quality.");
            return;
        } else {
            
            switch(t) {
            case 1: {
                CachedWeaponInfo cached = plugin.getCacheManager().getCachedWeaponInfo(improve);
                ItemStack cacheWrite = improve;
                try {
                    cached.setQuality(cached.getQuality() + 4 > threshold ? threshold : cached.getQuality() + 4);
                } catch (ItemStackChangedException e) {
                    cacheWrite = e.newStack;
                }
                ImprovementUtil.applyEnchantmentLevel(cacheWrite, Enchantment.DAMAGE_ALL);
                break;
            }
            case 2: {
                CachedWeaponInfo cached = plugin.getCacheManager().getCachedWeaponInfo(improve);
                ItemStack cacheWrite = improve;
                try {
                    cached.setQuality(cached.getQuality() + 4 > threshold ? threshold : cached.getQuality() + 4);
                } catch (ItemStackChangedException e) {
                    cacheWrite = e.newStack;
                }
                ImprovementUtil.applyEnchantmentLevel(cacheWrite, Enchantment.ARROW_DAMAGE);
                break;
            }
            case 3:
                ImprovementUtil.applyEnchantmentLevel(improve, Enchantment.PROTECTION_ENVIRONMENTAL);
                break;
            case 4:
                ImprovementUtil.applyEnchantmentLevel(improve, Enchantment.DIG_SPEED);
                break;
            }
            player.sendMessage("Item Improvement Successful!");
            if (mat.getAmount() > 1) {
                mat.setAmount(mat.getAmount() - 1);
            }
            else {
                anvilGUI.clear(31);
            }
            int exp = 0;
            switch (requiredImprove) {
            case IRON_INGOT:
                exp = 60;
                break; 
            case GOLD_INGOT:
                exp = 20;
                break;
            case LEATHER:
                exp = 12;
                break;
            case WOOD:
                exp = 4;
                break;
            case FLINT:
                exp = 8;
                break;
            default:
                exp = 0;
            }   
            h.addExp(exp, h.getSecondClass(), h.getPlayer().getLocation());      
            player.updateInventory();
        }
    }

    @SuppressWarnings("deprecation")
    private void salvage(ItemStack salvage, Inventory anvilGUI, Player player) {
        if(salvage == null) {
            return;
        }
        Salvageable salvageable = null;
        try {
            salvageable = Salvageable.valueOf(salvage.getType().name());
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.GRAY + "This is not a salvageable item!");
            return;
        }
        if(anvilGUI.getItem(34) != null) {
            player.sendMessage(ChatColor.GRAY + "There is currently still items in the salvage output slot! Salvaging cannot be attempted until that is removed!");
            return;
        }
        Hero h = plugin.heroesPlugin.getCharacterManager().getHero(player);
        double deductable = h.getLevel(h.getSecondClass())*0.15*0.01;
        double calcDurability = salvage.getDurability() + (salvageable.getMaxDurability() * (0.2-deductable));
        int resultAmount = 0;
        if(salvage.getType() == Material.IRON_FENCE) {
            resultAmount = 6;
            if(salvage.getAmount() > 16) {
                salvage.setAmount(salvage.getAmount()-16);
            } else if (salvage.getAmount() == 16) {
                anvilGUI.clear(25); 
            } else {
                player.sendMessage(ChatColor.GRAY + "Iron Bars can only be salvaged 16 at a time!");
                return;
            }
        } else {
            resultAmount = (int)Math.floor(salvageable.getResultAmountBase()*((salvageable.getMaxDurability()-calcDurability)/(salvageable.getMaxDurability())));
            anvilGUI.clear(25);
        }
        if(resultAmount > 0) {
            anvilGUI.setItem(34, new ItemStack(salvageable.getTo(),resultAmount));
        } else {
            player.sendMessage(ChatColor.GRAY + "This item was too wrecked to salvage and was destroyed!");
            player.updateInventory();
            return;
        }
        player.updateInventory();
        int exp = 0;
        switch (salvageable.getTo()) {
        case DIAMOND:
            exp = 60;
            break; 
        case IRON_INGOT:
            exp = 20;
            break;
        case GOLD_INGOT:
            exp = 12;
            break;
        case WOOD:
            exp = 4;
            break;
        case FLINT:
            exp = 8;
            break;
        default:
            exp = 0;
        }   
        h.addExp(exp*resultAmount, h.getSecondClass(), h.getPlayer().getLocation());     
        h.getPlayer().sendMessage(ChatColor.GRAY + "Salvage Successful! Salvaged " + resultAmount + " items!");
    }

    private enum Salvageable {

        DIAMOND_SPADE(Material.DIAMOND,1,Material.DIAMOND_SPADE.getMaxDurability()),
        DIAMOND_SWORD(Material.DIAMOND,2,Material.DIAMOND_SWORD.getMaxDurability()),
        DIAMOND_HOE(Material.DIAMOND,2,Material.DIAMOND_HOE.getMaxDurability()),
        DIAMOND_PICKAXE(Material.DIAMOND,3,Material.DIAMOND_PICKAXE.getMaxDurability()),
        DIAMOND_AXE(Material.DIAMOND,3,Material.DIAMOND_AXE.getMaxDurability()),
        DIAMOND_HELMET(Material.DIAMOND,5,Material.DIAMOND_HELMET.getMaxDurability()),
        DIAMOND_CHESTPLATE(Material.DIAMOND,8,Material.DIAMOND_CHESTPLATE.getMaxDurability()),
        DIAMOND_LEGGINGS(Material.DIAMOND,7,Material.DIAMOND_LEGGINGS.getMaxDurability()),
        DIAMOND_BOOTS(Material.DIAMOND,4,Material.DIAMOND_BOOTS.getMaxDurability()),
        IRON_SPADE(Material.IRON_INGOT,1,Material.IRON_SPADE.getMaxDurability()),
        IRON_SWORD(Material.IRON_INGOT,2,Material.IRON_SWORD.getMaxDurability()),
        IRON_HOE(Material.IRON_INGOT,2,Material.IRON_HOE.getMaxDurability()),
        IRON_PICKAXE(Material.IRON_INGOT,3,Material.IRON_PICKAXE.getMaxDurability()),
        IRON_AXE(Material.IRON_INGOT,3,Material.IRON_AXE.getMaxDurability()),
        IRON_HELMET(Material.IRON_INGOT,5,Material.IRON_HELMET.getMaxDurability()),
        IRON_CHESTPLATE(Material.IRON_INGOT,8,Material.IRON_CHESTPLATE.getMaxDurability()),
        IRON_LEGGINGS(Material.IRON_INGOT,7,Material.IRON_LEGGINGS.getMaxDurability()),
        IRON_BOOTS(Material.IRON_INGOT,4,Material.IRON_BOOTS.getMaxDurability()),
        IRON_FENCE(Material.IRON_INGOT,1,(short)0),
        CHAINMAIL_HELMET(Material.IRON_FENCE,5,Material.CHAINMAIL_HELMET.getMaxDurability()),
        CHAINMAIL_CHESTPLATE(Material.IRON_FENCE,8,Material.CHAINMAIL_CHESTPLATE.getMaxDurability()),
        CHAINMAIL_LEGGINGS(Material.IRON_FENCE,7,Material.CHAINMAIL_LEGGINGS.getMaxDurability()),
        CHAINMAIL_BOOTS(Material.IRON_FENCE,4,Material.CHAINMAIL_BOOTS.getMaxDurability()),
        GOLD_SPADE(Material.GOLD_INGOT,1,Material.GOLD_SPADE.getMaxDurability()),
        GOLD_SWORD(Material.GOLD_INGOT,2,Material.GOLD_SWORD.getMaxDurability()),
        GOLD_HOE(Material.GOLD_INGOT,2,Material.GOLD_HOE.getMaxDurability()),
        GOLD_PICKAXE(Material.GOLD_INGOT,3,Material.GOLD_PICKAXE.getMaxDurability()),
        GOLD_AXE(Material.GOLD_INGOT,3,Material.GOLD_AXE.getMaxDurability()),
        GOLD_HELMET(Material.GOLD_INGOT,5,Material.GOLD_HELMET.getMaxDurability()),
        GOLD_CHESTPLATE(Material.GOLD_INGOT,8,Material.GOLD_CHESTPLATE.getMaxDurability()),
        GOLD_LEGGINGS(Material.GOLD_INGOT,7,Material.GOLD_LEGGINGS.getMaxDurability()),
        GOLD_BOOTS(Material.GOLD_INGOT,4,Material.GOLD_BOOTS.getMaxDurability());

        private Salvageable(Material to, int baseAmount, short maxDura) {
            setTo(to);
            setResultAmountBase(baseAmount);
            setMaxDurability(maxDura);
        }

        private Material to;
        private double resultAmountBase;
        private short maxDurability;

        public Material getTo() {
            return to;
        }
        private void setTo(Material to) {
            this.to = to;
        }
        public double getResultAmountBase() {
            return resultAmountBase;
        }
        private void setResultAmountBase(double resultAmountBase) {
            this.resultAmountBase = resultAmountBase;
        }
        public short getMaxDurability() {
            return maxDurability;
        }
        private void setMaxDurability(short maxDurability) {
            this.maxDurability = maxDurability;
        }


    }

    public HashMap<Location, Inventory> getActiveAnvilChests() {
        return activeAnvilInventories;
    }
}
