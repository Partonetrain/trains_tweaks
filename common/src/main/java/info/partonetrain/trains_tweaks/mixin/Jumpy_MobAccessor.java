package info.partonetrain.trains_tweaks.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mob.class)
public interface Jumpy_MobAccessor {
    @Accessor("goalSelector")
    GoalSelector trains_tweaks$getMobGoalSelector();
}
