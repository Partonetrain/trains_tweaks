package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.spawnswith.EquipType;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Pillager.class)
public class SpawnsWith_PillagerMixin {
    @Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"), cancellable = true)
    public void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        if (!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean() && SpawnsWithFeatureConfig.APPLY_TO_PILLAGER_SPAWN.getAsBoolean()) {
            Pillager self = (Pillager)(Object)this;

            List<ItemStack> loot = SpawnsWithFeature.getEquipmentFromLootTableForSpecificMob(self, Constants.PILLAGER_SPAWN_LOOT_TABLE);
            SpawnsWithFeature.equipMob(loot, self, EquipType.BOTH_HANDS);

            ci.cancel();
        }
    }
}
