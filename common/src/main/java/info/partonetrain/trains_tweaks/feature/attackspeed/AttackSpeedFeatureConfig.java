package info.partonetrain.trains_tweaks.feature.attackspeed;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.ModConfigSpec;

public class AttackSpeedFeatureConfig {
    public static ModConfigSpec.Builder builder;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue ENABLED;
    public static ModConfigSpec.BooleanValue FIX_EFFECTS;
    public static ModConfigSpec.BooleanValue ADD_EFFECTS;
    public static ModConfigSpec.DoubleValue FIXED_EFFECT_MODIFIER;
    public static ModConfigSpec.BooleanValue ADD_HASTE_TO_CONDUIT;
    public static ModConfigSpec.IntValue ELDER_GUARDIAN_FATIGUE_AMPLIFIER;

    static {
        builder = new ModConfigSpec.Builder();
        registerConfig(builder);
        SPEC = builder.build();
    }

    public static void registerConfig(ModConfigSpec.Builder builder) {

        ENABLED = builder.comment("Whether or not to enable attack speed tweaks")
                .define("Attack Speed tweaks",false);

        FIX_EFFECTS = builder.comment("If enabled, Haste and Mining Fatigue no longer modify the " + Attributes.ATTACK_SPEED.getRegisteredName() + " attribute")
                .comment("Instead, they properly affect the " + Attributes.BLOCK_BREAK_SPEED.getRegisteredName() + " attribute")
                .comment("The mining speed calculation will no longer consider the presence of these effects, only the attribute they modify")
                .comment("However, as a side effect, Conduit Power will no longer grant a Haste-like effect")
                .comment("This makes Haste and Mining Fatigue behave similar to Bedrock Edition ")
                .define("Fix Effects",true);

        ADD_EFFECTS = builder.comment("If enabled, the effects " + Constants.DEXTERITY_EFFECT_ID + " and " + Constants.CLUMSY_EFFECT_ID + " will be registered")
                .comment("There is no potion defined for these, and they are simply substitutes for the attack speed part of Haste/Fatigue that is disabled from Fix Effects")
                .comment("However, this does not explicitly require Fix Effects to be enabled")
                .define("Add Effects", true);

        FIXED_EFFECT_MODIFIER = builder.comment("If Fix Effects is enabled, this is the value of the Haste attribute modifier")
                .comment("Mining Fatigue's modifier will have the negative version of this value")
                .defineInRange("Fixed Effects Modifier", 0.25D, 0.1D, 1D);

        ADD_HASTE_TO_CONDUIT = builder.comment("If enabled, Haste will be granted by the Conduit in addition to Conduit Power")
                .comment("This makes up for the fact that Conduit Power no longer influences block break speed if Fix Effects is enabled")
                .define("Add Haste to Conduit", false);

        ELDER_GUARDIAN_FATIGUE_AMPLIFIER = builder.comment("If > 2, Elder Guardians will grant Mining Fatigue with this amplifier")
                .comment("This makes up for a potentially weaker Mining Fatigue effect from Fixed Effects being enabled")
                .defineInRange("Elder Guardian Fatigue Amplifier", 2, 2, 9);

    }
}
