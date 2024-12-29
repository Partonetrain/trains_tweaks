package info.partonetrain.trains_tweaks.feature.ocelot;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.ModConfigSpec;

public class OcelotFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue TAME;
    public static ModConfigSpec.BooleanValue PRESERVE_SCALE;
    public static ModConfigSpec.BooleanValue HUNTER;
    public static ModConfigSpec.ConfigValue<String> FORCE_TYPE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable ocelot tweaks")
                .define("Ocelot Tweaks",true);

        TAME = builder.comment("Whether or not to make ocelots tameable into cats by making them trust you")
                .comment("Note that this does not work exactly like pre-1.14 to ensure the Two by Two advancement is possible")
                .define("Tame Ocelots",true);

        PRESERVE_SCALE = builder.comment("If true, cats that were tamed from ocelots will preserve the size of an ocelot (Cats are usually 80% the size of ocelots)")
                .comment("This uses the " + Attributes.SCALE.getRegisteredName() +" attribute so it has the side effect of increasing the hitbox slightly")
                .define("Preserve Ocelot Scale", false);

        FORCE_TYPE = builder.comment("Force tamed ocelots to be a specific variant of cat")
                .comment("set to 'none' to disable")
                .comment("See https://minecraft.wiki/w/Cat#Entity_data for valid values")
                .define("Force Cat Variant", "none");

        HUNTER = builder.comment("If true, ocelots will attempt to hunt fish and rabbits in addition to chickens and baby turtles")
                .comment("This is to make them more interesting / give them a use in farms")
                .comment("Too add more mobs, use the tag " + Constants.OCELOT_HUNT_TARGETS.location())
                .define("Ocelots Hunt", true);
    }
}
