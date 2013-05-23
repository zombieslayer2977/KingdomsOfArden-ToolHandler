package net.swagserv.andrew2060.heroestools.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;

public class CriticalStrikeEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private int dmg;
	private WeaponDamageEvent event;
	private Player player;
	/**
	 * This event is to be thrown whenever a critical strike is made. 
	 * 
	 * @param damage   Represents the damage dealt post-critical strike
	 * @param event    WeaponDamageEvent associated with the critical strike
	 * @param attacker The attacker that dealt the critical strike
	 */
	public CriticalStrikeEvent(int damage, WeaponDamageEvent event, Player attacker) {
		this.dmg = damage;
		this.event = event;
		this.player = attacker;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	/**
	 * Calls the event (included mainly for simplicity purposes)
	 */
	public void callEvent(){
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
	/**
	 * Get the damage post-critical strike
	 * @return     Post-Crit damage
	 */
	public int getDamage() {
		return dmg;
	}
	/**
	 * Get the WeaponDamageEvent associated with this critical strike
	 * @return     The associated WeaponDamageEvent
	 */
	public WeaponDamageEvent getEvent() {
		return event;
	}
	/**
	 * Get the attacker associated with this critical strike
	 * @return     The associated attacking Player
	 */
	public Player getAttacker() {
		return player;
	}
	/**
	 * Set event damage
	 * 
	 * @param damage  Damage to set 
	 */
	public void setDamage(int damage) {
		this.dmg = damage;
	}
}
