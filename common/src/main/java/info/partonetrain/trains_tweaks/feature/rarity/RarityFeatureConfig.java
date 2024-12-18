package info.partonetrain.trains_tweaks.feature.rarity;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class RarityFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue PREVENT_ENCHANTMENT_ALTERING;
    public static ModConfigSpec.BooleanValue DATA_TAG_ENABLED;
    public static ModConfigSpec.BooleanValue RESTORE_DEFAULT;

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
                .comment(Constants.COMMON_TAG.location() + ", " + Constants.UNCOMMON_TAG.location() + ", " + Constants.RARE_TAG.location() + ", " + Constants.EPIC_TAG.location())
                .define("Rarity Tagging", true);

        RESTORE_DEFAULT = builder.comment("If enabled, any item that isn't in one of the rarity tags that has had its rarity component modified (or removed) will automatically have its rarity be reset")
                .comment("Requires Rarity Tagging to be true")
                .define("Restore Default Rarity", false);

    }
}
