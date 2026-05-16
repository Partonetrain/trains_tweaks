package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.interdimensional.InterdimensionalFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnder.class)
public class Interdimensional_EyeOfEnderMixin {

    @Shadow
    private boolean surviveAfterDeath;

    @Inject(method = "signalTo", at=@At("TAIL"))
    public void trains_tweaks$signalTo(BlockPos pos, CallbackInfo ci){
        if(!AllFeatures.INTERDIMENSIONAL_FEATURE.isIncompatibleLoaded() && InterdimensionalFeatureConfig.ENABLED.getAsBoolean()
                && InterdimensionalFeatureConfig.EYE_OF_ENDER_IMPLODE_CHANCE.getAsInt() != InterdimensionalFeatureConfig.EYE_OF_ENDER_IMPLODE_CHANCE.getDefault()){
            int cfg = InterdimensionalFeatureConfig.EYE_OF_ENDER_IMPLODE_CHANCE.getAsInt();
            if(cfg == 0) //prevent div by 0
            {
                surviveAfterDeath = true;
            }
            else
            {
                Entity self = (Entity)(Object)this;
                RandomSource random = self.getRandom();
                surviveAfterDeath = random.nextInt( 10/cfg) > 0;
                //2->5, 5->2, 10->1, etc
            }
        }
    }
}
