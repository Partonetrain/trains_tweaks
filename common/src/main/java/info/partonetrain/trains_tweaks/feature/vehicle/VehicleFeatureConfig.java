package info.partonetrain.trains_tweaks.feature.vehicle;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class VehicleFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue INSTANT_BREAK;
    public static ModConfigSpec.BooleanValue BETTER_BOAT_BREAKING;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to vehicles")
                .define("Vehicle Tweaks",true);

        INSTANT_BREAK = builder.comment("If true, players will instantly break vehicles such as boats and minecarts, even with their fists")
                .comment("This is similar to what happens in Creative mode, except the vehicle drops its item")
                .define("Instant Break", true);

        BETTER_BOAT_BREAKING = builder.comment("If true, boats breaking lily pads is replaced by boats breaking any block in the tag " + Constants.BOAT_BREAKS_TAG.location())
                .comment("This version of the mechanic checks a 3x2x3 area around occupied boats for said blocks, so boats aren't slowed down by lily pads (breaking them before the boat makes contact)")
                .define("Better Boat Breaking", true);

    }
}
