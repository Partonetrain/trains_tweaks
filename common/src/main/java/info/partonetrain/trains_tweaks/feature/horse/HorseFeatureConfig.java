package info.partonetrain.trains_tweaks.feature.horse;

import net.neoforged.neoforge.common.ModConfigSpec;

public class HorseFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue GUARANTEE_GREATER_THAN_OR_EQUAL_TO_STATS;
    public static ModConfigSpec.DoubleValue MAX_HEALTH_BUFF;
    public static ModConfigSpec.DoubleValue SPEED_BUFF;
    public static ModConfigSpec.DoubleValue JUMP_BUFF;
    public static ModConfigSpec.IntValue LLAMA_STRENGTH_BUFF;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder
                .comment("Whether or not to enable any tweaks relating to horses and related mobs")
                .define("Horse Tweaks", true);

        GUARANTEE_GREATER_THAN_OR_EQUAL_TO_STATS = builder.comment("If set to true, a bred horse's stats will never have a random variation that makes that stat worse than the average of its parents")
                .define("Guarantee Greater Than Or Equal To Stats", true);

        MAX_HEALTH_BUFF = builder.comment("Newly-spawned horses (including offspring) with a health value lower than this will have their health upped to this minimum")
                .comment("For reference, the average is ~22.5")
                .comment("Leave as default for vanilla value")
                .defineInRange("Horse Health Minimum", 15.0, 15.0, 30.0);

        SPEED_BUFF = builder.comment("Newly-spawned horses (including offspring) with a speed value lower than this will have their speed upped to this minimum")
                .comment("For reference, the average is ~0.225, or 9.49 blocks/sec")
                .comment("Leave as default for vanilla value")
                .defineInRange("Horse Speed Minimum", 0.1125, 0.1125, 0.3375);

        JUMP_BUFF = builder.comment("Newly-spawned horses (including offspring) with a jump value lower than this will have their jump upped to this minimum")
                .comment("For reference, the average is ~0.7, or 2.89 blocks (does not scale linearly)")
                .comment("Leave as default for vanilla value")
                .defineInRange("Horse Jump Minimum", 0.4, 0.4, 1.0);

        LLAMA_STRENGTH_BUFF = builder.comment("Newly-spawned llamas (including offspring) with a strength value lower than this will have their strength upped to this minimum")
                .comment("Set to 0 to disable")
                .defineInRange("Llama Strength Minimum", 0, 0, 5);

    }
}
