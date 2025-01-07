package tech.thatgravyboat.lootbags.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.Lootbags;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

@JeiPlugin
public class LootbagsJeiPlugin implements IModPlugin {

    private static final ResourceLocation UID = new ResourceLocation(Lootbags.MOD_ID, Lootbags.MOD_ID);

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(McRegistry.LOOT_BAG.get(), LootBagSubtypeInterpreter.INSTANCE);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }
}
