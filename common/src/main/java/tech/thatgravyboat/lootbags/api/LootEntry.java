package tech.thatgravyboat.lootbags.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.lootbags.common.utils.ItemStackCodec;

public record LootEntry(ItemStack stack, int weight) {
        public static final Codec<LootEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStackCodec.CODEC.fieldOf("stack").forGetter(LootEntry::stack),
            Codec.INT.fieldOf("weight").forGetter(LootEntry::weight)
        ).apply(instance, LootEntry::new));

        public void giveLoot(Player player) {
            if (player.addItem(stack.copy())) {
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            } else {
                ItemEntity itemEntity = player.drop(stack.copy(), false);
                if (itemEntity != null) {
                    itemEntity.setNoPickUpDelay();
                    itemEntity.setOwner(player.getUUID());
                }
            }
        }
    }