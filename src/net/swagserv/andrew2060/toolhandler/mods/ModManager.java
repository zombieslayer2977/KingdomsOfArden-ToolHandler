package net.swagserv.andrew2060.toolhandler.mods;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import net.swagserv.andrew2060.toolhandler.ToolHandlerPlugin;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.ScytheMod;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.WeaponMod;
import net.swagserv.andrew2060.toolhandler.util.ModUtil;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.PluginClassLoader;

import com.herocraftonline.heroes.util.Util;
@SuppressWarnings("rawtypes")
public class ModManager {
	private final PluginClassLoader classLoader;

	private final File weaponModDir;
	private final File armorModDir;
	private final File toolModDir;
	private final File scytheModDir;

	private final Map<String,File> weaponModFiles;
	private final Map<String,File> armorModFiles;
	private final Map<String,File> toolModFiles;
	private final Map<String,File> scytheModFiles;

	//Map of mod name along with its associated mod data file
	private final Map<String,WeaponMod> weaponMods;
	private final Map<String,ArmorMod> armorMods;
	private final Map<String,ToolMod> toolMods;
	private final Map<String,ScytheMod> scytheMods;

	//Used to calculate random chances
	private int weaponModWeightTotal;
	private int armorModWeightTotal;
	private int toolModWeightTotal;
	private int scytheModWeightTotal;

	private ToolHandlerPlugin plugin;

