package info.partonetrain.trains_tweaks.feature.cure;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CureFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.ConfigValue<String> WEAKENING_EFFECTS;
    public static ModConfigSpec.BooleanValue CURING_ITEMS_TAG_ENABLED;
    public static ModConfigSpec.BooleanValue INSTANT_CURE_TAG_ENABLED;
    public static ModConfigSpec.BooleanValue INSTANT_CURE_GRANTS_ADVANCEMENT;
    public static ModConfigSpec.BooleanValue USE_SPEEDUP_TAG;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the options in this tweak")
                .define("Cure tweaks",true);

        WEAKENING_EFFECTS = builder.comment("A list of potion effects that act like weakness in Zombie Villager curing, separated by a comma (no spaces)")
                .define("Weakening Effects", "minecraft:weakness");

        CURING_ITEMS_TAG_ENABLED = builder.comment("Whether or not items in the " + Constants.CURING_TAG.location() + " tag should be used to cure weakened Zombie Villagers instead of hardcoded Golden Apples")
                .define("Use Curing Tag", true);

        INSTANT_CURE_TAG_ENABLED = builder.comment("Whether or not items in the " + Constants.INSTANT_CURING_TAG.location() + " tag should be used to cure Zombie Villagers instantly without an effect")
                .define("Use Instant Curing Tag", true);

        INSTANT_CURE_GRANTS_ADVANCEMENT = builder.comment("If enabled, Instant Cures will grant the vanilla Zombie Doctor advancement")
                .comment("Note that this makes the advancement have a slightly inaccurate description")
                .define("Instant Cure Advancement", true);

        USE_SPEEDUP_TAG = builder.comment("If enabled, the hardcoded checks for Iron Bars and Bed blocks around a Zombie Villager being cured")
                .comment("will be replaced with a check for the block tag " + Constants.SPEEDS_UP_CURE_TAG.location())
                .comment("Iron Bars and Beds are in this tag by default")
                .define("Use Speeds Up Cure Block Tag", true);
    }
}
