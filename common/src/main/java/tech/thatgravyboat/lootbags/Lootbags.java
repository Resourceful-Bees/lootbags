package tech.thatgravyboat.lootbags;

import tech.thatgravyboat.lootbags.common.network.NetworkHandlers;
import tech.thatgravyboat.lootbags.common.registry.McRegistry;

public class Lootbags {
	public static final String MOD_ID = "lootbags";

	public static void init() {
		McRegistry.ITEMS.init();
		McRegistry.RECIPE_TYPES.init();
		McRegistry.RECIPE_SERIALIZERS.init();
		NetworkHandlers.register();
	}
}