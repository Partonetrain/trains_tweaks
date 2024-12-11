package info.partonetrain.trains_tweaks.feature.ocelot;

import net.neoforged.neoforge.common.ModConfigSpec;

public class OcelotFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.ConfigValue<String> FORCE_TYPE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to make ocelots tameable into cats by making them trust you")
                .comment("Note that this does not work exactly like pre-1.14 to ensure the Two by Two advancement is possible")
                .define("Tame Ocelots",true);

        FORCE_TYPE = builder.comment("Force tamed ocelots to be a specific variant of cat")
                .comment("set to 'none' to disable")
                .comment("See https://minecraft.wiki/w/Cat#Entity_data for valid values")
                .define("Force Cat Variant", "none");
    }
}
