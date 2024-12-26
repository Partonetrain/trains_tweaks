package info.partonetrain.trains_tweaks.feature.attackspeed;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AttackSpeedEffects {

    //unregistered effects
    //registered effect holders are in AttackSpeedFeature
    public static final MobEffect d = (new AttackSpeedEffect(MobEffectCategory.BENEFICIAL, 14270531)).addAttributeModifier(Attributes.ATTACK_SPEED, Constants.DEXTERITY_EFFECT_ID, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    public static final MobEffect c = (new AttackSpeedEffect(MobEffectCategory.HARMFUL, 4866583)).addAttributeModifier(Attributes.ATTACK_SPEED, Constants.CLUMSY_EFFECT_ID, -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    //this is called from Fabric_AttackSpeed_MobEffectsMixin
    public static void fabricInit(){
        AttackSpeedFeature.DEXTERITY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Constants.DEXTERITY_EFFECT_ID, d);
        AttackSpeedFeature.CLUMSY = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Constants.CLUMSY_EFFECT_ID, c);
    }

    private static Holder<MobEffect> register(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.withDefaultNamespace(name), effect);
    }

    public static class AttackSpeedEffect extends MobEffect {

        protected AttackSpeedEffect(MobEffectCategory category, int color) {
            super(category, color);
        }

        // Utility method that is called when the effect is first added to the entity.
        // This does not get called again until all instances of this effect have been removed from the entity.
        @Override
        public void onEffectAdded(@NotNull LivingEntity entity, int amplifier) {
            super.onEffectAdded(entity, amplifier);
            if(entity instanceof Player p){
                p.resetAttackStrengthTicker();
            }
        }
    }
}
