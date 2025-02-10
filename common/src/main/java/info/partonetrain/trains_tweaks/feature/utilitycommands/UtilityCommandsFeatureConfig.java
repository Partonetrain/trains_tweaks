package info.partonetrain.trains_tweaks.feature.utilitycommands;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UtilityCommandsFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue VANILLA_DEBUG_COMMANDS;
    public static ModConfigSpec.BooleanValue KILL_NON_PLAYERS_COMMAND;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable Train's Tweaks operator commands")
                .define("Utility Commands",false);

        VANILLA_DEBUG_COMMANDS = builder.comment("If set to true, vanilla debugging commands will be forcibly enabled")
                .comment("These commands include /raid and /warden_spawn_tracker among others")
                .comment("Not recommended unless you need one specifically, as there's no guarantee these actually work")
                .define("Vanilla Debug Commands",false);

        KILL_NON_PLAYERS_COMMAND = builder.comment("If set to true, /kill_non_players command will be available")
                .comment("This command simply kills all loaded non-player entities essentially a shortcut for /kill @e[type=!player]")
                .define("Kill Non Players",true);


    }
}
