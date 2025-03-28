package info.partonetrain.trains_tweaks.feature.interdimensional;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class InterdimensionalFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue NETHER_PORTAL_BLOCK_TAG;
    public static ModConfigSpec.BooleanValue NETHER_PORTAL_DIMENSIONS_TAG;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder
                .comment("Whether or not to enable any tweaks relating to dimensions and portals")
                .define("Interdimensional Tweaks", false);

        NETHER_PORTAL_BLOCK_TAG = builder.comment("Whether or not the tag " + Constants.NETHER_PORTAL_FRAME_TAG.location() + " function")
                .comment("This option outright replaces the predicate, meaning it could cause issues if other mods attempt to add different portal frame blocks")
                .comment("You can remove obsidian by replacing this tag with the datapack")
                .define("Nether Portal Frame tags", true);

        NETHER_PORTAL_DIMENSIONS_TAG = builder.comment("If set to true, any dimension in the dimension type tag " + Constants.SUPPORTS_NETHER_PORTALS_TAG.location() + " will be able to have Nether Portals constructed in it")
                .comment("Nether Portals in the Nether will still return you to the Overworld. Allowing creation of Nether Portals in non-Overworld dimensions by adding to the tag has the potential for sequence breaks and other weirdness and isn't recommended")
                .define("Nether Portal Dimensions", false);
    }
}
