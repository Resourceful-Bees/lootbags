package tech.thatgravyboat.lootbags.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import net.minecraft.world.item.ItemStack;

public record LootEntry(ItemStack stack, int weight) {
    public static final Codec<LootEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ItemStackCodec.CODEC.fieldOf("stack").forGetter(LootEntry::stack),
        Codec.INT.fieldOf("weight").forGetter(LootEntry::weight)
    ).apply(instance, LootEntry::new));
}