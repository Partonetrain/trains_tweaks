package info.partonetrain.trains_tweaks.feature.mobdrops;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MobDropsFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.DoubleValue MOB_EQUIPMENT_DROP_CHANCE;
    public static ModConfigSpec.BooleanValue APPLY_TO_WITHER_DEATH;
    public static ModConfigSpec.BooleanValue APPLY_TO_WITHER_ROSE;
    public static ModConfigSpec.BooleanValue APPLY_TO_CHICKEN_EGG; //TODO remove in 1.21.4
    public static ModConfigSpec.BooleanValue APPLY_TO_ARMADILLO_SHED; //TODO remove in 1.21.4 (does brushing use the same table?)
    public static ModConfigSpec.BooleanValue APPLY_TO_ARMADILLO_BRUSH;
    public static ModConfigSpec.BooleanValue APPLY_TO_TURTLE_GROW;
    public static ModConfigSpec.BooleanValue ADD_TURTLE_BRUSH; //someone should make datadriven brushable mobs
    public static ModConfigSpec.BooleanValue BABIES_DROP_LOOT;
    public static ModConfigSpec.BooleanValue BABIES_DROP_EXPERIENCE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to mob drops")
                .define("Enable drop tweaks",false);

        MOB_EQUIPMENT_DROP_CHANCE = builder.comment("The chance for any mob to drop equipment it spawned with")
                .comment("This should only affect mobs that did NOT spawn with a SpawnsWith table. See that feature's config for more details")
                .comment("Leaving it at the default value of 0.085 will not write the value again; if a modded mob specifies its equipment drop chances in its code, you'll probably want to leave this alone")
                .defineInRange("Mob Equipment Drop Chance", 0.085D, 0D, 1D);

        APPLY_TO_WITHER_DEATH = builder.comment("Whether or not to convert the hardcoded Nether Star (that takes longer to despawn) drop to the loot table " + Constants.STAR_LOOT_TABLE.location())
                .define("Convert Nether Star Drop", true);

        APPLY_TO_WITHER_ROSE = builder.comment("Whether or not to convert the hardcoded Wither Rose drop to the loot table " + Constants.ROSE_LOOT_TABLE.location())
                .comment("If enabled, any entity tagged with " + Constants.ROSE_KILLER_TAG.location() + " will cause this loot table to be rolled")
                .comment("This will disable the placement of the block")
                .define("Convert Wither Rose Drop", false);

        APPLY_TO_CHICKEN_EGG = builder.comment("Whether or not to convert the hardcoded chicken egg lay to the loot table " + Constants.EGG_LOOT_TABLE.location())
                .define("Convert Chicken Egg", true);

        APPLY_TO_ARMADILLO_SHED = builder.comment("Whether or not to convert the hardcoded armadillo shed to the loot table " + Constants.ARMADILLO_SHED_LOOT_TABLE.location())
                .define("Convert Armadillo Shed", true);

        APPLY_TO_ARMADILLO_BRUSH = builder.comment("Whether or not to convert the hardcoded brush armadillo drop to the loot table " + Constants.BRUSH_ARMADILLO_LOOT_TABLE.location())
                .define("Convert Brush Armadillo", true);

        APPLY_TO_TURTLE_GROW = builder.comment("Whether or not to convert the hardcoded turtle scute drop to the loot table " + Constants.TURTLE_GROW_LOOT_TABLE.location())
                .define("Convert Turtle Grow", true);

        ADD_TURTLE_BRUSH = builder.comment("Whether or not to add turtle scute brushing with the loot table " + Constants.TURTLE_GROW_LOOT_TABLE.location())
                .comment("This works identically to Armadillo brushing")
                .define("Add Turtle Brush", true);

        BABIES_DROP_LOOT = builder.comment("Allow baby mobs to drop their standard loot table")
                .define("Baby mobs drop loot", false);

        BABIES_DROP_EXPERIENCE = builder.comment("Allow baby mobs to drop their standard amount of experience")
                .define("Baby mobs drop experience", false);
    }
}
