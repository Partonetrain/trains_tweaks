package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.ocelot.OcelotFeature;
import info.partonetrain.trains_tweaks.feature.ocelot.OcelotFeatureConfig;
import info.partonetrain.trains_tweaks.feature.wolf.WolfFeatureConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class Wolf_WolfMixin extends Animal {
    protected Wolf_WolfMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private static final AttributeModifier ARMOR_MODIFIER = new AttributeModifier(
            Constants.WOLF_ARMOR_MODIFIER, 11, AttributeModifier.Operation.ADD_VALUE
    );

    @Unique
    public boolean trains_tweaks$checkTag(LivingEntity livingEntity){
        Constants.LOG.info("Wolf evaluating " + livingEntity.getName().getString() + ": " + livingEntity.getType().is(Constants.WOLF_AVOIDS_TAG));
        return livingEntity.getType().is(Constants.WOLF_AVOIDS_TAG);

    }

    @ModifyArg(method = "applyTamingSideEffects()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;setBaseValue(D)V"), index = 0)
    private double trains_tweaks$applyTamingSideEffects(double baseValue) {
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.NERF_HEALTH.getAsBoolean()){
            return 20.0;
        }
        return baseValue;
    }

    @ModifyArg(method = "applyTamingSideEffects()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Wolf;setHealth(F)V"))
    private float trains_tweaks$applyTamingSideEffects2(float original) {
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.NERF_HEALTH.getAsBoolean()){
            return 20.0F;
        }
        return original;
    }

    @Inject(method = "hurtArmor", at=@At(value = "HEAD"), cancellable = true)
    private void trains_tweaks$hurtArmor(DamageSource damageSource, float damageAmount, CallbackInfo ci){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.NERF_ARMOR.getAsBoolean()){
            ci.cancel();
        }
    }

    @Inject(method = "canArmorAbsorb", at=@At(value = "HEAD"), cancellable = true)
    private void trains_tweaks$canArmorAbsorb(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.NERF_ARMOR.getAsBoolean()){
            cir.setReturnValue(false);
        }
    }

    /*
    * Turns out these are unnecessary - wolf armor does actually add 11 points of armor attribute,
    * even though an armored wolf doesn't use its armor attribute?
    * https://bugs.mojang.com/browse/MC-268913
    @Inject(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Wolf;setBodyArmorItem(Lnet/minecraft/world/item/ItemStack;)V", ordinal = 0))
    private void trains_tweaks$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.NERF_ARMOR.getAsBoolean()){
            if(this.getAttribute(Attributes.ARMOR) != null){
                this.getAttribute(Attributes.ARMOR).addPermanentModifier(ARMOR_MODIFIER);
            }
            else{
                Constants.LOG.info("Wolf has no armor attribute");
            }
        }
    }

    @Inject(method = "mobInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Wolf;setBodyArmorItem(Lnet/minecraft/world/item/ItemStack;)V", ordinal = 1))
    private void trains_tweaks$mobInteract2(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.NERF_ARMOR.getAsBoolean()){
            if(this.getAttribute(Attributes.ARMOR) != null){
                this.getAttribute(Attributes.ARMOR).removeModifier(ARMOR_MODIFIER);
            }
            else{
                Constants.LOG.info("Wolf has no armor attribute");
            }
        }
    }
    *
     */

    @WrapOperation(method = "wantsToAttack", constant = {@Constant(classValue = Creeper.class), @Constant(classValue =  Ghast.class), @Constant(classValue =  ArmorStand.class)})
    private boolean trains_tweaks$wantsToAttack(Object obj, Operation<Boolean> original){
        if(WolfFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.WOLF_FEATURE.isIncompatibleLoaded() && WolfFeatureConfig.AVOIDS_ATTACKING_TAG.getAsBoolean()) {
            return trains_tweaks$checkTag((LivingEntity) obj);
        }
        return original.call(obj);
    }

}
