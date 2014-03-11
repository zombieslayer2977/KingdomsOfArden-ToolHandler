package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedToolInfo extends CachedItemInfo {

    private ToolHandlerPlugin plugin;
    private double quality;
    private String qualityFormat;
    private double trueDamage;
    private double bashChance;
    private double decimateChance;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;    


    public CachedToolInfo(ItemStack item, double quality, double trueDamage, double bashChance, double decimatingStrikeChance) {
        this(item,quality,trueDamage,bashChance,decimatingStrikeChance,new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedToolInfo(ItemStack item, double quality, double trueDamage, double bashChance, double decimatingStrikeChance, UUID[] mods) {
        super(ToolHandlerPlugin.instance,item);
        this.qualityFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        this.quality = quality;
        this.trueDamage = trueDamage;
        this.bashChance = bashChance;
        this.decimateChance = decimatingStrikeChance;
        this.mods = mods;
        this.dF = new DecimalFormat("##.##");
    }

    @Override
    public double getQuality() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getQuality();
        }
        return quality;
    }

    @Override
    public ItemStack setQuality(double quality) {
        if(this.invalidated){
            this.item = plugin.getCacheManager().getCachedToolInfo(this.item).setQuality(quality);
            return this.item;
        }
        this.quality = quality;
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.forceWrite();
        }
        return this.item;
    }

    @Override
    public ItemStack reduceQuality() {
        if(this.invalidated) { 
            this.item = plugin.getCacheManager().getCachedToolInfo(this.item).reduceQuality();
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
        String newFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.forceWrite();
        }
        return item;
    }

    @Override
    public String toString() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dF.format(quality));
        sb.append(":");
        sb.append(dF.format(trueDamage));
        sb.append(":");
        sb.append(dF.format(bashChance));
        sb.append(":");
        sb.append(decimateChance);
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }

    @Override
    public int getNumBonusSlots() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getNumBonusSlots();
        }
        int bonusSlots = 0;
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.bonusId)) {
                bonusSlots++;
            }
        }
        return bonusSlots;
    }

    @Override
    public ItemStack addModSlot() {
        if(this.invalidated) {
            this.item = plugin.getCacheManager().getCachedToolInfo(this.item).addModSlot();
            return this.item;
        }
        this.mods = Arrays.copyOf(this.mods, this.mods.length + 1);
        this.mods[this.mods.length] = EmptyModSlot.bonusId;
        return forceWrite();
    }

    @Override
    public ItemStack addMod(ItemMod mod) {
        if(this.invalidated) {
            this.item = plugin.getCacheManager().getCachedToolInfo(this.item).addMod(mod);
            return this.item;
        }
        if(!(mod instanceof ToolMod)) {
            throw new IllegalArgumentException("This is not a tool mod!");
        }
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.baseId) || this.mods[i].equals(EmptyModSlot.bonusId)) {
                this.mods[i] = mod.modUUID;
                this.forceWrite();
            }
        }
        return item;
    }
    public UUID[] getMods() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getMods();
        }
        return this.mods;
    }
    public double getTrueDamage() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getTrueDamage();
        }
        return trueDamage;
    }

    public double getBashChance() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getBashChance();
        }
        return bashChance;
    }

    public double getDecimatingStrikeChance() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getDecimatingStrikeChance();
        }
        return decimateChance;
    }

    public static CachedToolInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        double trueDamage = Double.valueOf(parsed[1]);
        double bashChance = Double.valueOf(parsed[2]);
        double decimateChance = Double.valueOf(parsed[3]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 4; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedToolInfo(is,quality,trueDamage,bashChance,decimateChance,coll.toArray(new UUID[coll.size()]));
    }
    public static CachedToolInfo getDefault(ItemStack is) {
        return new CachedToolInfo(is,0,0,0,0);
    }

}
