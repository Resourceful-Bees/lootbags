package tech.thatgravyboat.lootbags.api;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec;

import java.util.Locale;
import java.util.Map;

public enum LootType {
    COMMON(0.1f),
    UNCOMMON(0.2f),
    RARE(0.3f),
    EPIC(0.4f),
    LEGENDARY(0.5f);

    public static final Codec<LootType> CODEC = EnumCodec.of(LootType.class);
    private static final Map<String, LootType> BY_NAME = Map.of(
        "common", COMMON,
        "uncommon", UNCOMMON,
        "rare", RARE,
        "epic", EPIC,
        "legendary", LEGENDARY
    );

    private final float id;

    LootType(float id) {
        this.id = id;
    }

    public static float getId(String input) {
        LootType type = BY_NAME.get(input.toLowerCase(Locale.ROOT));
        return type != null ? type.id : 0.0f;
    }
}
