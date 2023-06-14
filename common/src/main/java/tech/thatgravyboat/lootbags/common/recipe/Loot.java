package tech.thatgravyboat.lootbags.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;
import com.teamresourceful.resourcefullib.common.color.ConstantColors;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.api.LootOutput;
import tech.thatgravyboat.lootbags.api.LootType;
import tech.thatgravyboat.lootbags.common.network.NetworkHandlers;
import tech.thatgravyboat.lootbags.common.network.messages.ShowToastPacket;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.List;

public record Loot(ResourceLocation id, String name, Color color, LootType type, LootOutput output) implements CodecRecipe<Container> {

    public static Codec<Loot> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                Codec.STRING.fieldOf("name").forGetter(Loot::name),
                Color.CODEC.fieldOf("color").orElse(ConstantColors.lightgray).forGetter(Loot::color),
                LootType.CODEC.fieldOf("rarity").orElse(LootType.COMMON).forGetter(Loot::type),
                LootOutput.CODEC.fieldOf("output").forGetter(Loot::output)
        ).apply(instance, Loot::new));
    }

    public ItemStack createLootBag() {
        ItemStack stack = new ItemStack(McRegistry.LOOT_BAG.get());
        CompoundTag tag = new CompoundTag();
        tag.putString("Loot", id().toString());
        tag.putString("Type", type().toString());
        tag.putString("Name", name());
        tag.putInt("Color", color().getValue());
        stack.setTag(tag);
        return stack;
    }

    public void openLootBag(Player player) {
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 2.0F, 2.0F);
        List<ItemStack> stacks = output.retrieveLoot(player);
        for (ItemStack stack : stacks) {
            if (player.addItem(stack.copy())) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            } else {
                ItemEntity itemEntity = player.drop(stack.copy(), false);
                if (itemEntity != null) {
                    itemEntity.setNoPickUpDelay();
                    itemEntity.setTarget(player.getUUID());
                }
            }
        }
        if (!stacks.isEmpty()) {
            NetworkHandlers.CHANNEL.sendToPlayer(new ShowToastPacket(this, stacks), player);
        }
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return McRegistry.LOOT_SERIALIZER.get();
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return McRegistry.LOOT_RECIPE.get();
    }
}
