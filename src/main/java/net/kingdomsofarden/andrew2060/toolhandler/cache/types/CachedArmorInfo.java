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
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CachedArmorInfo extends CachedItemInfo {
    private double quality;
    private String qualityFormat;
    private double magicResist;
    private double knockBackResist;
    private double protBonus;
    private ItemStack item;
    private UUID[] mods;
    private DecimalFormat dF;
    
    public CachedArmorInfo(ItemStack item, double quality) {
        this(item,quality, new UUID[] {EmptyModSlot.baseId, EmptyModSlot.baseId});
    }
    public CachedArmorInfo(ItemStack item, double quality, UUID[] mods) {
        super(ToolHandlerPlugin.instance,item);
        this.qualityFormat = FormattingUtil.getArmorQualityFormat(quality);
        this.quality = quality;
        this.magicResist = 0.00;
        this.knockBackResist = 0.00;
        this.protBonus = 0.00;
        this.mods = mods;
        for(UUID id : mods) {
            ArmorMod mod = plugin.getModManager().getArmorMod(id);
            if(mod != null) {
                magicResist += mod.getMagicResist();
                knockBackResist += mod.getKnockbackResist();
                protBonus += mod.getProtBonus();
            }
        }
        switch(item.getType()) { //All chainmail has +10% base magic resist
        
        case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: {
            magicResist += 10;
            break;
        }
        default: {
            break;
        }
        
        }
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
            this.qualityFormat = newFormat;
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
            this.qualityFormat = newFormat;
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
                break;
            }
        }
        this.magicResist = 0.00;
        this.knockBackResist = 0.00;
        this.protBonus = 0.00;
        for(UUID id : mods) {
            ArmorMod foundMod = plugin.getModManager().getArmorMod(id);
            if(foundMod != null) {
                magicResist += foundMod.getMagicResist();
                knockBackResist += foundMod.getKnockbackResist();
                protBonus += foundMod.getProtBonus();
            }
        }
        switch(item.getType()) { //All chainmail has +10% base magic resist
        
        case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: {
            magicResist += 10;
            break;
        }
        default: {
            break;
        }
        
        }
        return this.forceWrite();
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
        for(UUID id : mods) {
            sb.append(":");
            sb.append(id.toString());
        }
        return sb.toString();
    }
    public static CachedArmorInfo getDefault(ItemStack is) {
        return new CachedArmorInfo(is,0.00);
    }
    public static CachedArmorInfo fromString(ItemStack is, String parseable) {
        String[] parsed = parseable.split(":");
        double quality = Double.valueOf(parsed[0]);
        Set<UUID> coll = new HashSet<UUID>();
        for(int i = 1; i < parsed.length; i++) {
            coll.add(UUID.fromString(parsed[i]));
        }
        return new CachedArmorInfo(is,quality,coll.toArray(new UUID[coll.size()]));
    }

    public ItemStack forceWrite() {
        if(this.invalidated) {
            this.item = plugin.getCacheManager().getCachedInfo(this.item).forceWrite();
            return this.item;
        }
        ItemStack write = NbtUtil.writeKnockbackResist(this.item, knockBackResist);
        if(write != this.item) {
            this.invalidated = true;
            this.item = write;
        }
        return super.forceWrite(); 
    }

}
