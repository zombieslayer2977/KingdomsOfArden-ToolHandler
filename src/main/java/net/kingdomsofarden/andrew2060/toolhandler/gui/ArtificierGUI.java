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

public class ArtificierGUI {

    private static ArrayList<Integer> inputSlotList;
    private static int[] soulGemSlots;
    private static int[] greenWoolSlots;
    private static int[] redWoolSlots;
    private static int[] whiteWoolSlots;
    private static int[] expBottleSlots;
    private static int[] toolSlots;
    private static int scrollSlot;
    private static int bookSlot;
    private static int modSignSlot;
    private static int modSlotSignSlot;
    private static int scrollSignSlot;
    private static int gemCombinerSignSlot;

    static {
        inputSlotList = new ArrayList<Integer>();
        for(Integer i : new Integer[] {10,11,14,15,16,37,38,41,42,43}) {
            inputSlotList.add(i);
        }
        soulGemSlots = new int[] {1,5,32,33};
        greenWoolSlots = new int[] {19,23,46,50};
        redWoolSlots = new int[] {20,25,47,52};
        whiteWoolSlots = new int[] {24,51};
        expBottleSlots = new int[] {7,34};
        toolSlots = new int[] {2,6};
        scrollSlot = 28;
        bookSlot = 29;
        modSignSlot = 9;
        modSlotSignSlot = 13;
        scrollSignSlot = 36;
        gemCombinerSignSlot = 40;
    }

