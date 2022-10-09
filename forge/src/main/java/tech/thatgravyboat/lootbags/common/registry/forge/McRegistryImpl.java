package tech.thatgravyboat.lootbags.common.registry.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.common.recipe.LootRecipe;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class McRegistryImpl {

    public static final Map<Registry<?>, DeferredRegister<?>> REGISTRIES = new HashMap<>();

    public static <V, T extends V>  Supplier<T> register(Registry<V> registry, String id, Supplier<T> entry) {
        return getOrCreateRegistry(registry).register(id, entry);
    }

    @SuppressWarnings("unchecked")
    public static <T> DeferredRegister<T> getOrCreateRegistry(Registry<T> registry) {
        if(REGISTRIES.containsKey(registry)) return (DeferredRegister<T>) REGISTRIES.get(registry);
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registry.key(), Lootbags.MOD_ID);
        REGISTRIES.put(registry, deferredRegister);
        return deferredRegister;
    }

    public static CreativeModeTab createTab() {
        return new CreativeModeTab(Lootbags.MOD_ID + ".itemgroup") {

            @Override
            public net.minecraft.world.item.@NotNull ItemStack makeIcon() {
                return new ItemStack(McRegistry.LOOT_BAG.get());
            }

            @Override
            public void fillItemList(@NotNull NonNullList<ItemStack> list) {
                getLoot().stream()
                    .map(LootRecipe::createLootBag)
                    .forEach(list::add);
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    private static List<LootRecipe> getLoot() {
        var level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        return level.getRecipeManager().getAllRecipesFor(McRegistry.LOOT_RECIPE.get());
    }
}
