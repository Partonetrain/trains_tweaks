package info.partonetrain.trains_tweaks.feature.xplosiv;

import net.neoforged.neoforge.common.ModConfigSpec;

public class XplosivFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue UNSTABLE_TNT;
    public static ModConfigSpec.BooleanValue INSTANT_TNT;
    public static ModConfigSpec.IntValue TNT_POWER;
    public static ModConfigSpec.IntValue INTENTIONAL_GAME_DESIGN_POWER; //beds and respawn anchors
    public static ModConfigSpec.IntValue END_CRYSTAL_POWER;
    public static ModConfigSpec.IntValue CREEPER_POWER;
    public static ModConfigSpec.IntValue CHARGED_CREEPER_POWER;
    public static ModConfigSpec.IntValue GHAST_FIREBALL_POWER;
    public static ModConfigSpec.IntValue WITHER_SPAWN_POWER;
    public static ModConfigSpec.IntValue WITHER_SKULL_POWER;


    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable tweaks related to explosions and explosives")
                .define("Xplosiv Tweaks",true);

        UNSTABLE_TNT = builder.comment("If enabled, all newly-placed TNT will be set to unstable")
                .comment("Unstable TNT is a vanilla blockstate that makes the TNT primed when broken by a non-Creative mode player")
                .define("Unstable TNT", false);

        INSTANT_TNT = builder.comment("If enabled, all newly-placed TNT will instantly be primed")
                .define("Instant TNT", false);

        TNT_POWER = builder.comment("The explosion power that primed TNT explodes with")
                .comment("Leave as default for the vanilla value")
                .defineInRange("TNT Power", 4, 0, 100);
    }
}