    private static Inventory constructArtificierGUI() {
        Inventory inv = Bukkit.createInventory(new ArtificierInventoryHolder(), 54, "Artificier Table");
        
        //Fill Inventory with sticks w/ no name
        ItemStack stick = new ItemStack(Material.STICK);
        ItemMeta stickMeta = stick.getItemMeta();
        stickMeta.setDisplayName(" ");
        stick.setItemMeta(stickMeta); 
        for(int i = 0; i<inv.getSize() ; i++) {
            inv.setItem(i, stick);
        }
        //Insert Empty Spaces/Input Slots
        for(Integer i : inputSlotList) {
            inv.setItem(i, null);
        }
        //Populate Soul Gem Item Slots
        ItemStack soulGem = new ItemStack(Material.EMERALD);
        ItemMeta soulGemMeta = soulGem.getItemMeta();
        soulGemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + "Soul Gem");
        LinkedList<String> soulGemLore = new LinkedList<String>();
        soulGemLore.add(ChatColor.GRAY + "Insert A Soul Gem in the slot Below");
        soulGemMeta.setLore(soulGemLore);
        soulGem.setItemMeta(soulGemMeta);
        for(int i : soulGemSlots) {
            inv.setItem(i, soulGem);
        }
        //Populate Wool Item Slots
        ItemStack greenWool = new ItemStack(Material.WOOL,1,(short) 5);
        ItemStack redWool = new ItemStack(Material.WOOL,1,(short) 14);
        ItemStack whiteWool = new ItemStack(Material.WOOL,1,(short) 0);
        ItemMeta greenMeta = greenWool.getItemMeta();
        ItemMeta redMeta = redWool.getItemMeta();
        ItemMeta whiteMeta = whiteWool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.GREEN + "Combine");
        redMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + "Cancel");
        whiteMeta.setDisplayName("");
        greenWool.setItemMeta(greenMeta);
        redWool.setItemMeta(redMeta);
        whiteWool.setItemMeta(whiteMeta);
        for(int i : greenWoolSlots) {
            inv.setItem(i,greenWool);
        }
        for(int i : redWoolSlots) {
            inv.setItem(i,redWool);
        }
        for(int i : whiteWoolSlots) {
            inv.setItem(i, whiteWool);
        }
        //Populate Essence of Enchanting Slots
        ItemStack expBottle = new ItemStack(Material.EXP_BOTTLE,64);
        ItemMeta expBottleMeta = expBottle.getItemMeta();
        expBottleMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + "Essence of Enchantment");
        LinkedList<String> expBottleLore = new LinkedList<String>();
        expBottleLore.add(ChatColor.RESET + "" + ChatColor.BLUE + "Insert essence of enchantments below");
        expBottleMeta.setLore(expBottleLore);
        expBottle.setItemMeta(expBottleMeta);
        for(int i : expBottleSlots) {
            inv.setItem(i, expBottle);
        }
        //Tools
        ItemStack tool = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta toolMeta = tool.getItemMeta();
        toolMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BLUE + "Weapon/Tool/Armor Piece");
        LinkedList<String> toolLore = new LinkedList<String>();
        toolLore.add(ChatColor.GRAY + "Insert a weapon/tool/armor piece below");
        toolMeta.setLore(toolLore);
        tool.setItemMeta(toolMeta);
        for(int i : toolSlots) {
            inv.setItem(i, tool);
        }
        //Mod Installer Sign
        ItemStack modSign = new ItemStack(Material.SIGN);
        ItemMeta modSignMeta = modSign.getItemMeta();
        modSignMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + "Mod Installer");
        LinkedList<String> modSignLore = new LinkedList<String>();
        modSignLore.add(ChatColor.GRAY + "Install new mods onto an item with empty mod slots here");
        modSignLore.add(ChatColor.GRAY + "by combining a soul gem.");
        modSignLore.add(ChatColor.GRAY + "The greater the power of the soul gem, ");
        modSignLore.add(ChatColor.GRAY + "The greater the chances of getting a rare mod.");
        modSignMeta.setLore(modSignLore);
        modSign.setItemMeta(modSignMeta);
        inv.setItem(modSignSlot,modSign);
        //Mod Slot Sign
        ItemStack modSlotCreatorSign = new ItemStack(Material.SIGN);
        ItemMeta modSlotCreatorMeta = modSlotCreatorSign.getItemMeta();
        modSlotCreatorMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + "Mod Slot Installer");
        LinkedList<String> modSlotCreatorLore = new LinkedList<String>();
        modSlotCreatorLore.add(ChatColor.GRAY + "Add new mod slots to an item here!");
        modSlotCreatorLore.add(ChatColor.GRAY + "Note that the more mod slots a tool/armor piece has,");
        modSlotCreatorLore.add(ChatColor.GRAY + "the greater the chances of it breaking on upgrade!");
        modSlotCreatorMeta.setLore(modSlotCreatorLore);
        modSlotCreatorSign.setItemMeta(modSlotCreatorMeta);
        inv.setItem(modSlotSignSlot, modSlotCreatorSign);
        //Soul Gem Combiner Sign
        ItemStack soulGemCombinerSign = new ItemStack(Material.SIGN);
        ItemMeta soulGemCombinerMeta = soulGemCombinerSign.getItemMeta();
        soulGemCombinerMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + "Soul Gem Combiner");
        LinkedList<String> soulGemCombinerLore = new LinkedList<String>();
        soulGemCombinerLore.add(ChatColor.GRAY + "Combine two soul gems of the same power level here");
        soulGemCombinerLore.add(ChatColor.GRAY + "to upgrade to the next tier.");
        soulGemCombinerLore.add(ChatColor.GRAY + "The higher your enchanter level!");
        soulGemCombinerLore.add(ChatColor.GRAY + "the more likely you are to succeed!");
        soulGemCombinerMeta.setLore(soulGemCombinerLore);
        soulGemCombinerSign.setItemMeta(soulGemCombinerMeta);
        inv.setItem(gemCombinerSignSlot, soulGemCombinerSign);
        //Temporary Fillers
        inv.setItem(bookSlot,new ItemStack(Material.BOOK));
        inv.setItem(scrollSlot, new ItemStack(Material.MAP));
        inv.setItem(scrollSignSlot, new ItemStack(Material.SIGN));
        return inv;
    }

    public static class ArtificierInventoryHolder implements InventoryHolder {
        
        @Override
        public Inventory getInventory() {
            return null;
        }
        
    }
    public static Inventory getInventoryInstance() {
        return constructArtificierGUI();
    }

    public static ArrayList<Integer> getInputSlots() {
        return inputSlotList;
    }

}
