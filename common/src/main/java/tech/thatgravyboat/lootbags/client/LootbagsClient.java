package tech.thatgravyboat.lootbags.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.api.LootType;
import tech.thatgravyboat.lootbags.client.popup.LootBagToast;
import tech.thatgravyboat.lootbags.common.recipe.LootRecipe;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.List;

public class LootbagsClient {

    public static void init() {
        registerProperty(McRegistry.LOOT_BAG.get(), new ResourceLocation(Lootbags.MOD_ID, "type"), (ItemStack stack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) -> {
            if (stack.hasTag() && stack.getOrCreateTag().contains("Type", Tag.TAG_STRING)) {
                return LootType.getId(stack.getOrCreateTag().getString("Type"));
            }
            return 0f;
        });
    }

    @ExpectPlatform
    public static void registerProperty(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        throw new NotImplementedException("Not implemented yet");
    }

    public static void showLootToast(LootRecipe recipe, List<ItemStack> rewards) {
        Minecraft.getInstance().getToasts().addToast(new LootBagToast(recipe, rewards));
    }
}
