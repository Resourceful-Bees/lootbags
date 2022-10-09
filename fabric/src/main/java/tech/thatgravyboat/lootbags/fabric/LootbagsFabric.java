package tech.thatgravyboat.lootbags.fabric;

import tech.thatgravyboat.lootbags.Lootbags;
import net.fabricmc.api.ModInitializer;

public class LootbagsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Lootbags.init();
    }
}