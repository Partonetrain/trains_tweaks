package info.partonetrain.trains_tweaks.feature.kritz;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class KritzEffects {

    //unregistered effects
    //registered effect holders are in KritzFeature
    public static final MobEffect me = (new KritzEffect(MobEffectCategory.BENEFICIAL, 16715776)).addAttributeModifier(KritzFeature.MELEE_CRIT_CHANCE, Constants.MELEE_CRIT_EFFECT_ID, 0.1, AttributeModifier.Operation.ADD_VALUE);
    public static final MobEffect re = (new KritzEffect(MobEffectCategory.HARMFUL, 16715776)).addAttributeModifier(KritzFeature.RANGED_CRIT_CHANCE, Constants.RANGED_CRIT_EFFECT_ID, 0.1, AttributeModifier.Operation.ADD_VALUE);

    //this is called from Fabric_Kritz_MobEffectsMixin
    public static void fabricInit(){
        KritzFeature.MELEE_CRIT_EFFECT = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Constants.MELEE_CRIT_EFFECT_ID, me);
        KritzFeature.RANGED_CRIT_EFFECT = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Constants.RANGED_CRIT_EFFECT_ID, re);
    }

    public static class KritzEffect extends MobEffect {
        protected KritzEffect(MobEffectCategory category, int color) {
            super(category, color);
        }
    }
}
