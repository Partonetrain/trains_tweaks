package info.partonetrain.trains_tweaks.feature.kritz;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.ModConfigSpec;

public class KritzFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue ADD_ATTRIBUTES;
    public static ModConfigSpec.BooleanValue ADD_EFFECTS;
    public static ModConfigSpec.DoubleValue KRIT_MULTIPLIER;
    public static ModConfigSpec.DoubleValue KRIT_CHANCE;
    
    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable critical hit tweaks (this feature disables itself if Apothic.Zenith Attributes or Critical Strike is installed)")
                .comment("\"JERMA THE KRITZ!!! JERMA!!!\"")
                .define("Critical Hit tweaks",false);

        ADD_ATTRIBUTES = builder.comment("If enabled, the attributes " + Constants.MELEE_CRIT_ATTRIBUTE_ID + " and " + Constants.RANGED_CRIT_ATTRIBUTE_ID + " will be registered,")
                .comment("applied to players, and the vanilla critical hit mechanics will be disabled")
                .define("Add Attributes", true);

        ADD_EFFECTS = builder.comment("If enabled, the effects " + Constants.MELEE_CRIT_EFFECT_ID + " and " + Constants.RANGED_CRIT_EFFECT_ID + " will be registered")
                .comment("They both increase the relevant attribute by 0.1, and thus require Add Attributes to be enabled")
                .define("Add Effects", true);

        KRIT_MULTIPLIER = builder.comment("The multiplier applied to the damage of melee critical hits")
                .comment("This will work with ranged crits too if Normalize Ranged Crits from the Yeet feature is enabled")
                .defineInRange("Crit Multiplier", 1.5D, 1.0D, 5.0D);

        KRIT_CHANCE = builder.comment("The base value of both the " + Constants.MELEE_CRIT_ATTRIBUTE_ID + " and " + Constants.RANGED_CRIT_ATTRIBUTE_ID + " attributes")
                .comment("Default value is 1/16")
                .defineInRange("Crit Chance", 0.0625, 0, 1);
    }
}
