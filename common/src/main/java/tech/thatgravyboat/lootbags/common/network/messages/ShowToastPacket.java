package tech.thatgravyboat.lootbags.common.network.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.client.LootbagsClient;
import tech.thatgravyboat.lootbags.common.recipe.LootRecipe;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.ArrayList;
import java.util.List;

public record ShowToastPacket(LootRecipe recipe, List<ItemStack> rewards) implements Packet<ShowToastPacket> {
    public static final ResourceLocation ID = new ResourceLocation(Lootbags.MOD_ID, "show_toast");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ShowToastPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ShowToastPacket> {

        @Override
        public void encode(ShowToastPacket showToastPacket, FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeResourceLocation(showToastPacket.recipe.id());
            friendlyByteBuf.writeVarInt(showToastPacket.rewards.size());
            showToastPacket.rewards.forEach(friendlyByteBuf::writeItem);
        }

        @Override
        public ShowToastPacket decode(FriendlyByteBuf friendlyByteBuf) {
            LootRecipe recipe = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(McRegistry.LOOT_RECIPE.get()).stream().filter(recipe1 -> recipe1.id().equals(friendlyByteBuf.readResourceLocation())).findFirst().orElseThrow();
            int size = friendlyByteBuf.readVarInt();
            List<ItemStack> rewards = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                rewards.add(friendlyByteBuf.readItem());
            }
            return new ShowToastPacket(recipe, rewards);
        }

        @Override
        public PacketContext handle(ShowToastPacket showToastPacket) {
            return (player, level) -> LootbagsClient.showLootToast(showToastPacket.recipe, showToastPacket.rewards);
        }
    }
}
