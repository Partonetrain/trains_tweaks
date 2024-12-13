package info.partonetrain.trains_tweaks.feature.spawnswith;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SpawnsWithFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue APPLY_TO_FOX_SPAWN;
    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to mob spawn equipment")
                .define("Enable equipment spawn tweaks",false);

        APPLY_TO_FOX_SPAWN = builder.comment("Whether or not to convert the hardcoded fox spawn held item to the loot table trains_tweaks:equipment/fox")
                .define("Convert Fox Spawn", true);

    }
}
