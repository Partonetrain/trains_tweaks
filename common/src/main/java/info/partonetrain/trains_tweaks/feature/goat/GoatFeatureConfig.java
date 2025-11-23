package info.partonetrain.trains_tweaks.feature.goat;

import net.neoforged.neoforge.common.ModConfigSpec;

public class GoatFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.DoubleValue SCREAMING_GOAT_SPAWN_CHANCE;
    public static ModConfigSpec.DoubleValue ONE_HORN_GOAT_CHANCE;
    public static ModConfigSpec.BooleanValue SHEAR_HORNS;
    public static ModConfigSpec.DoubleValue SPAWN_HORNS_ON_DEATH;

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

        ONE_HORN_GOAT_CHANCE = builder.comment("Goats will have this chance to spawn with only one horn")
                .comment("Leave as default for vanilla value")
                .defineInRange("One-horn Goat Spawn Chance", 0.10000000149011612, 0.00, 1.0);

        SHEAR_HORNS = builder.comment("If true, you will be able to use Shears to remove horns from goats")
                .comment("For balance reasons, this will damage the goat and cause it to target you")
                .define("Shear Horns", false);

        SPAWN_HORNS_ON_DEATH = builder.comment("If set to > 0, goats may drop horns when they die")
                .defineInRange("Goat Horn Drop Chance", 0.01, 0.00, 1.0);
    }
}
