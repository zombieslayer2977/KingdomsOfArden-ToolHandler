package net.swagserv.andrew2060.heroestools.mods;

public enum WeaponModBackup {
	ACCURATE(null),
	ACE(null),
	ARES(null),
	BESERK(null),
	BRUTE(null),
	BANDIT(null),
	BOLT(null),
	BURNING(null),
	CATALYST(null),
	CELEST(null),
	CHASM(null),
	CHILLY(null),
	CLUSTER(null),
	DEVIL(null),
	DEADLY(null),
	DRAINING(null),
	ETHER(null),
	EVIL(null),
	GOLIATH(null),
	GENIUS(null),
	HELL(null),
	HERA(null),
	ICY(null),
	JUSTICE(null),
	LEGEND(null),
	LUNAR(null),
	LUNATIC(null),
	MONO(null),
	NEITH(null),
	OMEGA(null),
	POISON(null),
	PRECISE(null),
	QUAKE(null),
	RUNIC(null),
	SACRED(null),
	SAVAGE(null),
	SILENCE(null),
	SPRINT(null),
	STORM(null),
	TOXIC(null),
	UNIVERSAL(null),
	VALKYRIE(null),
	VISION(null),
	WARRIOR(null),
	WITHERING(null),
	WIZARD(null),
	ZEUS(null);
	private String name;
	private WeaponMod data;
	WeaponModBackup(WeaponMod data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public WeaponMod getModData() {
		return data;
	}
}
