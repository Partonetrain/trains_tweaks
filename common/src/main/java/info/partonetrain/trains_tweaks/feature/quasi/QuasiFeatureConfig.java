package info.partonetrain.trains_tweaks.feature.quasi;

import net.neoforged.neoforge.common.ModConfigSpec;

public class QuasiFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.EnumValue<QuasiMode> DISPENSER_MODE;
    public static ModConfigSpec.EnumValue<QuasiMode> PISTON_MODE;
    public static ModConfigSpec.ConfigValue<String> TOGGLE_MESSAGE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable quasi-connectivity related tweaks")
                .comment("read more about quasi-connectivity here: https://minecraft.wiki/w/Tutorial:Quasi-connectivity")
                .define("Quasi-connectivity tweaks",false);

        DISPENSER_MODE = builder.comment("What mode Dispensers and Droppers will use to determine their quasi-connectivity")
                .defineEnum("Dispenser Quasi Mode", QuasiMode.SHIFT_RCLICK_OPT_OUT);

        PISTON_MODE = builder.comment("What mode Pistons and Sticky Pistons will use to determine their quasi-connectivity")
                .defineEnum("Piston Quasi Mode", QuasiMode.SHIFT_RCLICK_OPT_OUT);

        TOGGLE_MESSAGE = builder.comment("The message sent to players when opting in or out of quasi-connectivity for a particular block by shift-right-clicking it")
                .comment("You can use the placeholders $BLOCKNAME, $COORDS, and $QUASIENABLED, but they must not be the very first or very last part of the string")
                .comment("Set to blank for no message")
                .define("Toggle Message", "Quasi-connectivity has been $QUASIENABLED for $COORDS ($BLOCKNAME).");

    }
}
