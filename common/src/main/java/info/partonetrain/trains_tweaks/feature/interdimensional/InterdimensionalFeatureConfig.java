package info.partonetrain.trains_tweaks.feature.interdimensional;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class InterdimensionalFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue NETHER_PORTAL_BLOCK_TAG;
    public static ModConfigSpec.BooleanValue NETHER_PORTAL_DIMENSIONS_TAG;
    public static ModConfigSpec.IntValue EYE_OF_ENDER_IMPLODE_CHANCE;
    public static ModConfigSpec.BooleanValue ENABLE_EFFECT_RESTRICTIONS;
    public static ModConfigSpec.ConfigValue<String> EFFECT_RESTRICTIONS;
    public static ModConfigSpec.ConfigValue<String> EFFECT_RESTRICTION_MESSAGE;

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
                .comment("You can remove obsidian by replacing this tag via datapack")
                .define("Nether Portal Frame tags", true);

        NETHER_PORTAL_DIMENSIONS_TAG = builder.comment("If set to true, any dimension in the dimension type tag " + Constants.SUPPORTS_NETHER_PORTALS_TAG.location() + " will be able to have Nether Portals constructed in it")
                .comment("Nether Portals in the Nether will still return you to the Overworld. Allowing creation of Nether Portals in non-Overworld dimensions by adding to the tag has the potential for sequence breaks and other weirdness and isn't recommended")
                .define("Nether Portal Dimensions", false);

        EYE_OF_ENDER_IMPLODE_CHANCE = builder.comment("The chance that a thrown Eye of Ender will implode/shatter and not drop its item")
                .comment("1 = 10% chance, 2 = 20% chance, etc. Leave as default to use the vanilla chance")
                .defineInRange("Eye of Ender Implode Chance", 2, 0, 10);

        ENABLE_EFFECT_RESTRICTIONS = builder.comment("If true, effects defined in Effect Restrictions will be removed from players that do not yet have the specified advancement")
                .comment("If you want to ban an effect in a dimension entirely, create an impossible advancement")
                .define("Enable Effect Restrictions", false);

        EFFECT_RESTRICTIONS = builder.comment("Format is effectid,dimensionid,unlockingAdvancementId;effectid,dimensionid,unlockingAdvancementId;")
                .define("Effect Restrictions", "minecraft:fire_resistance,minecraft:the_nether,minecraft:nether/get_wither_skull;");

        EFFECT_RESTRICTION_MESSAGE = builder.comment("The message sent to players when an effect is removed due to a restriction")
                .comment("You can use the placeholders $EFFECT, $DIMENSION, and $ADVANCEMENT, but they must not be the very first or very last part of the string")
                .comment("Set to blank for no message")
                .define("Effect Restriction Message", "You cannot have the $EFFECT effect in $DIMENSION until you unlock the advancement $ADVANCEMENT.");

    }
}
