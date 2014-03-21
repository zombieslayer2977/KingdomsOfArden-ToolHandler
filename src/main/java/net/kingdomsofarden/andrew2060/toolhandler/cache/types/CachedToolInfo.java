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

    private double quality;
    private String qualityFormat;
    private double trueDamage;
    private double bashChance;
    private double decimateChance;
    private UUID[] mods;
    private DecimalFormat dF;    


    public CachedToolInfo(ItemStack item, double quality) {
        this(item, quality, new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedToolInfo(ItemStack item, double quality, UUID[] mods) {
        super(ToolHandlerPlugin.instance,item);
        this.qualityFormat = FormattingUtil.getWeaponToolQualityFormat(quality);
        this.quality = quality;
        this.trueDamage = 0.00;
        this.bashChance = 0.00;
        this.decimateChance = 0.00;
        this.mods = mods;
        for(UUID id : this.mods) {
            if(id == null) {
                continue;
            }
            ToolMod mod = plugin.getModManager().getToolMod(id);
            if(mod != null) {
                trueDamage += mod.getTrueDamage();
                bashChance += mod.getBashChance();
                decimateChance += mod.getDecimateChance();
            }
        }
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
            this.qualityFormat = newFormat;
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
        case WOOD:
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
            this.qualityFormat = newFormat;
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
        this.mods[this.mods.length-1] = EmptyModSlot.bonusId;
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
        boolean replaced = false;
        for(int i = 0; i < this.mods.length; i++) {
            if(this.mods[i].equals(EmptyModSlot.baseId) || this.mods[i].equals(EmptyModSlot.bonusId)) {
                this.mods[i] = mod.modUUID;
                replaced = true;
                break;
            }
        }
        if(!replaced) {
            return null;
        }
        this.trueDamage = 0.00;
        this.bashChance = 0.00;
        this.decimateChance = 0.00;
        for(UUID id : this.mods) {
            ToolMod foundMod = plugin.getModManager().getToolMod(id);
            if(foundMod != null) {
                trueDamage += foundMod.getTrueDamage();
                bashChance += foundMod.getBashChance();
                decimateChance += foundMod.getDecimateChance();
            }
        }
        return this.forceWrite();
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
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 1; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedToolInfo(is,quality,coll.toArray(new UUID[coll.size()]));
    }
    public static CachedToolInfo getDefault(ItemStack is) {
        return new CachedToolInfo(is,0);
    }

}
