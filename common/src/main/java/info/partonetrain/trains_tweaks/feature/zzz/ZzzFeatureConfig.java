package info.partonetrain.trains_tweaks.feature.zzz;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ZzzFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue SLEEP_THROUGH_DAMAGE;
    public static ModConfigSpec.BooleanValue SLEEP_HEALS;
    public static ModConfigSpec.BooleanValue SLEEP_REMOVES_EFFECTS;
    public static ModConfigSpec.IntValue SLEEP_REQUIRED_TICKS;
    public static ModConfigSpec.BooleanValue CREATIVE_INSTANT_SLEEP;
    public static ModConfigSpec.IntValue CLIENT_SLEEP_COLOR;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Enable tweaks relating to sleeping")
                .define("Sleeping Tweaks",true);

        SLEEP_HEALS = builder.comment("If set to true, sleeping fully heals players")
                .define("Sleeping Heals", true);

        SLEEP_REMOVES_EFFECTS = builder.comment("If set to true, sleeping removes (finite duration) harmful effects")
                .define("Sleeping Removes Debuffs", false);

        SLEEP_THROUGH_DAMAGE = builder.comment("If set to true, damage of a type in the " + Constants.SLEEP_THROUGH_DAMAGE_TAG.location() + " will not wake any sleeping entity up")
                .comment("By default this tag includes minecraft:magic (and neoforge:poison, if on neoforge)")
                .define("Sleep Through Tagged Damage", true);

        SLEEP_REQUIRED_TICKS = builder.comment("The number of ticks a player must in bed for before the server considers them asleep")
                .comment("This is 100 ticks in vanilla, and cannot be increased further than that")
                .defineInRange("Sleep Required Ticks", 60, 1, 100);

        CREATIVE_INSTANT_SLEEP = builder.comment("If true, the sleep timer will be skipped in creative mode")
                .define("Instant Sleep in Creative", true);

        CLIENT_SLEEP_COLOR = builder.comment("This value, represented as an ARGB int, is added to the sleeping overlay")
                .comment("This can be used to make the sleep overlay darker or a different color entirely")
                .comment("Use a site such as https://argb-int-calculator.netlify.app/ to calculate a value")
                .defineInRange("Client Sleep Color", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
