package tech.thatgravyboat.lootbags.forge;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.client.LootbagsClient;

@Mod(Lootbags.MOD_ID)
public class LootbagsForge {
    public LootbagsForge() {
        Lootbags.isClient = FMLLoader.getDist().isClient();
        Lootbags.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(LootbagsForge::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                LootbagsClient.onRegisterProperties(ItemProperties::register)
        );
    }
}