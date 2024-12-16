package info.partonetrain.trains_tweaks.feature.spawnswith;

import info.partonetrain.trains_tweaks.Constants;
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
    public static ModConfigSpec.BooleanValue APPLY_TO_PILLAGER_SPAWN;
    public static ModConfigSpec.BooleanValue APPLY_TO_ZOMBIE_SPAWN;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to mob spawn equipment")
                .define("Enable equipment spawn tweaks",/*false*/ true);

        GENERIC_MOB_TABLES = builder.comment("If set to true, mobs that can spawn with armor in vanilla will roll " + Constants.GENERIC_EQUIPMENT_LOOT_TABLE.location() + " instead of the vanilla calculation")
                .comment("In this case, regional difficulty is passed to the table as a luck value, changing the weight of entries with a quality set")
                .comment("Note that this loot table does NOT match vanilla behavior by default, so only enable if you intend to overwrite the table in a datapack")
                .define("Generic Mob Tables", /*false*/ true);

        GENERIC_TABLE_ONLY_ARMOR = builder.comment("If set to true, the generic table will be considered to only have armor")
                .comment("This should only be false if you have defined mainhand/offhand items in the table, and want most mobs to have the same equipment rolls, including mainhand and offhand")
                .define("Generic Table Only Armor", true);

        EQUIPMENT_TABLE_DROP_CHANCE = builder.comment("The drop chance for every equipment item generated from equipment loot tables")
                .comment("For reference, 8.5% is the default chance for a mob to drop a piece of equipment it spawned with (with the exception of trial chamber spawns, which is 0%)")
                .defineInRange("Equipment Table Drop Chance", 0.085D, 0D, 1D);

        APPLY_TO_ABSTRACT_SKELETON_SPAWN = builder.comment("Whether or not to convert the hardcoded skeleton/stray/bogged spawn held item to the loot table " + Constants.ABSTRACT_SKELETON_SPAWN_LOOT_TABLE.location())
                .define("Convert Skeletons Spawn", true);

        APPLY_TO_DROWNED_SPAWN = builder.comment("Whether or not to convert the hardcoded drowned spawn held item to the loot table " + Constants.DROWNED_SPAWN_LOOT_TABLE.location())
                .define("Convert Drowned Spawn", true);

        APPLY_TO_FOX_SPAWN = builder.comment("Whether or not to convert the hardcoded fox spawn held item to the loot table " + Constants.FOX_SPAWN_LOOT_TABLE.location())
                .define("Convert Fox Spawn", true);

        APPLY_TO_PILLAGER_SPAWN = builder.comment("Whether or not to convert the hardcoded pillager spawn held items to the loot table " + Constants.PILLAGER_SPAWN_LOOT_TABLE.location())
                .define("Convert Pillager Spawn", false);

        APPLY_TO_ZOMBIE_SPAWN = builder.comment("Whether or not to convert the hardcoded zombie/zombie villager/husk spawn held items to the loot table " + Constants.ZOMBIE_SPAWN_LOOT_TABLE.location())
                .comment("This happens after the generic table is rolled")
                .define("Convert Zombie Spawn", false);

    }
}
