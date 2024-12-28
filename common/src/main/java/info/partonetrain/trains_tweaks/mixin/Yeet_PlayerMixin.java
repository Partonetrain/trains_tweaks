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

@Mixin(Player.class)
public class Yeet_PlayerMixin {
    //fun fact if REFLECT_ANYTHING is true, without this one you get disconnected for "Attempting to attack invalid entity"
    //I assume it's some kind of anti-cheat
    @WrapOperation(method = "attack", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityType;is(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean trains_tweaks$attack(EntityType instance, TagKey<EntityType<?>> tag, Operation<Boolean> original){
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean()
                && YeetFeatureConfig.REFLECT_ANYTHING.getAsBoolean()) {
            return true;
        }
        return original.call(instance, tag);
    }
}
