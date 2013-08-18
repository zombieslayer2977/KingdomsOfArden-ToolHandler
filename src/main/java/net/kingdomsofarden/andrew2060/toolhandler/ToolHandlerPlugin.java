package net.kingdomsofarden.andrew2060.toolhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.kingdomsofarden.andrew2060.toolhandler.commands.ModCommandExecutor;
import net.kingdomsofarden.andrew2060.toolhandler.commands.RefreshLoreCommandExecutor;
import net.kingdomsofarden.andrew2060.toolhandler.commands.ReloadCommandExecutor;
import net.kingdomsofarden.andrew2060.toolhandler.gui.AnvilGUI;
import net.kingdomsofarden.andrew2060.toolhandler.gui.ArtificierGUI;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.crafting.CraftingListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.crafting.ShiftClickListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.durability.ChainMailListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.durability.DurabilityChangeListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.effects.HealingEffectListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.gui.AnvilListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.gui.ArtificierListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.lore.ArmorLoreListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.lore.WeaponLoreListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.mods.ModListener;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;
import net.kingdomsofarden.andrew2060.toolhandler.tasks.ArmorPassiveTask;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.herocraftonline.heroes.Heroes;

public class ToolHandlerPlugin extends JavaPlugin{

    public static ToolHandlerPlugin instance;

    public Heroes heroesPlugin;

    public Permission permission;

    private ModManager modManager;

    private DurabilityChangeListener qualityListener;

    private CraftingListener craftingListener;
    private ShiftClickListener craftingShiftClickListener;
    private ChainMailListener chainmailListener;

    private ArmorLoreListener armorLoreListener;
    private WeaponLoreListener weapLoreListener;

    private ModListener modListener;

    private ArtificierListener artificierListener;
    private AnvilListener anvilListener;

    private Random rand;
    private HealingEffectListener healingEffectListener;

    private PotionEffectManager potionEffectManager;


    // Gets the 4 character version identifier associated with this version of tool lore to determine if an update is needed.
    public static String versionIdentifier = ChatColor.AQUA + "" + ChatColor.BLACK + "" + ChatColor.RESET + "";



    public void onEnable() {
        //Load the Heroes plugin instance
        this.heroesPlugin = (Heroes)Bukkit.getPluginManager().getPlugin("Heroes");
        //Initialize Listeners
        this.qualityListener = new DurabilityChangeListener();

        this.craftingListener = new CraftingListener();
        this.craftingShiftClickListener = new ShiftClickListener(this);

        this.chainmailListener = new ChainMailListener();

        this.armorLoreListener = new ArmorLoreListener();
        this.weapLoreListener = new WeaponLoreListener();

        this.modListener = new ModListener(this);

        this.artificierListener = new ArtificierListener(this);
        this.anvilListener = new AnvilListener(this);

        this.healingEffectListener = new HealingEffectListener();

        this.rand = new Random();

        //Register listeners used by plugin
        registerListeners();

        //Load Potion Effect Manager
        this.potionEffectManager = new PotionEffectManager(this);

        //Initialize Mod Manager 
        setModManager(new ModManager(this));

        //Set Up Permissions
        setupPermissions();

        //Set up Commands
        getCommand("toolhandler").setExecutor(new ReloadCommandExecutor(this));
        getCommand("modtool").setExecutor(new ModCommandExecutor(this));
        getCommand("refreshtoollore").setExecutor(new RefreshLoreCommandExecutor(this));

        //Schedule Tasks
        new ArmorPassiveTask(modManager).runTaskTimer(this, 0, 20);   //Armor Passives Task

        //Set instance
        ToolHandlerPlugin.instance = this;


    }

    private Boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private void registerListeners() {
        //Durability Based Prot/Sharpness/Efficiency Listeners
        Bukkit.getPluginManager().registerEvents(this.qualityListener, this);

        //Crafting Listeners
        Bukkit.getPluginManager().registerEvents(this.craftingListener, this);
        Bukkit.getPluginManager().registerEvents(this.craftingShiftClickListener, this);

        //Chainmail Durability Listener
        Bukkit.getPluginManager().registerEvents(this.chainmailListener, this);

        //Lore Listeners
        Bukkit.getPluginManager().registerEvents(this.armorLoreListener, this);
        Bukkit.getPluginManager().registerEvents(this.weapLoreListener, this);		

        //Modification Listeners
        Bukkit.getPluginManager().registerEvents(this.modListener, this);

        //GUI Listeners
        Bukkit.getPluginManager().registerEvents(this.artificierListener, this);
        Bukkit.getPluginManager().registerEvents(this.anvilListener, this);

        //Effect Listeners
        Bukkit.getPluginManager().registerEvents(this.healingEffectListener, this);

    }
    @Override
    public void onDisable() {
        //Prevent contents of item mod combiners from disappearing on server restart
        HashMap<Location, Inventory> modChests = artificierListener.getActiveArtificierTables();
        Iterator<Location> entryIterator = modChests.keySet().iterator();
        while(entryIterator.hasNext()) {
            Location loc = entryIterator.next();
            Inventory inv = modChests.get(loc);
            for(int i : ArtificierGUI.getInputSlots()) {
                if(inv.getItem(i) != null) {
                    loc.getWorld().dropItemNaturally(loc, inv.getItem(i));
                }
            }
        }
        //Prevent contents of anvils from disappearing on server restart
        HashMap<Location, Inventory> anvilChest = anvilListener.getActiveAnvilChests();
        Iterator<Location> locIterator = anvilChest.keySet().iterator();
        while(locIterator.hasNext()) {
            Location loc = locIterator.next();
            Inventory inv = modChests.get(loc);
            for(int i : AnvilGUI.getInputSlots()) {
                if(inv.getItem(i) != null) {
                    loc.getWorld().dropItemNaturally(loc, inv.getItem(i));
                }
            }
        }
    }

    /**
     * Gets the mod manager, which handles mod loading/storage
     * @return the Mod Manager
     */
    public ModManager getModManager() {
        return modManager;
    }

    /**
     * @param modManager the modManager to set
     */
    public void setModManager(ModManager modManager) {
        this.modManager = modManager;
    }

    /**
     * @return the Random Number Generator used by this plugin
     */
    public Random getRand() {
        return rand;
    }

    public PotionEffectManager getPotionEffectHandler() {
        return this.potionEffectManager;
    }


}
