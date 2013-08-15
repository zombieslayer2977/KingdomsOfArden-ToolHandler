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
    public FireTickEffect(long duration, CharacterTemplate fireApplier) {
        super((Skill)null, ToolHandlerPlugin.instance.heroesPlugin, "FireTickEffect", 10, duration);
        this.fireTickDamage = plugin.getDamageManager().getEnvironmentalDamage(DamageCause.FIRE_TICK);
        this.applier = fireApplier;
    }
    @Override
    public void applyToHero(Hero h) {
        h.getEntity().setFireTicks(0);
    }
    @Override
    public void tickMonster(Monster monster) {
        CharacterDamageEvent cEvent = new CharacterDamageEvent(monster.getEntity(), DamageCause.FIRE_TICK, fireTickDamage);
        Bukkit.getPluginManager().callEvent(cEvent);
        if(!cEvent.isCancelled()) {
            if(!Skill.damageEntity(monster.getEntity(), applier.getEntity(), cEvent.getDamage(), DamageCause.FIRE_TICK)) {
                monster.getEntity().damage(cEvent.getDamage());
            }
            monster.getEntity().setNoDamageTicks(0);
        } 
    }

    @Override
    public void tickHero(Hero hero) {
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
