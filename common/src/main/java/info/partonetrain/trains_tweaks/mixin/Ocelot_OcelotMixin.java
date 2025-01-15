package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.ocelot.OcelotFeature;
import info.partonetrain.trains_tweaks.feature.ocelot.OcelotFeatureConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ocelot.class)
public abstract class Ocelot_OcelotMixin extends Animal {
    protected Ocelot_OcelotMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "mobInteract", at = @At("RETURN"))
    private void trains_tweaks$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if(OcelotFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.OCELOT_FEATURE.isIncompatibleLoaded() && OcelotFeatureConfig.TAME.getAsBoolean()){
            Ocelot ocelot = (Ocelot)(Object)this;
            if(ocelot.isTrusting())
            {
                Level level = ocelot.level();

                if (!level.isClientSide){
                    Cat cat = ocelot.convertTo(EntityType.CAT, true);
                    cat.tame(player); //this sets owner UUID
                    cat.setOrderedToSit(true);
                    cat.setXRot(ocelot.getXRot());
                    //this doesn't seem to actually work, it always faces south
                    //but as far as I can tell this is only a rendering thing?

                    if(OcelotFeatureConfig.PRESERVE_SCALE.getAsBoolean()){
                        cat.getAttribute(Attributes.SCALE).addPermanentModifier(new AttributeModifier(Constants.OCELOT_SIZE_MODIFIER, 0.2, AttributeModifier.Operation.ADD_VALUE));
                    }

                    if(!OcelotFeatureConfig.FORCE_TYPE.get().equals("none")){
                        if(!OcelotFeature.variantParsed){
                            OcelotFeature.parseCatVariant();
                        }
                        cat.setVariant(OcelotFeature.parsedCatVariant);
                    }
                }
            }
        }
    }

    @Inject(method = "registerGoals", at=@At("TAIL"))
    public void trains_tweaks$registerGoals(CallbackInfo ci){
        if(OcelotFeatureConfig.ENABLED.getAsBoolean() && !AllFeatures.OCELOT_FEATURE.isIncompatibleLoaded() && OcelotFeatureConfig.HUNTER.getAsBoolean()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, false, OcelotFeature.TARGET));
        }
    }
}
