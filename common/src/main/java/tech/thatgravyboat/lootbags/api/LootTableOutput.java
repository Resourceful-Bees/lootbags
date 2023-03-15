package tech.thatgravyboat.lootbags.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.List;

public record LootTableOutput(ResourceLocation table) implements LootOutput {

    public static final Codec<LootTableOutput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("table").forGetter(LootTableOutput::table)
    ).apply(instance, LootTableOutput::new));
    
    @Override
    public List<ItemStack> retrieveLoot(Player player) {
        List<ItemStack> rewards = new ArrayList<>();
        if (player.level instanceof ServerLevel serverLevel) {
            LootContext lootContext = new LootContext.Builder(serverLevel)
                    .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .create(LootContextParamSets.CHEST);
            LootTable lootTable = serverLevel.getServer().getLootTables().get(table);
            if (lootTable == LootTable.EMPTY) {
                player.sendSystemMessage(Component.literal("Loot table " + table + " does not exist!"));
            } else {
                lootTable.getRandomItems(lootContext, item -> LootOutput.mergeItemStacks(rewards, item.copy()));
            }
        }
        return rewards;
    }
}