	@SuppressWarnings("deprecation")
	public ModManager(ToolHandlerPlugin plugin) {
		this.plugin = plugin;

		this.weaponModFiles = new HashMap<String, File>();
		this.armorModFiles = new HashMap<String, File>();
		this.toolModFiles = new HashMap<String, File>();	
		this.scytheModFiles = new HashMap<String, File>();	

		this.weaponMods = new LinkedHashMap<String, WeaponMod>();
		this.armorMods = new LinkedHashMap<String, ArmorMod>();
		this.toolMods = new LinkedHashMap<String, ToolMod>();
		this.scytheMods = new LinkedHashMap<String, ScytheMod>();

		File modDir = new File(plugin.getDataFolder(),"Mods");
		modDir.mkdirs();
		this.weaponModDir = new File(modDir,"WeaponMods");
		this.weaponModDir.mkdirs();
		this.armorModDir = new File(modDir,"ArmorMods");
		this.armorModDir.mkdirs();
		this.toolModDir = new File(modDir,"ToolMods");
		this.toolModDir.mkdirs();
		this.scytheModDir = new File(modDir,"ScytheMods");
		this.scytheModDir.mkdirs();

		this.armorModWeightTotal = 0;
		this.toolModWeightTotal = 0;
		this.weaponModWeightTotal = 0;
		this.scytheModWeightTotal = 0;
		
		PluginClassLoader classLoader = (PluginClassLoader)plugin.getClass().getClassLoader();
		if (classLoader.getClass().getConstructors().length > 1) {
			this.classLoader = null;
			return;
		}
		this.classLoader = new PluginClassLoader((JavaPluginLoader)plugin.getPluginLoader(),classLoader.getURLs(),classLoader);

		loadWeaponModFiles();
		loadArmorModFiles();
		loadToolModFiles();

		loadArmorMods();
		loadToolMods();
		loadWeaponMods();
	}
	private void loadWeaponModFiles() {
		for(String modFileName : this.weaponModDir.list()) {
			if(modFileName.contains(".jar")) {
				File modFile = new File(weaponModDir, modFileName);
				String name = modFileName.toLowerCase().replace(".jar", "");
				if(weaponModFiles.containsKey(name)) {
					plugin.getLogger().log(Level.SEVERE, "A seperate weapon mod pack with pack name " + name + " was already loaded!");
				} else {
					this.weaponModFiles.put(name, modFile);
					try {
						classLoader.addURL(modFile.toURI().toURL());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void loadArmorModFiles() {
		for(String modFileName : this.armorModDir.list()) {
			if(modFileName.contains(".jar")) {
				File modFile = new File(armorModDir, modFileName);
				String name = modFileName.toLowerCase().replace(".jar", "");
				if(armorModFiles.containsKey(name)) {
					plugin.getLogger().log(Level.SEVERE, "A seperate armor mod pack with pack name " + name + " was already loaded!");
				} else {
					this.armorModFiles.put(name, modFile);
					try {
						classLoader.addURL(modFile.toURI().toURL());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void loadToolModFiles() {
		for(String modFileName : this.toolModDir.list()) {
			if(modFileName.contains(".jar")) {
				File modFile = new File(toolModDir, modFileName);
				String name = modFileName.toLowerCase().replace(".jar", "");
				if(toolModFiles.containsKey(name)) {
					plugin.getLogger().log(Level.SEVERE, "A seperate tool mod pack with pack name " + name + " was already loaded!");
				} else {
					this.toolModFiles.put(name, modFile);
					try {
						classLoader.addURL(modFile.toURI().toURL());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked" })
	private List<WeaponMod> loadWeaponMods(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			List<String> mainClasses = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClasses = new LinkedList<String>();
					String next = reader.readLine();
					while(next != null) {
						mainClasses.add(next);
						try {
							next = reader.readLine();
							continue;
						} catch(NullPointerException e) {
							break;
						}
					}
				}
			}
			if (mainClasses != null) {
				List<WeaponMod> mods = new LinkedList<WeaponMod>();
				Iterator<String> classIterator = mainClasses.iterator();
				while(classIterator.hasNext()) {
					Class weaponModClass = Class.forName(classIterator.next(), true, this.classLoader);
					Class modClass = weaponModClass.asSubclass(WeaponMod.class);
					Constructor ctor = modClass.getConstructor(new Class[] {});
					WeaponMod mod = (WeaponMod)ctor.newInstance(new Object[] {});
					mods.add(mod);
				}
				jarFile.close(); 
				return mods;
			}
			jarFile.close();
		} catch (Exception e) {
			plugin.getLogger().log(Level.INFO, "The mod pack " + file.getName() + " failed to load.");
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "unchecked" })
	private List<ArmorMod> loadArmorMods(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			List<String> mainClasses = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClasses = new LinkedList<String>();
					String next = reader.readLine();
					while(next != null) {
						mainClasses.add(next);
						try {
							next = reader.readLine();
							continue;
						} catch(NullPointerException e) {
							break;
						}
					}
				}
			}
			if (mainClasses != null) {
				List<ArmorMod> mods = new LinkedList<ArmorMod>();
				Iterator<String> classIterator = mainClasses.iterator();
				while(classIterator.hasNext()) {
					Class armorModClass = Class.forName(classIterator.next(), true, this.classLoader);
					Class modClass = armorModClass.asSubclass(ArmorMod.class);
					Constructor ctor = modClass.getConstructor(new Class[] {});
					ArmorMod mod = (ArmorMod)ctor.newInstance(new Object[] {});
					mods.add(mod);
				}
				jarFile.close(); 
				return mods;
			}
			jarFile.close();
		} catch (Exception e) {
			plugin.getLogger().log(Level.INFO, "The mod pack " + file.getName() + " failed to load.");
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "unchecked" })
	private List<ToolMod> loadToolMods(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			List<String> mainClasses = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClasses = new LinkedList<String>();
					String next = reader.readLine();
					while(next != null) {
						mainClasses.add(next);
						try {
							next = reader.readLine();
							continue;
						} catch(NullPointerException e) {
							break;
						}
					}
				}
			}
			if (mainClasses != null) {
				List<ToolMod> mods = new LinkedList<ToolMod>();
				Iterator<String> classIterator = mainClasses.iterator();
				while(classIterator.hasNext()) {
					Class toolModClass = Class.forName(classIterator.next(), true, this.classLoader);
					Class modClass = toolModClass.asSubclass(ToolMod.class);
					Constructor ctor = modClass.getConstructor(new Class[] {});
					ToolMod mod = (ToolMod)ctor.newInstance(new Object[] {});
					mods.add(mod);
				}
				jarFile.close(); 
				return mods;
			}
			jarFile.close();
		} catch (Exception e) {
			plugin.getLogger().log(Level.INFO, "The mod pack " + file.getName() + " failed to load.");
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "unchecked" })
	private List<ScytheMod> loadScytheMods(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			List<String> mainClasses = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClasses = new LinkedList<String>();
					String next = reader.readLine();
					while(next != null) {
						mainClasses.add(next);
						try {
							next = reader.readLine();
							continue;
						} catch(NullPointerException e) {
							break;
						}
					}
				}
			}
			if (mainClasses != null) {
				List<ScytheMod> mods = new LinkedList<ScytheMod>();
				Iterator<String> classIterator = mainClasses.iterator();
				while(classIterator.hasNext()) {
					Class toolModClass = Class.forName(classIterator.next(), true, this.classLoader);
					Class modClass = toolModClass.asSubclass(ScytheMod.class);
					Constructor ctor = modClass.getConstructor(new Class[] {});
					ScytheMod mod = (ScytheMod)ctor.newInstance(new Object[] {});
					mods.add(mod);
				}
				jarFile.close(); 
				return mods;
			}
			jarFile.close();
		} catch (Exception e) {
			plugin.getLogger().log(Level.INFO, "The mod pack " + file.getName() + " failed to load.");
			e.printStackTrace();
		}
		return null;
	}
	public void loadWeaponMods() {
		for (Map.Entry entry : this.weaponModFiles.entrySet()) {
			if (!isWeaponModLoaded((String)entry.getKey())) {
				Iterator<WeaponMod> loadModsFromFile = loadWeaponMods((File)entry.getValue()).iterator();
				while(loadModsFromFile.hasNext()) {
					WeaponMod weaponMod = loadModsFromFile.next();
					addWeaponMod(weaponMod);
					plugin.getLogger().log(Level.INFO, "Mod " + weaponMod.getName() + "(Weapon) Loaded");
				}
				
			}
		}
	}

	public void loadArmorMods() {
		for (Map.Entry entry : this.armorModFiles.entrySet()) {
			if (!isArmorModLoaded((String)entry.getKey())) {
				Iterator<ArmorMod> loadModsFromFile = loadArmorMods((File)entry.getValue()).iterator();
				while(loadModsFromFile.hasNext()) {
					ArmorMod armorMod = loadModsFromFile.next();
					addArmorMod(armorMod);
					plugin.getLogger().log(Level.INFO, "Mod " + armorMod.getName() + "(Armor) Loaded");
				}
			}
		}
	}

	public void loadToolMods() {
		for (Map.Entry entry : this.toolModFiles.entrySet()) {
			if (!isToolModLoaded((String)entry.getKey())) {
				Iterator<ToolMod> loadModsFromFile = loadToolMods((File)entry.getValue()).iterator();
				while(loadModsFromFile.hasNext()) {
					ToolMod toolMod = loadModsFromFile.next();
					addToolMod(toolMod);
					plugin.getLogger().log(Level.INFO, "Mod " + toolMod.getName() + "(Tool) Loaded");
				}
			}
		}
	}
	public void loadScytheMods() {
		for (Map.Entry entry : this.scytheModFiles.entrySet()) {
			if (!isScytheModLoaded((String)entry.getKey())) {
				Iterator<ScytheMod> loadModsFromFile = loadScytheMods((File)entry.getValue()).iterator();
				while(loadModsFromFile.hasNext()) {
					ScytheMod scytheMod = loadModsFromFile.next();
					addScytheMod(scytheMod);
					plugin.getLogger().log(Level.INFO, "Mod " + scytheMod.getName() + "(Tool) Loaded");
				}
			}
		}
	}
	private void addScytheMod(ScytheMod mod) {
		this.scytheMods.put(mod.getName().toLowerCase(), mod);	
		this.scytheModWeightTotal += mod.getWeight();
	}
	private void addToolMod(ToolMod mod) {
		this.toolMods.put(mod.getName().toLowerCase(), mod);	
		this.toolModWeightTotal +=mod.getWeight();

	} 
	private void addArmorMod(ArmorMod mod) {
		this.armorMods.put(mod.getName().toLowerCase(), mod);	
		this.armorModWeightTotal += mod.getWeight();
	}
	private void addWeaponMod(WeaponMod mod) {
		this.weaponMods.put(mod.getName().toLowerCase(), mod);
		this.weaponModWeightTotal += mod.getWeight();
	}
	private boolean isWeaponModLoaded(String key) {
		return this.weaponMods.containsKey(key.toLowerCase());
	}
	private boolean isArmorModLoaded(String key) {
		return this.armorMods.containsKey(key.toLowerCase());
	}
	private boolean isToolModLoaded(String key) {
		return this.toolMods.containsKey(key.toLowerCase());
	}
	private boolean isScytheModLoaded(String key) {
		return this.scytheMods.containsKey(key.toLowerCase());
	}
	public PluginClassLoader getClassLoader() {
		return classLoader;
	}
	public WeaponMod getRandomWeaponMod(int seed) {
		Collection<String> mods = weaponMods.keySet();
		Iterator<String> modIt = mods.iterator();
		if(mods.size() > 0) {
			int rand = plugin.getRand().nextInt(this.weaponModWeightTotal+1);
			WeaponMod mod = null;
			while(rand > 0 && modIt.hasNext()) {
				String next = modIt.next();
				mod = weaponMods.get(next);
				rand -= mod.getWeight();
			}
			if(mod.getWeight() >= 20) {
				return mod;
			} else {
				if(seed < 7) {
					return getRandomWeaponMod(seed + 1);
				} else {
					return mod;
				}
			}
		} else {
			return null;
		}
	}
	public ArmorMod getRandomArmorMod(int seed) {
		Collection<String> mods = armorMods.keySet();
		Iterator<String> modIt = mods.iterator();
		if(mods.size() > 0) {
			int rand = plugin.getRand().nextInt(this.armorModWeightTotal+1);
			ArmorMod mod = null;
			while(rand > 0 && modIt.hasNext()) {
				String next = modIt.next();
				mod = armorMods.get(next);
				rand -= mod.getWeight();
			}
			if(mod.getWeight() >= 20) {
				return mod;
			} else {
				if(seed < 7) {
					return getRandomArmorMod(seed + 1);
				} else {
					return mod;
				}
			}
		} else {
			return null;
		}	
	}
	public ToolMod getRandomToolMod(int seed) {
		Collection<String> mods = toolMods.keySet();
		Iterator<String> modIt = mods.iterator();
		if(mods.size() > 0) {
			int rand = plugin.getRand().nextInt(this.toolModWeightTotal+1);
			ToolMod mod = null;
			while(rand > 0 && modIt.hasNext()) {
				String next = modIt.next();
				mod = toolMods.get(next);
				rand -= mod.getWeight();
			}
			if(mod.getWeight() >= 20) {
				return mod;
			} else {
				if(seed < 7) {
					return getRandomToolMod(seed + 1);
				} else {
					return mod;
				}
			}
		} else {
			return null;
		}	
	}
	public ScytheMod getRandomScytheMod(int seed) {
		Collection<String> mods = scytheMods.keySet();
		Iterator<String> modIt = mods.iterator();
		if(mods.size() > 0) {
			int rand = plugin.getRand().nextInt(this.scytheModWeightTotal+1);
			ScytheMod mod = null;
			while(rand > 0 && modIt.hasNext()) {
				String next = modIt.next();
				mod = scytheMods.get(next);
				rand -= mod.getWeight();
			}
			if(mod.getWeight() >= 20) {
				return mod;
			} else {
				if(seed < 7) {
					return getRandomScytheMod(seed + 1);
				} else {
					return mod;
				}
			}
		} else {
			return null;
		}	
	}
	public WeaponMod getWeaponMod(String name) {
		name = name.toLowerCase();
		return weaponMods.get(name);
	}
	public ScytheMod getScytheMod(String name) {
		name = name.toLowerCase();
		return scytheMods.get(name);
	}
	public ArmorMod getArmorMod(String name) {
		name = name.toLowerCase();
		return armorMods.get(name);
	}
	public ToolMod getToolMod(String name) {
		name = name.toLowerCase();
		return toolMods.get(name); 
	}
	/**
	 * Determines whether itemstack is a weapon/tool/armor, and adds a mod to it
	 * 
	 * @param itemstack Itemstack to add mod to
	 * @return -2 if invalid size input, -1 if not a valid tool type, 0 for all mod slots full, 1 for normal operation
	 */
	public int addMod(ItemStack itemstack,int weight) {
		if(itemstack.getAmount() > 1) {
			return -2;
		} else {
			if(Util.isArmor(itemstack.getType())) {
				return ModUtil.addArmorMod(itemstack, getRandomArmorMod(weight));
			} else if(Util.isWeapon(itemstack.getType())) {
				switch(itemstack.getType()) {
					case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
						return ModUtil.addWeaponMod(itemstack, getRandomWeaponMod(weight));
					}
					case DIAMOND_PICKAXE: case DIAMOND_AXE: case DIAMOND_SPADE: 
					case IRON_PICKAXE: case IRON_AXE: case IRON_SPADE: 
					case GOLD_PICKAXE: case GOLD_AXE: case GOLD_SPADE: 
				    case STONE_PICKAXE: case STONE_AXE: case STONE_SPADE: 
					case WOOD_PICKAXE: case WOOD_AXE: case WOOD_SPADE: { 
						return ModUtil.addToolMod(itemstack, getRandomToolMod(weight));
					}
					case DIAMOND_HOE: case IRON_HOE: case GOLD_HOE: case STONE_HOE: case WOOD_HOE:
						return ModUtil.addScytheMod(itemstack, getRandomScytheMod(weight));
					default: {
						return -1;
					}
				}
			} else {
				return -1;
			}
		}
	}
	
}
