package info.partonetrain.trains_tweaks.feature.yeet;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class YeetFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue THROW_FIRE_CHARGES;
    public static ModConfigSpec.IntValue FIRE_CHARGES_COOLDOWN;
    public static ModConfigSpec.IntValue SNOWBALL_FREEZE_TICKS;
    public static ModConfigSpec.IntValue SNOWBALL_FREEZE_MAX;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable configs related to throwing things")
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
                .defineInRange("Snowball Max Freeze", 500, 40, 2000);

    }
}
