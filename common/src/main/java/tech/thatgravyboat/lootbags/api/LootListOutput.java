package tech.thatgravyboat.lootbags.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.CodecExtras;
import com.teamresourceful.resourcefullib.common.collections.WeightedCollection;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record LootListOutput(WeightedCollection<LootEntry> entries, int rolls) implements LootOutput {

    public static final Codec<LootListOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        CodecExtras.weightedCollection(LootEntry.CODEC, LootEntry::weight).fieldOf("entries").forGetter(LootListOutput::entries),
        Codec.INT.fieldOf("rolls").orElse(1).forGetter(LootListOutput::rolls)
    ).apply(instance, LootListOutput::new));

    @Override
    public List<ItemStack> retrieveLoot(Player player) {
        List<ItemStack> rewards = new ArrayList<>();
        for (int i = 0; i < rolls; i++) {
            LootOutput.mergeItemStacks(rewards, entries.next().stack().copy());
        }
        return rewards;
    }
}
