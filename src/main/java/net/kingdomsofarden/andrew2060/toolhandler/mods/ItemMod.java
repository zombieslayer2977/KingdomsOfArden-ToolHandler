package net.kingdomsofarden.andrew2060.toolhandler.mods;

import java.util.UUID;

public abstract class ItemMod {
    public final UUID modUUID;

    public ItemMod(UUID modUUID) {
        this.modUUID = modUUID;
    }

}
