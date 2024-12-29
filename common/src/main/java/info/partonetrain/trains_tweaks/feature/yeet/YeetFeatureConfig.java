package info.partonetrain.trains_tweaks.feature.yeet;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.ModConfigSpec;

public class YeetFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue THROW_FIRE_CHARGES;
    public static ModConfigSpec.IntValue FIRE_CHARGES_COOLDOWN;
    public static ModConfigSpec.IntValue SNOWBALL_FREEZE_TICKS;
    public static ModConfigSpec.IntValue SNOWBALL_FREEZE_MAX;
    public static ModConfigSpec.IntValue EXPERIENCE_BOTTLE_AMOUNT;
    public static ModConfigSpec.BooleanValue PREVENT_WIND_INTERACTIONS;
    public static ModConfigSpec.BooleanValue ANY_IMPACT_BREAKS_DRIPSTONE;
    public static ModConfigSpec.BooleanValue REFLECT_ANYTHING;
    public static ModConfigSpec.BooleanValue FIX_REDIRECTED_ARROWS; //MC-270834

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable tweaks related to throwing things")
                .define("Yeet Tweaks",true);

        THROW_FIRE_CHARGES = builder.comment("If enabled, you will be able to right-click the air with a fire charge to throw it")
                .comment("However, the projectile is inaccurate and unable to ignite blocks when spawned this way")
                .comment("Note: this re-uses the blaze attack sound event, so subtitles will be inaccurate")
                .define("Throw Fire Charges", true);

        FIRE_CHARGES_COOLDOWN = builder.comment("Cooldown in ticks between throwing fire charges")
                .defineInRange("Fire Charges Cooldown", 10, 0, 1200);

        SNOWBALL_FREEZE_TICKS = builder.comment("If > 0, snowballs inflict this many ticks of freeze when hitting an entity")
                .defineInRange("Snowball Freeze Ticks", 40, 0, 2000);

        SNOWBALL_FREEZE_MAX = builder.comment("The maximum amount of freeze ticks consecutive snowball projectiles can inflict")
                .defineInRange("Snowball Max Freeze", 500, 0, 2000);

        EXPERIENCE_BOTTLE_AMOUNT = builder.comment("The amount of experience points granted from thrown Bottles o' Enchanting")
                .comment("Set to -1 to use the vanilla value (random amount between 3 and 13)")
                .defineInRange("Experience Bottle Amount", 10, -1, 2477);

        PREVENT_WIND_INTERACTIONS = builder.comment("If true, Wind Charge explosions will not interact with blocks such as trapdoors and levers")
                .comment("It will still cause damage and knockback")
                .define("Prevent Wind Interactions", false);

        ANY_IMPACT_BREAKS_DRIPSTONE = builder.comment("If set to true, any projectile tagged with " + EntityTypeTags.IMPACT_PROJECTILES.location() + "breaks Pointed Dripstone")
                .comment("(as opposed to only Tridents when they have the tag)")
                .define("Any Impact Projectile Breaks Dripstone", false);

        REFLECT_ANYTHING = builder.comment("If set to true, any projectile can be reflected by hitting it with melee or another projectile, similar to Ghast Fireballs")
                .comment("This is a \"just for fun\" option")
                .comment("This removes the check for the " + EntityTypeTags.REDIRECTABLE_PROJECTILE.location() + " tag and effectively makes said tag useless")
                .define("Reflect Any Projectile", false);

        FIX_REDIRECTED_ARROWS = builder.comment("Fixes MC-270834: https://bugs.mojang.com/browse/MC-270834")
                .comment("If disabled and Reflect Any Projectile is enabled (or arrows/tridents are added to " + EntityTypeTags.REDIRECTABLE_PROJECTILE.location() + " via datapack), you will be able to pick up reflected arrows/tridents. Not recommended")
                .define("Fix Redirected Arrows", true);
    }
}
