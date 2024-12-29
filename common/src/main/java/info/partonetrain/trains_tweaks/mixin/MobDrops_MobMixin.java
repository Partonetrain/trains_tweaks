package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobDrops_MobMixin {
    //Arrays.fill(this.armorDropChances, 0.085F);
    @ModifyArg(method = "<init>", at= @At(value = "INVOKE", target = "Ljava/util/Arrays;fill([FF)V", ordinal = 0), index = 1)
    public float trains_tweaks$mobConstructor(float original){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() &&
                MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getAsDouble() != MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getDefault()) {
            Mob self = (Mob)(Object)this;

            return (float) MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getAsDouble();
        }
        return original;
    }

    //Arrays.fill(this.handDropChances, 0.085F);
    @ModifyArg(method = "<init>", at= @At(value = "INVOKE", target = "Ljava/util/Arrays;fill([FF)V", ordinal = 1), index = 1)
    public float trains_tweaks$mobConstructor2(float original){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() &&
                MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getAsDouble() != MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getDefault()) {
            Mob self = (Mob)(Object)this;

            return (float) MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getAsDouble();
        }
        return original;
    }

    //this.bodyArmorDropChance = 0.085F;
    @Inject(method = "<init>", at= @At(value = "TAIL"))
    public void trains_tweaks$mobConstructor3(EntityType entityType, Level level, CallbackInfo ci){
        Mob self = (Mob)(Object)this;
        self.setDropChance(EquipmentSlot.BODY, (float) MobDropsFeatureConfig.MOB_EQUIPMENT_DROP_CHANCE.getAsDouble());
    }
}
