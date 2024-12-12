package info.partonetrain.trains_tweaks.feature.difficulty;

import net.neoforged.neoforge.common.ModConfigSpec;

public class DifficultyFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue DAMAGE_SCALING;
    public static ModConfigSpec.DoubleValue REGIONAL_DIFFICULTY_MOD;
    public static ModConfigSpec.DoubleValue SPECIAL_MULTIPLIER_MOD;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the tweaks in this feature")
                .define("Difficulty tweaks",false);

        DAMAGE_SCALING = builder.comment("If set to false, the 0.5x (Easy) or 1.5x (Hard) multipliers will not be applied to incoming damage to players")
                .comment("There are many other, more interesting ways the difficulty setting matters - see below options")
                .define("Damage Scaling", true);

        REGIONAL_DIFFICULTY_MOD = builder.comment("This is a constant modifier to Regional Difficulty")
                .comment("This value is added to the vanilla calculation. Higher values are more difficult. End result cannot go below 0")
                .comment("See https://minecraft.wiki/w/Difficulty#Regional_difficulty for more information on calculation and effects")
                .defineInRange("Regional Difficulty Mod", 0D, -10D, 10D);

        SPECIAL_MULTIPLIER_MOD = builder.comment("This is a constant modifier to Special Multiplier AKA Clamped Regional Difficulty")
                .comment("This value is added to the vanilla calculation (which is influenced by Regional Difficulty). Higher values are more difficult. End result cannot go below 0")
                .comment("See https://minecraft.wiki/w/Difficulty#Clamped_regional_difficulty for more information on calculation and effects")
                .defineInRange("Special Multiplier Mod", 0D, -5D, 5D);

    }
}
