package net.swagserv.andrew2060.heroestools.mods;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

import net.swagserv.andrew2060.heroestools.Plugin;

import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.java.PluginClassLoader;

public class ModManager {
	private final PluginClassLoader classLoader;

	private final File weaponModDir;
	private final File armorModDir;
	private final File toolModDir;

	private final Map<String,File> weaponModFiles;
	private final Map<String,File> armorModFiles;
	private final Map<String,File> toolModFiles;

	//Map of mod name along with its associated mod data file
	private final Map<String,WeaponMod> weaponMods;
	private final Map<String,ArmorMod> armorMods;
	private final Map<String,ToolMod> toolMods;
	
	//Lists containing multiple instances of the mod depending on its given weight
	private final List<WeaponMod> weaponModList;
	private final List<ArmorMod> armorModList;
	private final List<ToolMod> toolModList;

	private Plugin plugin;
	@SuppressWarnings("deprecation")
	public ModManager(Plugin plugin) {
		this.plugin = plugin;
		
		this.weaponModFiles = new HashMap<String, File>();
		this.armorModFiles = new HashMap<String, File>();
		this.toolModFiles = new HashMap<String, File>();	

		this.weaponMods = new LinkedHashMap<String, WeaponMod>();
		this.armorMods = new LinkedHashMap<String, ArmorMod>();
		this.toolMods = new LinkedHashMap<String, ToolMod>();

		File modDir = new File(plugin.getDataFolder(),"Mods");
		modDir.mkdirs();
		this.weaponModDir = new File(modDir,"WeaponMods");
		this.weaponModDir.mkdirs();
		this.armorModDir = new File(modDir,"ArmorMods");
		this.armorModDir.mkdirs();
		this.toolModDir = new File(modDir,"ToolMods");
		this.toolModDir.mkdirs();
		
		this.weaponModList = new ArrayList<WeaponMod>();
		this.armorModList = new ArrayList<ArmorMod>();
		this.toolModList = new ArrayList<ToolMod>();

		
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
				String name = modFileName.toLowerCase().replace(".jar", "").replace("mod", "");
				if(weaponModFiles.containsKey(name)) {
					plugin.getLogger().log(Level.SEVERE, "A seperate weapon mod file with mod name " + name + " was already loaded!");
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
				String name = modFileName.toLowerCase().replace(".jar", "").replace("mod", "");
				if(armorModFiles.containsKey(name)) {
					plugin.getLogger().log(Level.SEVERE, "A seperate armor mod file with mod name " + name + " was already loaded!");
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
				String name = modFileName.toLowerCase().replace(".jar", "").replace("mod", "");
				if(toolModFiles.containsKey(name)) {
					plugin.getLogger().log(Level.SEVERE, "A seperate tool mod file with mod name " + name + " was already loaded!");
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
	private WeaponMod loadWeaponMod(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			String mainClass = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClass = reader.readLine().substring(12);
					break;
				}
			}
			if (mainClass != null) {
		        Class<?> weaponModClass = Class.forName(mainClass, true, this.classLoader);
		        Class<? extends WeaponMod> modClass = weaponModClass.asSubclass(WeaponMod.class);
		        Constructor<? extends WeaponMod> ctor = modClass.getConstructor(new Class[] { this.plugin.getClass() });
		        WeaponMod mod = (WeaponMod)ctor.newInstance();
		        jarFile.close(); 
		        return mod;
			}
			jarFile.close();
		} catch (Exception e) {
		      plugin.getLogger().log(Level.INFO, "The mod " + file.getName() + " failed to load.");
		      e.printStackTrace();
	    }
		return null;
	}
	private ArmorMod loadArmorMod(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			String mainClass = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClass = reader.readLine().substring(12);
					break;
				}
			}
			if (mainClass != null) {
		        Class<?> armorModClass = Class.forName(mainClass, true, this.classLoader);
		        Class<? extends ArmorMod> modClass = armorModClass.asSubclass(ArmorMod.class);
		        Constructor<? extends ArmorMod> ctor = modClass.getConstructor(new Class[] { this.plugin.getClass() });
		        ArmorMod mod = (ArmorMod)ctor.newInstance();
		        jarFile.close();
		        return mod;
			}
			jarFile.close();
		} catch (Exception e) {
		      plugin.getLogger().log(Level.INFO, "The mod " + file.getName() + " failed to load.");
		      e.printStackTrace();
	    }
		return null;
	}
	private ToolMod loadToolMod(File file) {
		try {
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			String mainClass = null;
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().equalsIgnoreCase("mod.info")) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
					mainClass = reader.readLine().substring(12);
					break;
				}
			}
			if (mainClass != null) {
		        Class<?> toolModClass = Class.forName(mainClass, true, this.classLoader);
		        Class<? extends ToolMod> modClass = toolModClass.asSubclass(ToolMod.class);
		        Constructor<? extends ToolMod> ctor = modClass.getConstructor(new Class[] { this.plugin.getClass() });
		        ToolMod mod = (ToolMod)ctor.newInstance();
		        jarFile.close();
		        return mod;
			}
			jarFile.close();
		} catch (Exception e) {
		      plugin.getLogger().log(Level.INFO, "The mod " + file.getName() + " failed to load.");
		      e.printStackTrace();
	    }
		return null;
	}
	@SuppressWarnings("rawtypes")
	public void loadWeaponMods() {
		for (Map.Entry entry : this.weaponModFiles.entrySet()) {
			if (!isWeaponLoaded((String)entry.getKey())) {
				WeaponMod weaponMod = loadWeaponMod((File)entry.getValue());
				if (weaponMod != null) {
					addWeaponMod(weaponMod);
					plugin.getLogger().log(Level.INFO, "Mod " + weaponMod.getName() + " Loaded");
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void loadArmorMods() {
		for (Map.Entry entry : this.armorModFiles.entrySet()) {
			if (!isArmorLoaded((String)entry.getKey())) {
				ArmorMod armorMod = loadArmorMod((File)entry.getValue());
				if (armorMod != null) {
					addArmorMod(armorMod);
					plugin.getLogger().log(Level.INFO, "Mod " + armorMod.getName() + " Loaded");
				}
			}
	    }
	}
	
	@SuppressWarnings("rawtypes")
	public void loadToolMods() {
		for (Map.Entry entry : this.armorModFiles.entrySet()) {
			if (!isToolLoaded((String)entry.getKey())) {
				ToolMod toolMod = loadToolMod((File)entry.getValue());
				if (toolMod != null) {
					addToolMod(toolMod);
					plugin.getLogger().log(Level.INFO, "Mod " + toolMod.getName() + " Loaded");
				}
			}
	    }
	}
	
	private void addToolMod(ToolMod mod) {
		this.toolMods.put(mod.getName().toLowerCase(), mod);	
		for(int i = 0; i < mod.getWeight(); i++) {
        	toolModList.add(mod);
        }
	} 
	private void addArmorMod(ArmorMod mod) {
		this.armorMods.put(mod.getName().toLowerCase(), mod);	
		for(int i = 0; i < mod.getWeight(); i++) {
        	armorModList.add(mod);
        }
	}
	private void addWeaponMod(WeaponMod mod) {
		this.weaponMods.put(mod.getName().toLowerCase(), mod);
        for(int i = 0; i < mod.getWeight(); i++) {
        	weaponModList.add(mod);
        }
	}
	private boolean isWeaponLoaded(String key) {
	    return this.weaponMods.containsKey(key.toLowerCase());
	}
	private boolean isArmorLoaded(String key) {
	    return this.armorMods.containsKey(key.toLowerCase());
	}
	private boolean isToolLoaded(String key) {
	    return this.toolMods.containsKey(key.toLowerCase());
	}
	public PluginClassLoader getClassLoader() {
		return classLoader;
	}
	public WeaponMod getRandomWeaponMod(int weight) {
		//Assume Legendary for now
		Random rand = new Random();
		int size = weaponModList.size();
		if(size > 0) {
			return weaponModList.get(rand.nextInt(size));
		} else {
			return null;
		}
	}
	public ArmorMod getRandomArmorMod(int weight) {
		//Assume Legendary for now
		Random rand = new Random();
		int size = armorModList.size();
		if(size > 0) {
			return armorModList.get(rand.nextInt(size));
		} else {
			return null;
		}	
	}
	public ToolMod getRandomToolMod(int weight) {
		//Assume Legendary for now
		Random rand = new Random();
		int size = toolModList.size();
		if(size > 0) {
			return toolModList.get(rand.nextInt(size));
		} else {
			return null;
		}	
	}
}
