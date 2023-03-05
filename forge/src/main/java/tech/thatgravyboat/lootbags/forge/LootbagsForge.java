package tech.thatgravyboat.lootbags.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.client.LootbagsClient;
import tech.thatgravyboat.lootbags.common.recipe.Loot;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

import java.util.List;

@Mod(Lootbags.MOD_ID)
public class LootbagsForge {
    public LootbagsForge() {
        Lootbags.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(LootbagsForge::clientSetup);
        modEventBus.addListener(LootbagsForge::onRegisterTabs);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        LootbagsClient.init();
    }

    public static void onRegisterTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(Lootbags.MOD_ID, "itemgroup"), builder -> {
            builder.title(Component.translatable("itemGroup.lootbags.itemgroup"));
            builder.icon(() -> new ItemStack(McRegistry.LOOT_BAG.get()));
            builder.displayItems((features, output, op) -> {
                if (FMLLoader.getDist().isClient()) {
                    getLoot().stream()
                        .map(Loot::createLootBag)
                        .forEach(output::accept);
                }
            });
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static List<Loot> getLoot() {
        var level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        return level.getRecipeManager().getAllRecipesFor(McRegistry.LOOT_RECIPE.get());
    }
}