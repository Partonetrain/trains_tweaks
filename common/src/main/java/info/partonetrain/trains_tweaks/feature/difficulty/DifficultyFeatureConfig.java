package info.partonetrain.trains_tweaks.feature.difficulty;

import net.minecraft.core.UUIDUtil;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.UUID;

public class DifficultyFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue DAMAGE_SCALING;
    public static ModConfigSpec.DoubleValue REGIONAL_DIFFICULTY_MOD;
    public static ModConfigSpec.DoubleValue SPECIAL_MULTIPLIER_MOD;
    public static ModConfigSpec.ConfigValue<String> MODIFIED_DIFFICULTY_PLAYERS;
    //Dev: 5084e6f3-8f54-43f1-8df5-1dca109e430f on mcuuid 380df991-f603-344c-a090-369bad2a924a in dev
    public static ModConfigSpec.DoubleValue MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue MODIFIED_DIFFICULTY_DAMAGE_RECEIVED_MULTIPLIER;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the tweaks in this feature")
                .define("Difficulty Tweaks",false);

        DAMAGE_SCALING = builder.comment("If set to false, the 0.5x (Easy) or 1.5x (Hard) multipliers will not be applied to incoming damage to players")
                .comment("There are many other, more interesting ways the difficulty setting matters - see below options")
                .define("Damage Scaling", true);

        REGIONAL_DIFFICULTY_MOD = builder.comment("This is a constant modifier to Regional Difficulty")
                .comment("This value is added to the vanilla calculation. Higher values are more difficult. End result cannot go below 0")
                .comment("See https://minecraft.wiki/w/Difficulty#Regional_difficulty for more information on calculation and effects")
                .defineInRange("Regional Difficulty Mod", 0D, -10D, 10D);

        SPECIAL_MULTIPLIER_MOD = builder.comment("This is a constant modifier to Special Multiplier AKA Clamped Regional Difficulty")
                .comment("This value is added to the vanilla calculation (which is influenced by Regional Difficulty). Higher values are more difficult. End result will not go below 0")
                .comment("See https://minecraft.wiki/w/Difficulty#Clamped_regional_difficulty for more information on calculation and effects")
                .defineInRange("Special Multiplier Mod", 0D, -5D, 5D);

        MODIFIED_DIFFICULTY_PLAYERS = builder.comment("UUIDs of players, separated by comma, who will have damage received and/or damage output modifiers applied")
                .comment("This can be used to make the game harder or easier for certain players")
                .comment("Use a tool like mcuuid.net to find UUIDs")
                .define("Modified Difficulty Players", "380df991-f603-344c-a090-369bad2a924a");

        MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER = builder.comment("A multiplier for damage output from players in the Modified Difficulty Players list")
                .comment("Leave at 1.0 for normal damage")
                .comment("This is applied after armor reduction")
                .defineInRange("Modified Difficulty Damage Multiplier", 1.0D, 0.1D, 10.0D);

        MODIFIED_DIFFICULTY_DAMAGE_RECEIVED_MULTIPLIER = builder.comment("A multiplier for damage received for players in the Modified Difficulty Players list")
                .comment("Leave at 1.0 for normal damage")
                .defineInRange("Modified Difficulty Damage Received Multiplier", 1.0D, 0.1D, 10.0D);
    }
}
