package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlock.class)
public class Xplosiv_BedBlockMixin {

    @ModifyArg(method = "useWithoutItem", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;Lnet/minecraft/world/phys/Vec3;FZLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"), index = 4)
    public float trains_tweaks$useWithoutItem(float original) {
        if (!AllFeatures.XPLOSIV_FEATURE.isIncompatibleLoaded() && XplosivFeatureConfig.ENABLED.getAsBoolean() && XplosivFeatureConfig.INTENTIONAL_GAME_DESIGN_POWER.getAsInt() != XplosivFeatureConfig.INTENTIONAL_GAME_DESIGN_POWER.getDefault()){
            return XplosivFeatureConfig.INTENTIONAL_GAME_DESIGN_POWER.getAsInt();
        }
        return original;
    }

}
