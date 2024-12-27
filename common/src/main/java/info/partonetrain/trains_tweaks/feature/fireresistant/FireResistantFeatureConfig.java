package info.partonetrain.trains_tweaks.feature.fireresistant;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class FireResistantFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable the functionality of the "+ Constants.FIRE_RESISTANT_TAG.location() + " and " + Constants.NOT_FIRE_RESISTANT_TAG.location() + " item tags")
                .define("Fire Resistant tweak",true);
    }
}
