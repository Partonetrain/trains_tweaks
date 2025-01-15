package info.partonetrain.trains_tweaks.feature.bee;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BeeFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue BEES_KEEP_STINGER;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder
                .comment("Whether or not to enable any tweaks relating to bees")
                .define("Custom Experience", false);

        BEES_KEEP_STINGER = builder.comment("If enabled, bees will not lose their stinger when they attack")
                .define("Bees Keep Stinger", true);
    }
}
