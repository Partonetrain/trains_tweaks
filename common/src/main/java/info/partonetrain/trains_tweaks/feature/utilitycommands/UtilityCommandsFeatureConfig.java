package info.partonetrain.trains_tweaks.feature.utilitycommands;

import net.neoforged.neoforge.common.ModConfigSpec;

public class UtilityCommandsFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue VANILLA_DEBUG_COMMANDS;
    public static ModConfigSpec.BooleanValue KILL_NON_PLAYERS_COMMAND;
    public static ModConfigSpec.BooleanValue SHOW_SCHEDULED_FUNCTIONS;
    public static ModConfigSpec.BooleanValue LOOT_COMMAND_CONSIDERS_LUCK;
    public static ModConfigSpec.BooleanValue SET_HUNGER;

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

        LOOT_COMMAND_CONSIDERS_LUCK = builder.comment("If set to true, the vanilla /loot spawn command will attempt to scan for nearby players and use their luck value")
                .comment("Players are scanned for within 3 blocks of the source of the command, which is not necessarily the same as the position arguments")
                .comment("This is useful for scripts or datapacks that spawn loot this way. It can apply to mods too, but mods really shouldn't be running serverside commands...")
                .define("Loot Spawn Command Considers Luck",true);

        SET_HUNGER = builder.comment("If set to true, the set_hunger command will be added")
                .comment("This is useful for testing foods that aren't always-edible")
                .comment("Note that the client's hunger bar only shakes when saturation is 0")
                .define("Set Hunger",true);

    }
}
