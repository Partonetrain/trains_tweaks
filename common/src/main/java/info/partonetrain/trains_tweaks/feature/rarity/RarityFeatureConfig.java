package info.partonetrain.trains_tweaks.feature.rarity;

import net.neoforged.neoforge.common.ModConfigSpec;

public class RarityFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue PREVENT_ENCHANTMENT_ALTERING;
    public static ModConfigSpec.BooleanValue DATA_TAG_ENABLED;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable rarity tweak")
                .define("Rarity tweak",true);

        PREVENT_ENCHANTMENT_ALTERING = builder.comment("If true, enchantments cannot alter an ItemStack's rarity")
                .define("Prevent Enchantment Altering", true);

        DATA_TAG_ENABLED = builder.comment("If true, any item in one of the following item tags will automatically have its rarity set to that rarity")
                .comment("trains_tweaks:common, trains_tweaks:uncommon, trains_tweaks:rare, trains_tweaks:epic")
                .define("Rarity Tagging", true);

    }
}
