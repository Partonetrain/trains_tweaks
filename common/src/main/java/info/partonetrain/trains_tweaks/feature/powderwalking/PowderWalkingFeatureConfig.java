package info.partonetrain.trains_tweaks.feature.powderwalking;

import net.neoforged.neoforge.common.ModConfigSpec;

public class PowderWalkingFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the functionality of the trains_tweaks:powder_walking_armor and trains_tweaks:powder_walking_item item tags")
                .comment("Reminder: there is a vanilla entity tag for this named minecraft:powder_snow_walkable_mobs")
                .define("Powder Walking tweak",true);
    }
}
