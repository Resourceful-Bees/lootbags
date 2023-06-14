package tech.thatgravyboat.lootbags;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import net.minecraft.resources.ResourceLocation;
import tech.thatgravyboat.lootbags.client.LootbagsClient;
import tech.thatgravyboat.lootbags.common.network.NetworkHandlers;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.stream.Stream;

public class Lootbags {
    public static final String MOD_ID = "lootbags";
    public static boolean isClient = false;

    public static void init() {
        McRegistry.ITEMS.init();
        McRegistry.RECIPE_TYPES.init();
        McRegistry.RECIPE_SERIALIZERS.init();
        NetworkHandlers.register();

        new ResourcefulCreativeTab(new ResourceLocation(Lootbags.MOD_ID, "itemgroup"))
                .setItemIcon(McRegistry.LOOT_BAG)
                .addContent(() -> isClient ? LootbagsClient.getLootbagItems() : Stream.of())
                .build();
    }
}