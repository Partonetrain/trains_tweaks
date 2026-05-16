package info.partonetrain.trains_tweaks.feature.bee;

import net.neoforged.neoforge.common.ModConfigSpec;

public class BeeFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue BEES_KEEP_STINGER;
    public static ModConfigSpec.BooleanValue ALWAYS_SEDATED;
    public static ModConfigSpec.IntValue HONEYCOMB_DROPPED;
    public static ModConfigSpec.IntValue HONEY_LEVEL_BONUS_CHANCE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder
                .comment("Whether or not to enable any tweaks relating to bees")
                .define("Bee Tweaks", false);

        BEES_KEEP_STINGER = builder.comment("If enabled, bees will not lose their stinger when they attack")
                .define("Bees Keep Stinger", true);

        ALWAYS_SEDATED = builder.comment("If enabled, campfires below beehives/bee nests are not necessary to prevent bees from becoming angry")
                .define("Always Sedated", false);

        HONEYCOMB_DROPPED = builder.comment("The number of honey combs dropped when shearing a beehive/bee nest")
                .defineInRange("Honeycomb Dropped", 3, 1, 64);

        HONEY_LEVEL_BONUS_CHANCE = builder.comment("The chance (1/x) that the honey level of a beehive/bee nest is incremented by 2 instead of 1 when a pollinated bee exits a beehive")
                .defineInRange("Honey Level Bonus", 100, 1, 100);
    }
}
