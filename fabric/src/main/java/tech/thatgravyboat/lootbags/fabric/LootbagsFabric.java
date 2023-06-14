package tech.thatgravyboat.lootbags.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import tech.thatgravyboat.lootbags.Lootbags;

public class LootbagsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Lootbags.isClient = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
        Lootbags.init();
    }
}