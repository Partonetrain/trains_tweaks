package info.partonetrain.trains_tweaks.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Block.class)
public interface Quasi_BlockAccessor {
    @Invoker("registerDefaultState")
    void trains_tweaks$registerDefaultState(BlockState state);
}
