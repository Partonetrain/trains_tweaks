package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class Yeet_AbstractArrowMixin {

    @Shadow
    public AbstractArrow.Pickup pickup;

    @Unique
    AbstractArrow.Pickup trains_tweaks$previousPickup;

    @Inject(method = "setOwner", at= @At(value = "HEAD"))
    public void trains_tweaks$setOwner(Entity entity, CallbackInfo ci){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.FIX_REDIRECTED_ARROWS.getAsBoolean()) {
            AbstractArrow thisArrow = (AbstractArrow)(Object)this;
            if(thisArrow.getOwner() instanceof Monster){
                trains_tweaks$previousPickup = pickup;
            }
        }
    }

    @Inject(method = "setOwner", at= @At(value = "TAIL"))
    public void trains_tweaks$setOwner2(Entity entity, CallbackInfo ci){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.FIX_REDIRECTED_ARROWS.getAsBoolean()) {
            AbstractArrow thisArrow = (AbstractArrow)(Object)this;
            if(thisArrow.getOwner() instanceof Player) {
                if(trains_tweaks$previousPickup != null){
                    pickup = trains_tweaks$previousPickup;
                }
            }
        }
    }

    @WrapOperation(method = "isAttackable", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean trains_tweaks$isAttackable(EntityType instance, TagKey<EntityType<?>> tag, Operation<Boolean> original){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.REFLECT_ANYTHING.getAsBoolean()) {
            return true;
        }
        return original.call(instance, tag);
    }
}
