package tech.thatgravyboat.lootbags.common.items;

import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

public class LootBagItem extends Item {

    public LootBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && stack.getOrCreateTag().contains("Loot", Tag.TAG_STRING)) {
            String id = stack.getOrCreateTag().getString("Loot");
            level.getRecipeManager().getAllRecipesFor(McRegistry.LOOT_RECIPE.get())
                    .stream()
                    .filter(recipe -> recipe.id().toString().equals(id))
                    .findFirst()
                    .ifPresent(loot -> loot.openLootBag(player));
        }
        stack.shrink(1);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public Component getName(ItemStack stack) {
        return new TranslatableComponent(stack.getOrCreateTag().getString("Name"));
    }
}
