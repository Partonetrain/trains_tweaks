package info.partonetrain.trains_tweaks.feature.goat;

import net.neoforged.neoforge.common.ModConfigSpec;

public class GoatFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.DoubleValue SCREAMING_GOAT_SPAWN_CHANCE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder
                .comment("Whether or not to enable any tweaks relating to goats")
                .define("Goat Tweaks", false);

        SCREAMING_GOAT_SPAWN_CHANCE = builder.comment("Goats will have this chance to spawn as a screaming goat")
                .comment("Leave as default for vanilla value")
                .defineInRange("Screaming Goat Spawn Chance", 0.02, 0.00, 1.0);
    }
}
