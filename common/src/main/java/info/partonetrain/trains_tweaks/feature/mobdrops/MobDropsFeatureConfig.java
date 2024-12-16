package info.partonetrain.trains_tweaks.feature.mobdrops;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class MobDropsFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue APPLY_TO_WITHER_DEATH;
    public static ModConfigSpec.BooleanValue APPLY_TO_WITHER_ROSE;
    public static ModConfigSpec.BooleanValue APPLY_TO_CHICKEN_EGG;
    //public static ModConfigSpec.BooleanValue APPLY_TO_PANDA_SNEEZE; //they added this to vanilla lol
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

        APPLY_TO_WITHER_DEATH = builder.comment("Whether or not to convert the hardcoded Nether Star (that takes longer to despawn) drop to the loot table " + Constants.STAR_LOOT_TABLE.location())
                .define("Convert Nether Star Drop", true);

        APPLY_TO_WITHER_ROSE = builder.comment("Whether or not to convert the hardcoded Wither Rose drop to the loot table " + Constants.ROSE_LOOT_TABLE.location())
                .comment("If enabled, any entity tagged with " + Constants.ROSE_KILLER_TAG.location() + " will cause this loot table to be rolled")
                .comment("This will disable the placement of the block")
                .define("Convert Wither Rose Drop", false);

        APPLY_TO_CHICKEN_EGG = builder.comment("Whether or not to convert the hardcoded chicken egg lay to the loot table " + Constants.EGG_LOOT_TABLE.location())
                .define("Convert Chicken Egg", true);

        BABIES_DROP_LOOT = builder.comment("Allow baby mobs to drop their standard loot table")
                .define("Baby mobs drop loot", false);

        BABIES_DROP_EXPERIENCE = builder.comment("Allow baby mobs to drop their standard amount of experience")
                .define("Baby mobs drop experience", false);
    }
}
