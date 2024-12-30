package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Mob.class)
public abstract class SpawnsWith_MobMixin {

    @Inject(method="populateDefaultEquipmentSlots", at=@At("HEAD"), cancellable = true)
    private void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean()){
            if(SpawnsWithFeatureConfig.GENERIC_MOB_TABLES.getAsBoolean()){
                Mob self = (Mob)(Object)this;
                Map<EquipmentSlot, Float> dropChanceMap = SpawnsWithFeature.createDropChanceMap();
                EquipmentTable equipmentTable = new EquipmentTable(Constants.GENERIC_EQUIPMENT_LOOT_TABLE, dropChanceMap);
                self.equip(equipmentTable);
                ci.cancel();
            }
        }
    }

    //implement local difficulty -> luck
    @ModifyReturnValue(method = "createEquipmentParams", at=@At("RETURN"))
    private LootParams trains_tweaks$createEquipmentParams(LootParams original){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean() && SpawnsWithFeatureConfig.GENERIC_MOB_TABLES.getAsBoolean()) {
            //prevent worldgen hang (?)
            if(original.getLevel() instanceof ServerLevel){
                return original;
            }

            Mob self = (Mob) (Object) this;
            LootParams.Builder builder = new LootParams.Builder(original.getLevel());
            builder.withLuck(original.getLevel().getCurrentDifficultyAt(self.getOnPos()).getEffectiveDifficulty())
                    .withParameter(LootContextParams.ORIGIN, original.getParameter(LootContextParams.ORIGIN))
                    .withParameter(LootContextParams.THIS_ENTITY, original.getParameter(LootContextParams.THIS_ENTITY));
            return builder.create(LootContextParamSets.EQUIPMENT);
        }
        return original;
    }
}
