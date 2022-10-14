package tech.thatgravyboat.lootbags.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record LootEntry(ItemStack stack, int weight) {
    public static final Codec<LootEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStackCodec.CODEC.fieldOf("stack").forGetter(LootEntry::stack),
            Codec.INT.fieldOf("weight").forGetter(LootEntry::weight)
    ).apply(instance, LootEntry::new));
}