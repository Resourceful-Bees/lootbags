package tech.thatgravyboat.lootbags.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.api.LootType;
import tech.thatgravyboat.lootbags.common.recipe.Loot;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.stream.Stream;

public class LootbagsClient {

    public static void onRegisterProperties(TriConsumer<Item, ResourceLocation, ClampedItemPropertyFunction> registry) {
        registry.accept(McRegistry.LOOT_BAG.get(), new ResourceLocation(Lootbags.MOD_ID, "type"), (ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) -> {
            if (stack.hasTag() && stack.getOrCreateTag().contains("Type", Tag.TAG_STRING)) {
                return LootType.getId(stack.getOrCreateTag().getString("Type"));
            }
            return 0f;
        });
    }

    public static Stream<ItemStack> getLootbagItems() {
        var level = Minecraft.getInstance().level;
        if (level == null) {
            return Stream.of();
        }
        return level.getRecipeManager().getAllRecipesFor(McRegistry.LOOT_RECIPE.get())
                .stream()
                .map(Loot::createLootBag);
    }
}
