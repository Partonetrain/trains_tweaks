package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.goat.GoatFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Goat.class)
public abstract class Goat_GoatMixin extends LivingEntity {
    protected Goat_GoatMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow public abstract boolean hasLeftHorn();

    @Shadow public abstract boolean hasRightHorn();

    @ModifyExpressionValue(method = "finalizeSpawn", at = @At(value = "CONSTANT", args = "doubleValue=0.02"))
    public double trains_tweaks$finalizeSpawn(double original) {
        if (!AllFeatures.GOAT_FEATURE.isIncompatibleLoaded() && GoatFeatureConfig.ENABLED.getAsBoolean() && GoatFeatureConfig.SCREAMING_GOAT_SPAWN_CHANCE.getAsDouble() != GoatFeatureConfig.SCREAMING_GOAT_SPAWN_CHANCE.getDefault()) {
            return GoatFeatureConfig.SCREAMING_GOAT_SPAWN_CHANCE.getAsDouble();
        }
        return original;
    }

    @ModifyExpressionValue(method = "finalizeSpawn", at = @At(value = "CONSTANT", args = "doubleValue=0.10000000149011612"))
    public double trains_tweaks$finalizeSpawn2(double original) {
        if (!AllFeatures.GOAT_FEATURE.isIncompatibleLoaded() && GoatFeatureConfig.ENABLED.getAsBoolean() && GoatFeatureConfig.ONE_HORN_GOAT_CHANCE.getAsDouble() != GoatFeatureConfig.ONE_HORN_GOAT_CHANCE.getDefault()) {
            return GoatFeatureConfig.ONE_HORN_GOAT_CHANCE.getAsDouble();
        }
        return original;
    }

    @Inject(method = "mobInteract", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        if (!AllFeatures.GOAT_FEATURE.isIncompatibleLoaded() && GoatFeatureConfig.ENABLED.getAsBoolean() && GoatFeatureConfig.SHEAR_HORNS.getAsBoolean()) {
            Goat self = (Goat) (Object) this;
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.is(Constants.SHEARS_TAG) || itemstack.is(Constants.SHEAR_TAG)) {
                if (!self.level().isClientSide && (this.hasLeftHorn() || hasRightHorn())) {
                    //self.hurt(self.damageSources().playerAttack(player), 2);
                    self.setHealth(self.getHealth() - 2); //just set health instead of getting hurt so fleeing doesn't interrupt the revenge ram
                    self.dropHorn(); //this makes the resultant item entity have velocity but that's funny so I kept it
                    self.gameEvent(GameEvent.SHEAR, player);
                    self.playSound(self.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_HORN_BREAK : SoundEvents.GOAT_HORN_BREAK);
                    itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    self.setTarget(player);
                    self.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, 0);
                    self.getBrain().stopAll((ServerLevel) self.level(), self);
                    self.getBrain().setActiveActivityIfPossible(Activity.RAM);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                    //it's possible for another mob to get in the way of it hitting you, but that's fine.
                } else {
                    cir.setReturnValue(InteractionResult.CONSUME);
                }
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit) {
        if (!AllFeatures.GOAT_FEATURE.isIncompatibleLoaded() && GoatFeatureConfig.ENABLED.getAsBoolean() && GoatFeatureConfig.SPAWN_HORNS_ON_DEATH.getAsDouble() > 0.0) {
            Goat self = (Goat) (Object) this;
            if(self.getRandom().nextDouble() < GoatFeatureConfig.SPAWN_HORNS_ON_DEATH.getAsDouble()){
                self.dropHorn();
                if(self.hasRightHorn() || self.hasLeftHorn()){
                    self.dropHorn();
                }
            }
        }
    }
}
