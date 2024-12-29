package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.vehicle.VehicleFeatureConfig;
import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;

import java.util.Iterator;

@Mixin(Boat.class)
public abstract class Vehicle_BoatMixin extends Entity {
    public Vehicle_BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method = "getGroundFriction", constant = @Constant(classValue =  WaterlilyBlock.class))
    private boolean trains_tweaks$instanceof(Object obj, Operation<Boolean> original, @Local BlockPos.MutableBlockPos blockpos$mutableblockpos){
        if (!AllFeatures.VEHICLE_FEATURE.isIncompatibleLoaded() && VehicleFeatureConfig.ENABLED.getAsBoolean() && VehicleFeatureConfig.BETTER_BOAT_BREAKING.getAsBoolean()) {
            Boat self = (Boat)(Object)this;
            BlockState blockstate = self.level().getBlockState(blockpos$mutableblockpos);
            return blockstate.is(Constants.BOAT_BREAKS_TAG);
        }
        return original.call(obj);
    }

    @Unique
    @Override
    protected void onInsideBlock(BlockState state) {
        if (!AllFeatures.VEHICLE_FEATURE.isIncompatibleLoaded() && VehicleFeatureConfig.ENABLED.getAsBoolean() && VehicleFeatureConfig.BETTER_BOAT_BREAKING.getAsBoolean()) {
            Boat self = (Boat)(Object)this;
            if(!self.getPassengers().isEmpty()){
                int width = Mth.floor(self.getBbWidth() / 2.0F + 1.0F);
                int height = Mth.floor(self.getBbHeight());
                Iterator<BlockPos> blockPosIterator = BlockPos.betweenClosed(this.getBlockX() - width, this.getBlockY() - height, this.getBlockZ() - width, this.getBlockX() + width, this.getBlockY() + 1, this.getBlockZ() + width).iterator();

                for (Iterator<BlockPos> i = blockPosIterator; i.hasNext(); ) {
                    BlockPos pos = i.next();
                    BlockState blockState = self.level().getBlockState(pos);
                    if(Services.PLATFORM.isDevelopmentEnvironment()) {
                        //indicate block breaking field
                        this.level().addParticle(ParticleTypes.BUBBLE_POP, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
                    }
                    if(blockState.is(Constants.BOAT_BREAKS_TAG)) {
                        self.level().destroyBlock(pos, true, this);
                    };
                }
            }
        }
    }
}
