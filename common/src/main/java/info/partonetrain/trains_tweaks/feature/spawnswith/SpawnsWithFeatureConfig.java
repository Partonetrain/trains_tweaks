package info.partonetrain.trains_tweaks.feature.spawnswith;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.platform.Services;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SpawnsWithFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue GENERIC_MOB_TABLES;
    public static ModConfigSpec.BooleanValue GENERIC_TABLE_ONLY_ARMOR;
    public static ModConfigSpec.DoubleValue EQUIPMENT_TABLE_DROP_CHANCE;

    public static ModConfigSpec.BooleanValue APPLY_TO_ABSTRACT_SKELETON_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_DROWNED_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_FOX_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_PIGLIN_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_PIGLIN_BRUTE_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_PILLAGER_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_VEX_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_VINDICATOR_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_WITHER_SKELETON_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_ZOMBIE_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_ZOMBIFIED_PIGLIN_SPAWN;

    private static boolean trueIfDev = Services.PLATFORM.isDevelopmentEnvironment();

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to mob spawn equipment")
                .define("Enable equipment spawn tweaks", trueIfDev);

        GENERIC_MOB_TABLES = builder.comment("If set to true, mobs that can spawn with armor in vanilla will roll " + Constants.GENERIC_EQUIPMENT_LOOT_TABLE.location() + " instead of the vanilla calculation")
                .comment("In this case, regional difficulty is passed to the table as a luck value, changing the weight of entries with a quality set")
                .comment("Note that unlike mob-specific tables, this loot table does NOT match vanilla behavior by default, so only enable if you intend to overwrite the table in a datapack")
                .define("Generic Mob Tables", trueIfDev);

        GENERIC_TABLE_ONLY_ARMOR = builder.comment("If set to true, the generic table will be considered to only have armor")
                .comment("This should only be false if you have defined mainhand/offhand items in the table, and want most mobs to have the same equipment rolls, including mainhand and offhand")
                .define("Generic Table Only Armor", true);

        EQUIPMENT_TABLE_DROP_CHANCE = builder.comment("The drop chance for every equipment item generated from equipment loot tables")
                .comment("For reference, 8.5% is the default chance for a mob to drop a piece of equipment it spawned with (with the exception of trial chamber spawns, which is 0%)")
                .defineInRange("Equipment Table Drop Chance", 0.085D, 0D, 1D);

        APPLY_TO_ABSTRACT_SKELETON_SPAWN = builder.comment("Whether or not to convert the hardcoded skeleton/stray/bogged spawn held item to the loot table " + Constants.ABSTRACT_SKELETON_SPAWN_LOOT_TABLE.location())
                .comment("This happens after the generic table is rolled")
                .define("Convert Skeletons Spawn", trueIfDev);

        APPLY_TO_DROWNED_SPAWN = builder.comment("Whether or not to convert the hardcoded drowned spawn held item to the loot table " + Constants.DROWNED_SPAWN_LOOT_TABLE.location())
                .define("Convert Drowned Spawn", trueIfDev);

        APPLY_TO_FOX_SPAWN = builder.comment("Whether or not to convert the hardcoded fox spawn held item to the loot table " + Constants.FOX_SPAWN_LOOT_TABLE.location())
                .define("Convert Fox Spawn", trueIfDev);

        APPLY_TO_PIGLIN_SPAWN = builder.comment("Whether or not to convert the hardcoded piglin spawn armor to the loot table " + Constants.PIGLIN_SPAWN_LOOT_TABLE.location())
                .define("Convert Piglin Spawn", trueIfDev);

        APPLY_TO_PIGLIN_SPAWN = builder.comment("Whether or not to convert the hardcoded piglin spawn held items to the loot table " + Constants.PIGLIN_BRUTE_SPAWN_LOOT_TABLE.location())
                .define("Convert Piglin Brute Spawn", trueIfDev);

        APPLY_TO_PILLAGER_SPAWN = builder.comment("Whether or not to convert the hardcoded pillager spawn held items to the loot table " + Constants.PILLAGER_SPAWN_LOOT_TABLE.location())
                .comment("This does not effect pillagers spawned as part of a raid")
                .define("Convert Pillager Spawn", trueIfDev);

        APPLY_TO_VEX_SPAWN = builder.comment("Whether or not to convert the hardcoded vex spawn held items to the loot table " + Constants.VEX_SPAWN_LOOT_TABLE.location())
                .define("Convert Vex Spawn", trueIfDev);

        APPLY_TO_VINDICATOR_SPAWN = builder.comment("Whether or not to convert the hardcoded vindicator spawn held items to the loot table " + Constants.VINDICATOR_SPAWN_LOOT_TABLE.location())
                .comment("This does not effect vindicators spawned as part of a raid")
                .define("Convert Vindicator Spawn", trueIfDev);

        APPLY_TO_WITHER_SKELETON_SPAWN = builder.comment("Whether or not to convert the hardcoded wither skeleton spawn held items to the loot table " + Constants.WITHER_SKELETON_SPAWN_LOOT_TABLE.location())
                .define("Convert Wither Skeleton Spawn", trueIfDev);

        APPLY_TO_ZOMBIE_SPAWN = builder.comment("Whether or not to convert the hardcoded zombie/zombie villager/husk spawn held items to the loot table " + Constants.ZOMBIE_SPAWN_LOOT_TABLE.location())
                .comment("This happens after the generic table is rolled")
                .define("Convert Zombie Spawn", trueIfDev);

        APPLY_TO_ZOMBIFIED_PIGLIN_SPAWN = builder.comment("Whether or not to convert the hardcoded zombified piglin spawn held items to the loot table " + Constants.ZOMBIFIED_PIGLIN_SPAWN_LOOT_TABLE.location())
                .define("Convert Pillager Spawn", trueIfDev);

    }
}
