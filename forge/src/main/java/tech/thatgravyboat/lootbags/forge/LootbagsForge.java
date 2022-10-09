package tech.thatgravyboat.lootbags.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.client.LootbagsClient;
import tech.thatgravyboat.lootbags.common.registry.forge.McRegistryImpl;

@Mod(Lootbags.MOD_ID)
public class LootbagsForge {
    public LootbagsForge() {
        Lootbags.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        McRegistryImpl.REGISTRIES.values().forEach(deferredRegister -> deferredRegister.register(modEventBus));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> LootbagsClient::init);
    }
}