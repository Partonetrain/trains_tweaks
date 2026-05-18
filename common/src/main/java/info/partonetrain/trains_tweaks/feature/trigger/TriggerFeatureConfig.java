package info.partonetrain.trains_tweaks.feature.trigger;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TriggerFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue GAME_TIME_TRIGGER;
    public static ModConfigSpec.BooleanValue DAY_TRIGGER;
    public static ModConfigSpec.BooleanValue SYSTEM_DATE_TRIGGER;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the Train's Tweaks advancement trigger feature")
                .comment("These triggers may have an extremely small performance impact, so it's recommended you keep them disabled if you are sure you won't use them")
                .define("Trigger tweaks",false);

        GAME_TIME_TRIGGER = builder.comment("Whether or not to register the " + Constants.GAME_TIME_TRIGGER + " trigger")
                .comment("This allows advancements to check for amount of ticks since world creation")
                .define("Game Time trigger", true);

        DAY_TRIGGER = builder.comment("Whether or not to register the " + Constants.DAY_TRIGGER + " trigger")
                .comment("This allows advancements to check for amount of days since world creation")
                .define("Day trigger", true);

        SYSTEM_DATE_TRIGGER = builder.comment("Whether or not to register the " + Constants.SYSTEM_DATE_TRIGGER + " trigger")
                .comment("This allows advancements to check for the system's year, month, and/or day")
                .define("System Date trigger", false);
    }
}
