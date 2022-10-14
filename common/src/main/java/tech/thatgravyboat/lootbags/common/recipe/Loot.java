package tech.thatgravyboat.lootbags.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;
import com.teamresourceful.resourcefullib.common.color.ConstantColors;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.api.LootEntry;
import tech.thatgravyboat.lootbags.api.LootType;
import tech.thatgravyboat.lootbags.client.LootbagsClient;
import tech.thatgravyboat.lootbags.common.network.NetworkHandlers;
import tech.thatgravyboat.lootbags.common.network.messages.ShowToastPacket;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;
import tech.thatgravyboat.lootbags.common.utils.WeightedCollection;

import java.util.ArrayList;
import java.util.List;

public record Loot(ResourceLocation id, String name, Color color, LootType type, WeightedCollection<LootEntry> loot, int rolls) implements Recipe<Container> {

    public static Codec<Loot> codec(ResourceLocation id) {
        return RecordCodecBuilder.create(instance -> instance.group(
                RecordCodecBuilder.point(id),
                Codec.STRING.fieldOf("name").forGetter(Loot::name),
                Color.CODEC.fieldOf("color").orElse(ConstantColors.lightgray).forGetter(Loot::color),
                LootType.CODEC.fieldOf("rarity").orElse(LootType.COMMON).forGetter(Loot::type),
                WeightedCollection.codec(LootEntry.CODEC, LootEntry::weight).fieldOf("loot").forGetter(Loot::loot),
                Codec.intRange(1, Integer.MAX_VALUE).fieldOf("rolls").orElse(1).forGetter(Loot::rolls)
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

    public DisplayInfo getDisplayInfo(ItemStack reward) {
        return new DisplayInfo(reward, new TextComponent(name()), reward.getDisplayName(), null, FrameType.CHALLENGE, true, false, false);
    }

    public void openLootBag(Player player) {
        List<ItemStack> rewards = new ArrayList<>();
        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUNDLE_DROP_CONTENTS, SoundSource.PLAYERS, 2.0F, 2.0F);
        for (int i = 0; i < rolls; i++) {
            LootEntry next = loot.next();
            mergeItemStacks(rewards, next.stack().copy());
            next.giveLoot(player);
        }
        NetworkHandlers.CHANNEL.sendToPlayer(new ShowToastPacket(this, rewards), player);
    }

    private static void mergeItemStacks(List<ItemStack> items, ItemStack stack) {
        for (ItemStack item : items) {
            if (ItemStack.isSameItemSameTags(item, stack)) {
                item.grow(stack.getCount());
                return;
            }
        }
        items.add(stack);
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(@NotNull Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return McRegistry.LOOT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return McRegistry.LOOT_RECIPE.get();
    }
}
