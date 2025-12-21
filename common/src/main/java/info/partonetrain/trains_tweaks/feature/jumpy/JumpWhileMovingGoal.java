package info.partonetrain.trains_tweaks.feature.jumpy;

import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class JumpWhileMovingGoal extends Goal {
    private final Mob owner;
    private final LevelReader levelReader;

    public JumpWhileMovingGoal(Mob owner){
        this.owner = owner;
        levelReader = owner.level();
    }

    @Override
    public boolean canUse() {

        Vec3 lookAngle = owner.getLookAngle();
        Vec3 srcVec = new Vec3(owner.getX(), owner.getY(), owner.getZ());
        Vec3 lookVec = lookAngle.scale(2);
        Vec3 destVec = srcVec.add(lookVec);

        BlockPos blockInFont = new BlockPos((int) destVec.x, (int) destVec.y, (int) destVec.z);
        BlockState blockStateInFront = this.levelReader.getBlockState(blockInFont);
        if(blockStateInFront.isCollisionShapeFullBlock(levelReader, blockInFont)){
            if(JumpyFeatureConfig.SHOW_DEBUG.getAsBoolean() && owner.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, blockInFont.getX() + 0.5, blockInFont.getY() + 0.5, blockInFont.getZ() + 0.5, 1, 0, 0, 0, 0);
            }
        }
        else{
            if(JumpyFeatureConfig.SHOW_DEBUG.getAsBoolean() && owner.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.FLAME, blockInFont.getX() + 0.5, blockInFont.getY() + 0.5, blockInFont.getZ() + 0.5, 1, 0, 0, 0, 0);
            }
        }

        if(JumpyFeatureConfig.SHOW_DEBUG.getAsBoolean()){
            Constants.LOG.info("Moving Laterally:" + !(owner.getDeltaMovement().x() == 0 && owner.getDeltaMovement().z() == 0));
            Constants.LOG.info("DeltaMovement:" + String.valueOf(owner.getDeltaMovement()));
            Constants.LOG.info("destVec:" + String.valueOf(destVec));
            Constants.LOG.info("blockInFront:" + String.valueOf(blockInFont));
        }

        return !(owner.getDeltaMovement().x() == 0 && owner.getDeltaMovement().z() == 0);
    }

    @Override
    public boolean requiresUpdateEveryTick() { //runs every other tick
        return false;
    }

    @Override
    public void tick() {

        if(isSolidBlockInFront() && owner.getRandom().nextDouble() < JumpyFeatureConfig.JUMP_WHILE_MOVING_CHANCE.getAsDouble()){
            owner.getJumpControl().jump();
        }
    }

    public boolean isSolidBlockInFront(){
        Vec3 lookAngle = owner.getLookAngle();
        Vec3 srcVec = new Vec3(owner.getX(), owner.getY(), owner.getZ());
        Vec3 lookVec = lookAngle.scale(2);
        Vec3 destVec = srcVec.add(lookVec);

        BlockPos blockInFont = new BlockPos((int) destVec.x, (int) destVec.y, (int) destVec.z);
        BlockState blockStateInFront = this.levelReader.getBlockState(blockInFont);
        return blockStateInFront.isCollisionShapeFullBlock(levelReader, blockInFont);
    }


}