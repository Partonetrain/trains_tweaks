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
    public static ModConfigSpec.BooleanValue SYSTEM_DAY_OF_WEEK_TRIGGER;
    public static ModConfigSpec.BooleanValue GLIDE_TRIGGER;
    public static ModConfigSpec.BooleanValue TAKE_DAMAGE_AND_LIVE_TRIGGER;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the Train's Tweaks advancement trigger feature")
                .comment("Some of these triggers may have an extremely small performance impact, so it's recommended you keep them disabled if you are sure you won't use them")
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

        SYSTEM_DAY_OF_WEEK_TRIGGER = builder.comment("Whether or not to register the " + Constants.SYSTEM_DAY_OF_WEEK_TRIGGER + " trigger")
                .comment("This allows advancements to check for the day of the week")
                .comment("The numeric values are: 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday, 7 = Sunday (follows ISO-8601/Java.time.DayOfWeek)")
                .define("System Day of Week trigger", false);

        GLIDE_TRIGGER = builder.comment("Whether or not to register the " + Constants.GLIDE_TRIGGER + " trigger")
                .comment("This allows advancements to check if the player is gliding with an elytra or other similar effect")
                .define("Glide trigger", true);

        TAKE_DAMAGE_AND_LIVE_TRIGGER = builder.comment("Whether or not to register the " + Constants.TAKE_DAMAGE_AND_LIVE_TRIGGER + " trigger")
                .comment("This triggers checks how much (post-armor reduction, not counting absorption) damage you took and if it is within the bounds and if you lived, grants the criteria")
                .define("Take Damage and Live trigger", true);
    }
}
