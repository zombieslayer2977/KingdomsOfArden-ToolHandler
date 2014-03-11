package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedArmorInfo extends CachedItemInfo {
    private ToolHandlerPlugin plugin;
    private double quality;
    private String qualityFormat;
    private double magicResist;
    private double knockBackResist;
    private double protBonus;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;
    
    public CachedArmorInfo(ItemStack item, double quality, double magicResist, double knockBackResist, double protBonus) {
        this(item,quality,magicResist,knockBackResist,protBonus,new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedArmorInfo(ItemStack item, double quality, double magicResist, double knockBackResist, double protBonus, UUID[] mods) {
        super(ToolHandlerPlugin.instance,item);
        this.qualityFormat = FormattingUtil.getArmorQualityFormat(quality);
        this.quality = quality;
        this.magicResist = magicResist;
        this.knockBackResist = knockBackResist;
        this.protBonus = protBonus;
        this.mods = mods;
        this.dF = new DecimalFormat("##.##");
    }
    public double getQuality() {
        if(this.invalidated) {
            return this.plugin.getCacheManager().getCachedArmorInfo(this.item).getQuality();
        }
        return quality;
    }
    public ItemStack setQuality(double quality) {
        if(this.invalidated) {
            this.item = this.plugin.getCacheManager().getCachedArmorInfo(this.item).setQuality(quality);
            return this.item;
        }
        this.quality = quality;
        String newFormat = FormattingUtil.getArmorQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.item = this.forceWrite();
        } 
        return item;
    }
    public final ItemStack reduceQuality() { 
        if(this.invalidated) {
            this.item = this.plugin.getCacheManager().getCachedArmorInfo(this.item).reduceQuality();
            return this.item;
        }
        int unbreakinglevel = item.getEnchantmentLevel(Enchantment.DURABILITY)+1;
        switch(ImprovementUtil.getItemType(item)) {
        case DIAMOND: 
            quality -= 0.1/unbreakinglevel;
            break;
        case IRON_INGOT:
            quality -= 0.2/unbreakinglevel;
            break;
        case GOLD_INGOT:
            quality -= 1.0/unbreakinglevel;
            break;
        case LEATHER: 
            quality -= 0.8/unbreakinglevel;
            break;
        case STONE: 
            quality -= 0.5/unbreakinglevel;
            break;
        case BOW:
            quality -= 0.5/unbreakinglevel;
            break;
        default: 
            System.err.println("Material Sent to Reduce Quality is Invalid");
            System.err.println("-" + item.toString());
            return item;
        }
        if(quality < 0) {
            quality = 0;
        }
        String newFormat = FormattingUtil.getArmorQualityFormat(quality);
        if(newFormat.equals(this.qualityFormat)) {
            return this.forceWrite();
        } else {
            return item;
        }
    }
    public double getMagicResist() {
        if(this.invalidated) {
            return this.plugin.getCacheManager().getCachedArmorInfo(this.item).getMagicResist();
        }
        return magicResist;
    }

    public double getKBResistBonus() {
        if(this.invalidated) {
            return this.plugin.getCacheManager().getCachedArmorInfo(this.item).getKBResistBonus();
        }
        return knockBackResist;
    }

    public double getProtBonus() {
        if(this.invalidated) {
            return this.plugin.getCacheManager().getCachedArmorInfo(this.item).getProtBonus();
        }
        return protBonus;
    }

    public ItemStack getItem() {
        if(this.invalidated) {
            this.item = this.plugin.getCacheManager().getCachedArmorInfo(this.item).getItem();
        }
        return item;
    }

    public UUID[] getMods() {
        if(this.invalidated) {
            return this.plugin.getCacheManager().getCachedArmorInfo(this.item).getMods();
        }
        return mods;
    }

    
    /** 
     * Attempts to add a mod to the cached weapon object
     * @param mod Mod to add
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack), null if no space
     */
    public ItemStack addMod(ItemMod mod) {
        if(this.invalidated) {
            return this.plugin.getCacheManager().getCachedArmorInfo(this.item).addMod(mod);
        }
        if(!(mod instanceof ArmorMod)) {
            throw new IllegalArgumentException("This is not a armor mod!");
        }
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.baseId) || this.mods[i].equals(EmptyModSlot.bonusId)) {
                this.mods[i] = mod.modUUID;
                return this.forceWrite();
            }
        }
        return this.item;
    }
    
    
    /**
     * Adds a mod slot to the cached weapon object
     * @return the ItemStack with the inserted mod
     */
    public ItemStack addModSlot() {
        this.mods = Arrays.copyOf(this.mods, this.mods.length + 1);
        this.mods[this.mods.length] = EmptyModSlot.bonusId;
        return forceWrite();
    }
    
    public int getNumBonusSlots() {
        int bonusSlots = 0;
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.bonusId)) {
                bonusSlots++;
            }
        }
        return bonusSlots;
    }
    
    @Override
    public String toString() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedArmorInfo(this.item).toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dF.format(quality));
        sb.append(":");
        sb.append(dF.format(magicResist));
        sb.append(":");
        sb.append(dF.format(knockBackResist));
        sb.append(":");
        sb.append(protBonus);
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }
    public static CachedArmorInfo getDefault(ItemStack is) {
        return new CachedArmorInfo(is,0,0,0,0);
    }
    public static CachedArmorInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        double magicResist = Double.valueOf(parsed[1]);
        double healBonus = Double.valueOf(parsed[2]);
        double protBonus = Double.valueOf(parsed[3]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 4; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedArmorInfo(is,quality,magicResist,healBonus,protBonus,coll.toArray(new UUID[coll.size()]));
    }


}
