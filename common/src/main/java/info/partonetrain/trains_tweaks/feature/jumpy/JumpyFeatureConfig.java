package info.partonetrain.trains_tweaks.feature.jumpy;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class JumpyFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue FIX_JUMPBOOST;
    public static ModConfigSpec.DoubleValue JUMPBOOST_ATTRIBUTE_ADD;
    public static ModConfigSpec.BooleanValue SHOW_DEBUG;
    public static ModConfigSpec.BooleanValue JUMP_WHILE_MOVING;
    public static ModConfigSpec.IntValue JUMP_WHILE_MOVING_PRIORITY;
    public static ModConfigSpec.DoubleValue JUMP_WHILE_MOVING_CHANCE;
    public static ModConfigSpec.BooleanValue JUMP_RANDOMLY;
    public static ModConfigSpec.IntValue JUMP_RANDOMLY_PRIORITY;
    public static ModConfigSpec.DoubleValue JUMP_RANDOMLY_CHANCE;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable tweaks relating to jumping")
                .define("Jumpy tweaks",false);

        FIX_JUMPBOOST = builder.comment("If true, the vanilla Jump Boost effect will have an additional " + Attributes.JUMP_STRENGTH.getRegisteredName() + " attribute modifier,")
                .comment("and the hardcoded checks for the effect in LocalPlayer and LivingEntity will be disabled")
                .comment("This makes the effect work the same across the player and other mobs, and makes it work more consistently with other effects.")
                .comment("However, modded mobs may still have a hardcoded check for the effect.")
                .comment("Additionally, keep in mind the auto-jump feature does NOT account for the " + Attributes.JUMP_STRENGTH.getRegisteredName() + " attribute, which is a Minecraft bug: MC-279903")
                .define("Jump Boost fix",false);

        JUMPBOOST_ATTRIBUTE_ADD = builder.comment("If Jump Boost fix is enabled, the amount added to the " + Attributes.JUMP_STRENGTH.getRegisteredName() + " attribute per level of the Jump Boost effect")
                .defineInRange("Jump Boost Bonus Per Level", 0.1, 0.01, 10f);

        JUMP_WHILE_MOVING = builder.comment("Whether or not mobs in the " + Constants.JUMPS_WHILE_MOVING_TAG.location() + " tag will jump while moving laterally AND there is a block in front of it")
                .comment("This replicates 2011-era behavior")
                .define("Mobs Jump While Moving",true);

        JUMP_WHILE_MOVING_PRIORITY = builder.comment("The priority of the goal for jumping while moving. This should be low")
                .defineInRange("Jump While Moving Priority", 1, 0, 100);

        JUMP_WHILE_MOVING_CHANCE = builder.comment("The chance that a mob will jump if the above conditions are met. 1.0 = 100%, 0.5 = 50%")
                .defineInRange("Jump While Moving Chance", 1.0, 0.0, 1.0);

        JUMP_RANDOMLY = builder.comment("Whether or not mobs in the " + Constants.JUMPS_RANDOMLY_TAG.location() + " tag will jump randomly")
                .define("Mobs Jump Randomly", false);

        JUMP_RANDOMLY_PRIORITY = builder.comment("The priority of the goal for jumping randomly. This should be low")
                .defineInRange("Jump Randomly Priority", 1, 0, 100);

        JUMP_RANDOMLY_CHANCE = builder.comment("The chance that a mob will jump randomly. 1.0 = 100%, 0.5 = 50%")
                .defineInRange("Jump Randomly Chance", 1.0, 0.0, 1.0);

        SHOW_DEBUG = builder.comment("Whether or not to show debugging particles and logs for the AI goals")
                .define("Mob Jump Debug",false);
    }
}
