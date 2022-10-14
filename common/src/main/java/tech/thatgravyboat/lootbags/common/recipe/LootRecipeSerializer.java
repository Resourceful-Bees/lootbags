package tech.thatgravyboat.lootbags.common.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LootRecipeSerializer extends SimpleRecipeSerializer<Loot> {

    private static final Gson GSON = new Gson();

    public LootRecipeSerializer() {
        super(r -> null);
    }

    @Override
    public @NotNull Loot fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        return Loot.codec(id).parse(JsonOps.INSTANCE, json).getOrThrow(false, (t) -> {
            throw new JsonSyntaxException("Failed to parse recipe: " + id);
        });
    }

    @Nullable
    @Override
    public Loot fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        String data = buffer.readUtf();
        return Loot.codec(id).parse(JsonOps.COMPRESSED, GSON.fromJson(data, JsonArray.class)).result().orElse(null);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull Loot recipe) {
        Loot.codec(recipe.id()).encodeStart(JsonOps.COMPRESSED, recipe).result().map(GSON::toJson).ifPresent(buffer::writeUtf);
    }

}
