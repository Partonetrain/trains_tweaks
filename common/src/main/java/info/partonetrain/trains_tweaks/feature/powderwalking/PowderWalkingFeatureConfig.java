package info.partonetrain.trains_tweaks.feature.powderwalking;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class PowderWalkingFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the functionality of the " + Constants.POWDER_WALKER_ARMOR_TAG.location() + " and " + Constants.POWDER_WALKER_ITEM_TAG.location() +" item tags")
                .comment("Reminder: there is a vanilla entity tag for this named minecraft:powder_snow_walkable_mobs")
                .define("Powder Walking tweak",true);
    }
}
