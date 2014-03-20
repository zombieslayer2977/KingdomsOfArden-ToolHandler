package net.kingdomsofarden.andrew2060.toolhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.cache.CacheManager;
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
import net.kingdomsofarden.andrew2060.toolhandler.listeners.fire.FireEffectListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.gui.AnvilListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.gui.ArtificierListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.mechanics.DamageTickResetListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.mods.ArmorModListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.mods.ModTickListener;
import net.kingdomsofarden.andrew2060.toolhandler.listeners.mods.WeaponModListener;
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

    private ArmorModListener armorLoreListener;
    private WeaponModListener weapLoreListener;

    private ModTickListener modListener;

    private ArtificierListener artificierListener;
    private AnvilListener anvilListener;

    private HealingEffectListener healingEffectListener;
    private FireEffectListener fireEffectListener;

    private DamageTickResetListener noDamageTickListener;
    
    private PotionEffectManager potionEffectManager;
    
    private Random rand;

    private CacheManager cacheManager;




    //Deprecated: Gets the 4 character version identifier associated with this version of tool lore to determine if an update is needed.
    public static String versionIdentifier = ChatColor.AQUA + "" + ChatColor.BLUE + "" + ChatColor.RESET + "";
    //Plugin identifier for nbt storage (change whenever an update is needed)
    public static final UUID identifier = UUID.fromString("4871b300-a1cf-11e3-a5e2-0800200c9a66");
    public static final UUID version = UUID.fromString("d08df4f0-b085-11e3-a5e2-0800200c9a66");


    public void onEnable() {
        

        //Set instance
        ToolHandlerPlugin.instance = this;
        
        //Load the Heroes plugin instance
        this.heroesPlugin = (Heroes)Bukkit.getPluginManager().getPlugin("Heroes");
        //Initialize Listeners
        this.qualityListener = new DurabilityChangeListener(this);

        this.craftingListener = new CraftingListener(this);
        this.craftingShiftClickListener = new ShiftClickListener(this);

        this.chainmailListener = new ChainMailListener();

        this.armorLoreListener = new ArmorModListener(this);
        this.weapLoreListener = new WeaponModListener(this);

        this.modListener = new ModTickListener(this);

        this.artificierListener = new ArtificierListener(this);
        this.anvilListener = new AnvilListener(this);

        this.healingEffectListener = new HealingEffectListener();
        this.fireEffectListener = new FireEffectListener(heroesPlugin);
        
        this.noDamageTickListener = new DamageTickResetListener();
        
        this.rand = new Random();

        //Register listeners used by plugin
        registerListeners();

        //Load Potion Effect Manager
        this.potionEffectManager = new PotionEffectManager(this);

        //Initialize Mod Manager 
        setModManager(new ModManager(this));

        //Set Up Permissions
        setupPermissions();
        
        //Set up caching
        setCacheManager(new CacheManager());
        
        //Set up Commands
        getCommand("toolhandler").setExecutor(new ReloadCommandExecutor(this));
        getCommand("modtool").setExecutor(new ModCommandExecutor(this));
        getCommand("refreshtoollore").setExecutor(new RefreshLoreCommandExecutor(this));

        //Schedule Tasks
        new ArmorPassiveTask(this).runTaskTimer(this, 0, 20);   //Armor Passives Task

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
        Bukkit.getPluginManager().registerEvents(this.fireEffectListener, this);

        //Mechanics Listeners
        Bukkit.getPluginManager().registerEvents(this.noDamageTickListener, this);
        
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
    
    public CacheManager getCacheManager() {
        return this.cacheManager;
    }
    
    private void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
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
