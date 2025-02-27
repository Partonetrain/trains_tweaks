package info.partonetrain.trains_tweaks.feature.xplosiv;

import net.neoforged.neoforge.common.ModConfigSpec;

public class XplosivFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue UNSTABLE_TNT;
    public static ModConfigSpec.BooleanValue INSTANT_TNT;
    public static ModConfigSpec.BooleanValue FIRE_EXPLOSIONS_IN_ULTRAWARM; //if true fire is placed only if level is ultrawarm
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
                .define("Xplosiv Tweaks",false);

        UNSTABLE_TNT = builder.comment("If enabled, all newly-placed TNT will be set to unstable")
                .comment("Unstable TNT is a vanilla blockstate that makes the TNT primed when broken by a non-Creative mode player")
                .define("Unstable TNT", false);

        INSTANT_TNT = builder.comment("If enabled, all newly-placed TNT will instantly be primed")
                .define("Instant TNT", false);

        FIRE_EXPLOSIONS_IN_ULTRAWARM = builder.comment("If set to true, all explosions in ultrawarm dimensions (like the Nether) will place fire")
                .comment("and explosions in non-ultrawarm dimensions will never place fire")
                .define("Ultrawarm Fire Explosions", false);

        builder.comment("\nAll of the following options default to the vanilla value");

        TNT_POWER = builder.comment("The explosion power that primed TNT explodes with")
                .defineInRange("TNT Power", 4, 0, 32);

        INTENTIONAL_GAME_DESIGN_POWER = builder.comment("The explosion power of bad respawn point explosions (Beds and Respawn Anchor in wrong dimension)")
                .defineInRange("Intentional Game Design Power", 5, 0, 32);

        END_CRYSTAL_POWER = builder.comment("The explosion power that End Crystals explode with")
                .defineInRange("End Crystal Power", 6, 0, 32);

        CREEPER_POWER = builder.comment("The explosion power that uncharged Creepers explode with")
                .defineInRange("Creeper Power", 3, 0, 32);

        CHARGED_CREEPER_POWER = builder.comment("The explosion power that charged Creepers explode with")
                .defineInRange("Charged Creeper Power", 6, 0, 32);

        GHAST_FIREBALL_POWER = builder.comment("The explosion power that Ghast Fireballs explode with")
                .defineInRange("Ghast Fireball Power", 1, 0, 32);

        WITHER_SPAWN_POWER = builder.comment("The explosion power of a spawned Wither")
                .defineInRange("Wither Spawn Power", 7, 0, 32);

        WITHER_SKULL_POWER = builder.comment("The explosion power of Wither Skull projectiles")
                .defineInRange("Wither Skull Power", 1, 0, 32);
    }
}
