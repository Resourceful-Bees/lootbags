package tech.thatgravyboat.lootbags.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.lootbags.Lootbags;
import net.fabricmc.api.ModInitializer;
import tech.thatgravyboat.lootbags.common.recipe.Loot;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.List;

public class LootbagsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Lootbags.init();

        FabricItemGroup.builder(new ResourceLocation(Lootbags.MOD_ID, "itemgroup"))
            .icon(() -> new ItemStack(McRegistry.LOOT_BAG.get()))
            .title(Component.translatable("itemGroup.lootbags.itemgroup"))
            .displayItems((features, output, op) -> {
                if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
                    getLoot().stream()
                        .map(Loot::createLootBag)
                        .forEach(output::accept);
                }
            })
            .build();
    }

    @Environment(EnvType.CLIENT)
    private static List<Loot> getLoot() {
        var level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        return level.getRecipeManager().getAllRecipesFor(McRegistry.LOOT_RECIPE.get());
    }
}