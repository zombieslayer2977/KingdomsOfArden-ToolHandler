package net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ItemMod;
import net.kingdomsofarden.andrew2060.toolhandler.potions.PotionEffectManager;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public abstract class ToolMod extends ItemMod {
	private String[] desc;
	private String name;
	private int weight;
	private boolean requiresSlot;
	private Double trueDamage;
	private Double bashChance;
	private Double decimateChance;
    private LinkedList<PotionEffect> applyTargetOnDamage;
    private LinkedList<PotionEffect> applySelfOnDamage;
    private LinkedList<PotionEffect> applySelfOnBlockBreak;
    private PotionEffectManager pEMan;
	
	public ToolMod(UUID modUUID, String name, int weight, boolean requiresSlot, String... desc) {
	    super(modUUID);
		this.name = name;
		this.desc = desc;
		this.weight = weight;
		this.requiresSlot = requiresSlot;
        this.pEMan = ToolHandlerPlugin.instance.getPotionEffectHandler();
        this.applyTargetOnDamage = new LinkedList<PotionEffect>();
        this.applySelfOnDamage = new LinkedList<PotionEffect>();
        this.applySelfOnBlockBreak = new LinkedList<PotionEffect>();
	}

	public String getName() {
		return name;
	}
	public String[] getDescription() {
		return desc;
	}
	public int getWeight() {
		return weight;
	}
	public boolean isSlotRequired() {
		return requiresSlot;
	}
	public abstract void executeOnBlockBreak(BlockBreakEvent event);
	public abstract void applyToTool(ItemStack tool);
	public abstract void executeOnWeaponDamage(WeaponDamageEvent event);
	
	public void addPotionEffectsTarget(PotionEffect effect) {
        this.applyTargetOnDamage.addFirst(effect);
    }
    public void addPotionEffectsTarget(Collection<PotionEffect> effects) {
        for(PotionEffect effect : effects) {
            addPotionEffectsTarget(effect);
        }
    }
    
    public void addPotionEffectsSelf(PotionEffect effect) {
        this.applySelfOnDamage.addFirst(effect);        
    }
    public void addPotionEffectsSelf(Collection<PotionEffect> effects) {
        for(PotionEffect effect : effects) {
            addPotionEffectsSelf(effect);
        }
    }
    
    public void addPotionEffectsBlockBreak(PotionEffect effect) {
        this.applySelfOnBlockBreak.addFirst(effect);        
    }
    public void addPotionEffectsBlockBreak(Collection<PotionEffect> effects) {
        for(PotionEffect effect : effects) {
            addPotionEffectsBlockBreak(effect);
        }
    }
    
    public Double getTrueDamage() {
        return trueDamage;
    }

    public void setTrueDamage(double trueDamage) {
        this.trueDamage = trueDamage;
    }

    public Double getBashChance() {
        return bashChance;
    }

    public void setBashChance(double bashChance) {
        this.bashChance = bashChance;
    }

    public Double getDecimateChance() {
        return decimateChance;
    }

    public void setDecimateChance(double decimateChance) {
        this.decimateChance = decimateChance;
    }

    public void tickModWeaponDamage(WeaponDamageEvent event) {
        executeOnWeaponDamage(event);
        pEMan.addPotionEffectStacking(applyTargetOnDamage, (LivingEntity) event.getEntity(), false);
        pEMan.addPotionEffectStacking(applySelfOnDamage, event.getDamager().getEntity(), false);
    }
    
    public void tickModBlockBreak(BlockBreakEvent event) {
        executeOnBlockBreak(event);
        pEMan.addPotionEffectStacking(applySelfOnBlockBreak, (LivingEntity) event.getPlayer(), false);
    }
    
}
