package net.kingdomsofarden.andrew2060.toolhandler.effects;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.herocraftonline.heroes.api.events.CharacterDamageEvent;
import com.herocraftonline.heroes.characters.CharacterTemplate;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.Monster;
import com.herocraftonline.heroes.characters.effects.PeriodicExpirableEffect;
import com.herocraftonline.heroes.characters.skill.Skill;

public class FireTickEffect extends PeriodicExpirableEffect {

    private double fireTickDamage;
    private CharacterTemplate applier;
    private int specialDamageSourceAppliers;
    private int ticksBeforeNextDamage;
    public FireTickEffect(long duration, CharacterTemplate fireApplier, Integer specialDamageSourceAppliers) {
        super((Skill)null, ToolHandlerPlugin.instance.heroesPlugin, "FireTickEffect", 1, duration);
        this.fireTickDamage = plugin.getDamageManager().getEnvironmentalDamage(DamageCause.FIRE_TICK);
        this.applier = fireApplier;
        if(fireApplier == null) {
            this.specialDamageSourceAppliers = specialDamageSourceAppliers;
        }
        this.ticksBeforeNextDamage = 0;
    }
    @Override
    public void applyToHero(Hero h) {
        h.getEntity().setFireTicks(0);
    }
    @Override
    public void tickMonster(Monster monster) {
        this.ticksBeforeNextDamage++;
        if(this.ticksBeforeNextDamage == 10) {
            this.ticksBeforeNextDamage = 0;
            CharacterDamageEvent cEvent = new CharacterDamageEvent(monster.getEntity(), DamageCause.FIRE_TICK, fireTickDamage);
            Bukkit.getPluginManager().callEvent(cEvent);
            if(!cEvent.isCancelled()) {
                if(!Skill.damageEntity(monster.getEntity(), applier.getEntity(), cEvent.getDamage(), DamageCause.FIRE_TICK)) {
                    monster.getEntity().damage(cEvent.getDamage());
                }
                monster.getEntity().setNoDamageTicks(0);
            } 
        }
        
    }

    @Override
    public void tickHero(Hero hero) {
        this.ticksBeforeNextDamage++;
        if(this.ticksBeforeNextDamage == 10) {
            this.ticksBeforeNextDamage = 0;
            CharacterDamageEvent cEvent = new CharacterDamageEvent(hero.getEntity(), DamageCause.FIRE_TICK, fireTickDamage);
            Bukkit.getPluginManager().callEvent(cEvent);
            if(!cEvent.isCancelled()) {
                if(!Skill.damageEntity(hero.getEntity(), applier.getEntity(), cEvent.getDamage(), DamageCause.FIRE_TICK)) {
                    hero.getEntity().damage(cEvent.getDamage());
                }
                hero.getEntity().setNoDamageTicks(0);
            } 
        }
    }

}
