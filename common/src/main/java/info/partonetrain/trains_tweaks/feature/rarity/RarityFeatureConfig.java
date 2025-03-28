package info.partonetrain.trains_tweaks.feature.rarity;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class RarityFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue DATA_TAG_ENABLED;
    public static ModConfigSpec.BooleanValue PREVENT_ENCHANTMENT_ALTERING;
    public static ModConfigSpec.BooleanValue RESTORE_DEFAULT;
    public static ModConfigSpec.BooleanValue ITEMSTACK_CHECK_TAG;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable rarity tweak")
                .define("Rarity tweak",true);

        DATA_TAG_ENABLED = builder.comment("If true, any item in one of the following item tags will automatically have its rarity set to that rarity (on item/item entity tick)")
                .comment(Constants.COMMON_TAG.location() + ", " + Constants.UNCOMMON_TAG.location() + ", " + Constants.RARE_TAG.location() + ", " + Constants.EPIC_TAG.location())
                .comment("These tags potentially have a performance impact, so they should be used sparingly")
                .define("Rarity Tagging", true);

        PREVENT_ENCHANTMENT_ALTERING = builder.comment("If true, enchantments cannot alter an ItemStack's rarity")
                .comment("This is recommended if Rarity Tagging is enabled, but is not required (in this case, enchanted & tagged items will have their rarity upgraded)")
                .define("Prevent Enchantment Altering", true);

        RESTORE_DEFAULT = builder.comment("If enabled, any item that isn't in one of the rarity tags that has had its rarity component modified (or removed) will automatically have its rarity be reset")
                .comment("Requires Rarity Tagging to be true, and completely ignores enchantments")
                .define("Restore Default Rarity", false);

        ITEMSTACK_CHECK_TAG = builder.comment("If enabled, ItemStacks will ONLY check for their presence in one of the rarity tags and ignore the rarity component AND enchantments entirely")
                .comment("Although it makes it seem more seamless, this is not recommended, because the stack won't actually be updated, and you will have to re-tag every item with non-Common rarity")
                .comment("You should disable Rarity Tagging, Prevent Enchantment Altering, and Restore Default Rarity when turning this on")
                .define("ItemStacks Check Tag", false);

    }
}
