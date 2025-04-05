package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.spawnswith.EquipType;
import info.partonetrain.trains_tweaks.feature.spawnswith.EquipmentTableType;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(LivingEntity.class)
public class SpawnsWith_LivingEntityMixin {
    @Inject(method = "tick", at=@At("RETURN"))
    public void trains_tweaks$tick(CallbackInfo ci){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean()){
            LivingEntity self = (LivingEntity)(Object)(this);
            if(!SpawnsWithFeature.isEntityChecked(self) && self.level() instanceof ServerLevel serverLevel){
                if(SpawnsWithFeatureConfig.GENERIC_MOB_TABLES.getAsBoolean() && (self.getType().is(Constants.ROLLS_GENERIC_EQUIPMENT))){
                    SpawnsWithFeature.rollGenericTable(self);
                }

                Map<EquipmentTableType, ResourceKey<LootTable>> map = SpawnsWithFeature.findLootTables(serverLevel, self);
                if(map != null){
                    SpawnsWithFeature.rollSpecificTable(self, map);
                }

                if(!SpawnsWithFeature.markEntityChecked(self)){
                    Constants.LOG.error(self.getName().getString() + " had too many tags and could not be marked as " + SpawnsWithFeature.CHECKED_TAG);
                }
            }
        }
    }
}
