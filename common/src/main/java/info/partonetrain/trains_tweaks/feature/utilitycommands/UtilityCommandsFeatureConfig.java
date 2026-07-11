package info.partonetrain.trains_tweaks.feature.utilitycommands;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UtilityCommandsFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue VANILLA_DEBUG_COMMANDS;
    public static ModConfigSpec.BooleanValue KILL_NON_PLAYERS_COMMAND;
    public static ModConfigSpec.BooleanValue SHOW_SCHEDULED_FUNCTIONS;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable Train's Tweaks operator commands")
                .define("Utility Commands",false);

        VANILLA_DEBUG_COMMANDS = builder.comment("If set to true, vanilla debugging commands will be forcibly enabled")
                .comment("These commands are: /test, /raid, /debugpath, /debugmobspawning, /warden_spawn_tracker, /spawn_armor_trims, /serverpack, /debugconfig")
                .comment("Not recommended unless you need one specifically; there's no guarantee these actually work")
                .define("Vanilla Debug Commands",false);

        KILL_NON_PLAYERS_COMMAND = builder.comment("If set to true, /kill_non_players command will be available")
                .comment("This command simply discards all non-player entities, without dropping items")
                .define("Kill Non Players",true);

        SHOW_SCHEDULED_FUNCTIONS = builder.comment("If set to true, /show_scheduled_functions command will be available")
                .comment("This prints the name of every scheduled function to the chat at when the gametime it is scheduled for")
                .comment("This can be useful for debugging datapacks and/or determining if they are causing any lag")
                .define("Show Scheduled Functions",true);

    }
}
