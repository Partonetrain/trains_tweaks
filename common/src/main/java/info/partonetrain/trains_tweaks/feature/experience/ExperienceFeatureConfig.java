package info.partonetrain.trains_tweaks.feature.experience;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ExperienceFeatureConfig {
    public static ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.IntValue BASE_XP;
    public static ModConfigSpec.BooleanValue CURVE_MODE;
    public static ModConfigSpec.IntValue LEVEL_CAP;
    public static ModConfigSpec.IntValue CAPPED_XP;
    public static ModConfigSpec.IntValue CURVE_MODE_MULTIPLIER;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder
                .comment("Whether or not the custom experience-per-level formula is used")
                .define("Custom Experience", false);

        BASE_XP = builder
                .comment("The amount of experience to go from level 0 to level 1.")
                .comment("If Curve Mode is false, this amount is for every level.")
                .defineInRange("Base XP", 10, 0, Integer.MAX_VALUE);

        CURVE_MODE = builder
                .comment("Whether or not Curve Mode is used, increasing the cost to next level for each level.")
                .comment("Curve Mode calculation is XPToNextLevel = (BaseXP + (experienceLevel * curveModeMultiplier)).")
                .define("Curve Mode", true);

        LEVEL_CAP = builder
                .comment("The level at which the amount of XP required for the next level stops increasing.")
                .comment("Only relevant to Curve Mode. Set to 0 to disable")
                .defineInRange("Level Cap", 50, 0, Integer.MAX_VALUE);

        CAPPED_XP = builder
                .comment("If Level Cap > 0, the amount of experience required for the next level.")
                .defineInRange("Capped XP", 107, 0, Integer.MAX_VALUE);

        CURVE_MODE_MULTIPLIER = builder
                .comment("The multiplier used in the Curve Mode calculation.")
                .defineInRange("Curve Mode Multiplier", 2, 1, Integer.MAX_VALUE);

    }
}
