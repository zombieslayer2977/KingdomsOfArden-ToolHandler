package net.kingdomsofarden.andrew2060.toolhandler.effects;

import java.lang.reflect.Field;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.herocraftonline.heroes.characters.CharacterTemplate;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.Monster;
import com.herocraftonline.heroes.characters.effects.PeriodicExpirableEffect;
import com.herocraftonline.heroes.characters.skill.Skill;

public class FireTickEffect extends PeriodicExpirableEffect {

    private double fireTickDamage;
    private CharacterTemplate applier;
    private int ticksBeforeNextDamage;
    public FireTickEffect(long initialDuration, CharacterTemplate fireApplier) {
        super((Skill)null, ToolHandlerPlugin.instance.heroesPlugin, "FireTickEffect", 50, initialDuration);
        this.fireTickDamage = plugin.getDamageManager().getEnvironmentalDamage(DamageCause.FIRE_TICK);
        this.applier = fireApplier;
        this.ticksBeforeNextDamage = 0;
    }

    @Override
    public void tickMonster(Monster monster) {
        int fireTicks = monster.getEntity().getFireTicks();
        if(fireTicks <= 0) {
            monster.removeEffect(this);
            return;
        } else {
            long remainingFireTickDuration = Math.round(fireTicks*1000*0.05);
            if(getRemainingTime() < remainingFireTickDuration) {
                try {
                    Field f = this.getClass().getDeclaredField("expireTime");
                    f.setAccessible(true);
                    f.setLong(this, System.currentTimeMillis() + remainingFireTickDuration);
                } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        this.ticksBeforeNextDamage++;
        if(this.ticksBeforeNextDamage == 10) {
            this.ticksBeforeNextDamage = 0;
            if(applier != null) {
                Skill.damageEntity(monster.getEntity(), applier.getEntity(), fireTickDamage, DamageCause.FIRE_TICK);
            } else {
                monster.getEntity().damage(fireTickDamage);
                monster.getEntity().setLastDamageCause(new EntityDamageEvent(monster.getEntity(), DamageCause.FIRE_TICK, fireTickDamage));
            }
            monster.getEntity().setNoDamageTicks(0);

        }

    }

    @Override
    public void tickHero(Hero hero) {
        int fireTicks = hero.getEntity().getFireTicks();
        if(fireTicks <= 0) {
            hero.removeEffect(this);
            return;
        } else {
            long remainingFireTickDuration = Math.round(fireTicks*1000*0.05);
            if(getRemainingTime() < remainingFireTickDuration) {
                try {
                    Field f = this.getClass().getDeclaredField("expireTime");
                    f.setAccessible(true);
                    f.setLong(this, System.currentTimeMillis() + remainingFireTickDuration);
                } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        this.ticksBeforeNextDamage++;
        if(this.ticksBeforeNextDamage == 10) {
            this.ticksBeforeNextDamage = 0;
            if(applier != null) {
                if(applier instanceof Hero && !Skill.damageCheck((Player) applier.getEntity(), hero.getEntity())) {
                    hero.getEntity().damage(fireTickDamage);
                } else {
                    Skill.damageEntity(hero.getEntity(), applier.getEntity(), fireTickDamage, DamageCause.FIRE_TICK);
                }
            } else {
                hero.getPlayer().damage(fireTickDamage);
                hero.getPlayer().setLastDamageCause(new EntityDamageEvent(hero.getPlayer(), DamageCause.FIRE_TICK, fireTickDamage));
            }
            hero.getEntity().setNoDamageTicks(0);

        }
    }

}
