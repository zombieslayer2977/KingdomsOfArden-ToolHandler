package net.kingdomsofarden.andrew2060.toolhandler.cache.types;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ScytheMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.FormattingUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedScytheInfo extends CachedItemInfo {

    private double quality;
    private String qualityFormat;
    private double damageBoost;
    private double manaRestoration;
    private double spellLeech;
    private UUID[] mods;
    private DecimalFormat dF;

    public CachedScytheInfo(ItemStack item, double quality, UUID[] mods) {
        super(ToolHandlerPlugin.instance, item);
        this.qualityFormat = FormattingUtil.getScytheQualityFormat(quality);
        this.quality = quality;
        this.damageBoost = 0.00;
        this.manaRestoration = 0.00;
        this.spellLeech = 0.00;
        this.mods = mods;
        for(UUID id : mods) {
            if(id == null) {
                continue;
            }
            ScytheMod mod = plugin.getModManager().getScytheMod(id);
            if(mod != null) {
                damageBoost += mod.getDamageBoost();
                manaRestoration += mod.getManaRestoration();
                spellLeech += mod.getSpellLeech();
            }
        }

        this.dF = new DecimalFormat("##.##");    
    }

    public CachedScytheInfo(ItemStack item, double quality) {
        this(item, quality, new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }

    @Override
    public double getQuality() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedScytheInfo(this.item).getQuality();
        }
        return quality;
    }

    @Override
    public ItemStack setQuality(double quality) {
        if(this.invalidated){
            this.item = plugin.getCacheManager().getCachedScytheInfo(this.item).setQuality(quality);
            return this.item;
        }
        this.quality = quality;
        String newFormat = FormattingUtil.getScytheQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.qualityFormat = newFormat;
            this.forceWrite();
        }
        return this.item;
    }

    @Override
    public ItemStack reduceQuality() {
        if(this.invalidated) { 
            this.item = plugin.getCacheManager().getCachedScytheInfo(this.item).reduceQuality();
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
        String newFormat = FormattingUtil.getScytheQualityFormat(quality);
        if(!newFormat.equalsIgnoreCase(qualityFormat)) {
            this.qualityFormat = newFormat;
            this.forceWrite();
        }
        return item;
    }

    @Override
    public int getNumBonusSlots() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedScytheInfo(this.item).getNumBonusSlots();
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
            this.item = plugin.getCacheManager().getCachedScytheInfo(this.item).addModSlot();
            return this.item;
        }
        this.mods = Arrays.copyOf(this.mods, this.mods.length + 1);
        this.mods[this.mods.length-1] = EmptyModSlot.bonusId;
        return forceWrite();
    }

    @Override
    public ItemStack addMod(ItemMod mod) {
        if(this.invalidated) {
            this.item = plugin.getCacheManager().getCachedScytheInfo(this.item).addMod(mod);
            return this.item;
        }
        if(!(mod instanceof ScytheMod)) {
            throw new IllegalArgumentException("This is not a Scythe mod!");
        }
        boolean replaced = false;
        for(int i = 0; i < this.mods.length && !replaced; i++) {
            if(this.mods[i].equals(EmptyModSlot.baseId) || this.mods[i].equals(EmptyModSlot.bonusId)) {
                this.mods[i] = mod.modUUID;
                replaced = true;
            }
        }
        if(!replaced) {
            return null;
        }
        this.damageBoost = 0.00;
        this.manaRestoration = 0.00;
        this.spellLeech = 0.00;
        for(UUID id : mods) {
            ScytheMod foundMod = plugin.getModManager().getScytheMod(id);
            if(foundMod != null) {
                damageBoost += foundMod.getDamageBoost();
                manaRestoration += foundMod.getManaRestoration();
                spellLeech += foundMod.getSpellLeech();
            }
        }
        return this.forceWrite();
    }

    @Override
    public String toString() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedScytheInfo(this.item).toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dF.format(quality));
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }

    public static CachedScytheInfo getDefault(ItemStack is) {
        return new CachedScytheInfo(is,0);
    }

    public static CachedScytheInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 1; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedScytheInfo(is,quality,coll.toArray(new UUID[coll.size()]));
    }

    public UUID[] getMods() {
        if(this.invalidated) {
            return plugin.getCacheManager().getCachedToolInfo(this.item).getMods();
        }
        return this.mods;
    }

    public double getDamageBoost() {
        return damageBoost;
    }

    public void setDamageBoost(double damageBoost) {
        this.damageBoost = damageBoost;
    }

    public double getManaRestoration() {
        return manaRestoration;
    }

    public void setManaRestoration(double manaRestoration) {
        this.manaRestoration = manaRestoration;
    }

    public double getSpellLeech() {
        return spellLeech;
    }

    public void setSpellLeech(double spellLeech) {
        this.spellLeech = spellLeech;
    }


}
