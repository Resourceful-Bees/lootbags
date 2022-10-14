package tech.thatgravyboat.lootbags.common.registry.fabric;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.common.recipe.Loot;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.List;
import java.util.function.Supplier;

public class McRegistryImpl {
    public static <V, T extends V> Supplier<T> register(Registry<V> registry, String id, Supplier<T> entry) {
        var register = Registry.register(registry, new ResourceLocation(Lootbags.MOD_ID, id), entry.get());
        return () -> register;
    }

    public static CreativeModeTab createTab() {
        return FabricItemGroupBuilder.create(new ResourceLocation(Lootbags.MOD_ID, "itemgroup"))
                .icon(() -> new ItemStack(McRegistry.LOOT_BAG.get()))
                .appendItems(list -> getLoot().stream().map(Loot::createLootBag).forEach(list::add))
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
