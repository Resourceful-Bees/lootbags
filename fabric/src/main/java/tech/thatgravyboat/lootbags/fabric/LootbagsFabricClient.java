package tech.thatgravyboat.lootbags.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;
import tech.thatgravyboat.lootbags.client.LootbagsClient;

public class LootbagsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LootbagsClient.onRegisterProperties(ItemProperties::register);
    }
}
