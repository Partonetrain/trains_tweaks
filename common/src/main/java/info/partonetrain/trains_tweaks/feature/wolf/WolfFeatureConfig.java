package info.partonetrain.trains_tweaks.feature.wolf;

import info.partonetrain.trains_tweaks.Constants;
import net.neoforged.neoforge.common.ModConfigSpec;

public class WolfFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue NERF_HEALTH;
    public static ModConfigSpec.BooleanValue NERF_ARMOR;
    public static ModConfigSpec.BooleanValue AVOIDS_ATTACKING_TAG;
    public static ModConfigSpec.BooleanValue SNOWY_WOLF_WALKS_ON_POWDER_SNOW;
    public static ModConfigSpec.BooleanValue DONT_SCARE_SKELETONS;
    public static ModConfigSpec.BooleanValue DONT_CHASE_SKELETONS;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable any of the tweaks relating to wolves")
                .define("Wolf Tweaks",true);

        NERF_HEALTH = builder.comment("If enabled, newly tamed wolves will have their max health set to 20 (instead of 40)")
                .define("Nerf Wolf Health", false);

        NERF_ARMOR = builder.comment("If enabled, wolf armor will not completely negate damage")
                .comment("Wolf armor will still grant 11 points of armor (which is actually useless in vanilla: https://bugs.mojang.com/browse/MC-268913)")
                .comment("As an upside, Wolf Armor will not take durability damage, acting like diamond horse armor")
                .define("Nerf Wolf Armor", false);

        AVOIDS_ATTACKING_TAG = builder.comment("If enabled, the hardcoded checks for creepers and ghasts in the tamed wolf attacking code")
                .comment("will be replaced with a check for entities in the tag " + Constants.WOLF_AVOIDS_TAG.location())
                .comment("This is especially useful if you have mods that add other exploding mobs")
                .define("Avoids Attacking Tag", true);

        SNOWY_WOLF_WALKS_ON_POWDER_SNOW = builder.comment("If enabled, the snowy wolf variant (the one that only spawns in Groves) can walk on powder snow")
                .define("Snowy Wolf Walks on Powder Snow", true);

        DONT_SCARE_SKELETONS = builder.comment("If enabled, wolves will not scare away skeletons")
                .comment("If Don't Chase Skeletons is disabled, this will usually result in wild wolves dying from skeleton retaliation")
                .define("Don't Scare Skeletons", true);

        DONT_CHASE_SKELETONS = builder.comment("If enabled, wolves will not attack skeletons unprovoked")
                .define("Don't Chase Skeletons", true);
    }
}
