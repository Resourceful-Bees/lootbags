package tech.thatgravyboat.lootbags.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeType;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.common.items.LootBagItem;
import tech.thatgravyboat.lootbags.common.recipe.Loot;

import java.util.function.Supplier;

public class McRegistry {

    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Lootbags.MOD_ID);
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, Lootbags.MOD_ID);
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, Lootbags.MOD_ID);

    public static final Supplier<Item> LOOT_BAG = ITEMS.register("loot_bag", () -> new LootBagItem(new Item.Properties()));
    public static final Supplier<RecipeType<Loot>> LOOT_RECIPE = RECIPE_TYPES.register("loot", () -> CodecRecipeType.of("loot"));
    public static final Supplier<RecipeSerializer<Loot>> LOOT_SERIALIZER = RECIPE_SERIALIZERS.register("loot", () -> new CodecRecipeSerializer<>(LOOT_RECIPE.get(), Loot::codec));

}
