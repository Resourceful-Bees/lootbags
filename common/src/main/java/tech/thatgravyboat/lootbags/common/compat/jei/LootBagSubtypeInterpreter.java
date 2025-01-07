package tech.thatgravyboat.lootbags.common.compat.jei;

import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum LootBagSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    INSTANCE;

    @Override
    public @NotNull String apply(@NotNull ItemStack ingredient, @NotNull UidContext context) {
        CompoundTag tag = ingredient.getTag();
        return tag != null && tag.contains("Loot") ? tag.getString("Loot") : NONE;
    }
}
