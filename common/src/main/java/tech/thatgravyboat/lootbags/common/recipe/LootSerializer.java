package tech.thatgravyboat.lootbags.common.recipe;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

public class LootSerializer extends CodecRecipeSerializer<Loot> {

    public LootSerializer() {
        super(null, Loot::codec);
    }

    @Override
    public RecipeType<Loot> type() {
        return McRegistry.LOOT_RECIPE.get();
    }
}
