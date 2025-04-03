package info.partonetrain.trains_tweaks.feature.spawnswith;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.platform.Services;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SpawnsWithFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue GENERIC_MOB_TABLES;
    public static ModConfigSpec.DoubleValue EQUIPMENT_TABLE_DROP_CHANCE;

    private static boolean trueIfDev = Services.PLATFORM.isDevelopmentEnvironment();

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to mob spawn equipment")
                .define("Enable equipment spawn tweaks", trueIfDev);

        GENERIC_MOB_TABLES = builder.comment("If set to true, any mob in the entity type tag " + Constants.ROLLS_GENERIC_EQUIPMENT.location() + " will roll " + Constants.GENERIC_EQUIPMENT_LOOT_TABLE.location() + " after being spawned")
                .comment("In this case, regional difficulty is passed to the table as a luck value, changing the weight of entries with a quality set")
                .comment("Note that unlike the provided mob-specific tables, this loot table does NOT match vanilla behavior by default, so only enable if you intend to overwrite the table in a datapack")
                .comment("This table is designed to replace mob armor, so it should only contain armor")
                .define("Generic Mob Tables", trueIfDev);

        EQUIPMENT_TABLE_DROP_CHANCE = builder.comment("The drop chance for every equipment item generated from equipment loot tables")
                .comment("For reference, 8.5% is the default chance for a mob to drop a piece of equipment it spawned with (with the exception of trial chamber spawns, which is 0%)")
                .comment("See also: MobDrops.toml - \"Mob Equipment Drop Chance\"")
                .defineInRange("Equipment Table Drop Chance", 0.085D, 0D, 1D);

    }
}
