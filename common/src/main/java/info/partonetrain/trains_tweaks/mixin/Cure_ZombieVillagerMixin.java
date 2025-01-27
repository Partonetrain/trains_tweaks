package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.cure.CureFeature;
import info.partonetrain.trains_tweaks.feature.cure.CureFeatureConfig;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieVillager.class)
public class Cure_ZombieVillagerMixin extends Mob {
    protected Cure_ZombieVillagerMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void trains_tweaks$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!AllFeatures.CURE_FEATURE.isIncompatibleLoaded() && CureFeatureConfig.ENABLED.getAsBoolean()) {
            ZombieVillager self = (ZombieVillager) (Object) this;
            ItemStack itemStack = player.getItemInHand(hand);
            if (CureFeatureConfig.INSTANT_CURE_TAG_ENABLED.getAsBoolean() && itemStack.is(Constants.INSTANT_CURING_TAG)) {
                itemStack.consume(1, player);
                if (!self.level().isClientSide) {
                    if (CureFeatureConfig.INSTANT_CURE_GRANTS_ADVANCEMENT.getAsBoolean() && player instanceof ServerPlayer) {
                        self.conversionStarter = player.getUUID();
                    }
                    self.finishConversion((ServerLevel) self.level());
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else if ((CureFeatureConfig.CURING_ITEMS_TAG_ENABLED.getAsBoolean() && itemStack.is(Constants.CURING_TAG))
                    || !CureFeatureConfig.CURING_ITEMS_TAG_ENABLED.getAsBoolean() && itemStack.is(Items.GOLDEN_APPLE)) {
                if (!CureFeature.effectsParsed) {
                    CureFeature.parseWeakeningEffects();
                }
                boolean hasWeakingEffect = false;
                for (Holder.Reference<MobEffect> weakeningEffect : CureFeature.parsedWeakeningEffects) {
                    if (self.hasEffect(weakeningEffect)) {
                        hasWeakingEffect = true;
                        break;
                    }
                }
                if (hasWeakingEffect) {
                    itemStack.consume(1, player);
                    if (!self.level().isClientSide) {
                        self.startConverting(player.getUUID(), self.getRandom().nextInt(2401) + 3600);
                    }

                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
            cir.setReturnValue(super.mobInteract(player, hand));
        }
    }

    @WrapOperation(method = "getConversionProgress", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
    public boolean trains_tweaks$getConversionProgress(BlockState instance, Block block, Operation<Boolean> original){
        if(!AllFeatures.CURE_FEATURE.isIncompatibleLoaded() && CureFeatureConfig.ENABLED.getAsBoolean() && CureFeatureConfig.USE_SPEEDUP_TAG.getAsBoolean()){
            return instance.is(Constants.SPEEDS_UP_CURE_TAG);
        }
        return original.call(instance, block);
    }

    @WrapOperation(method = "getConversionProgress", constant = @Constant(classValue = BedBlock.class) )
    public boolean trains_tweaks$getConversionProgress2(Object obj, Operation<Boolean> original){
        Block instance = (Block) obj;
        if(!AllFeatures.CURE_FEATURE.isIncompatibleLoaded() && CureFeatureConfig.ENABLED.getAsBoolean() && CureFeatureConfig.USE_SPEEDUP_TAG.getAsBoolean()){
            return instance.defaultBlockState().is(Constants.SPEEDS_UP_CURE_TAG);
            //defaultBlockState may cause some weirdness in specific circumstances
        }
        return original.call(obj);
    }
}
