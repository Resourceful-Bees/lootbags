package tech.thatgravyboat.lootbags.api;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface LootOutput {

    Codec<LootOutput> CODEC = Codec.either(
        LootTableOutput.CODEC,
        LootListOutput.CODEC
    ).xmap(
        either -> either.map(table -> table, list -> list),
        loot -> loot instanceof LootTableOutput table ? Either.left(table) : Either.right((LootListOutput) loot)
    );

    List<ItemStack> retrieveLoot(Player player);

    static void mergeItemStacks(List<ItemStack> items, ItemStack stack) {
        for (ItemStack item : items) {
            if (ItemStack.isSameItemSameTags(item, stack)) {
                item.grow(stack.getCount());
                return;
            }
        }
        items.add(stack);
    }
}
