package tech.thatgravyboat.lootbags.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeType;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.apache.commons.lang3.NotImplementedException;
import tech.thatgravyboat.lootbags.common.items.LootBagItem;
import tech.thatgravyboat.lootbags.common.recipe.Loot;
import tech.thatgravyboat.lootbags.common.recipe.LootSerializer;

import java.util.function.Supplier;

public class McRegistry {

    public static final CreativeModeTab TAB = createTab();
    public static final Supplier<Item> LOOT_BAG = register(Registry.ITEM, "loot_bag", () -> new LootBagItem(new Item.Properties().tab(TAB)));
    public static final Supplier<RecipeType<Loot>> LOOT_RECIPE = register(Registry.RECIPE_TYPE, "loot", () -> CodecRecipeType.of("loot"));
    public static final Supplier<RecipeSerializer<Loot>> LOOT_SERIALIZER = register(Registry.RECIPE_SERIALIZER, "loot", LootSerializer::new);


    public static void register() {
        //Init class
    }

    @ExpectPlatform
    public static  <V, T extends V> Supplier<T> register(Registry<V> registry, String id, Supplier<T> entry) {
        throw new NotImplementedException("Not yet implemented");
    }

    @ExpectPlatform
    public static CreativeModeTab createTab() {
        throw new NotImplementedException("Not yet implemented");
    }


}
