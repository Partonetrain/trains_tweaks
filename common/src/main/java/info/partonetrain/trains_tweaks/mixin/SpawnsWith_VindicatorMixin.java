package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.spawnswith.EquipType;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Vindicator.class)
public class SpawnsWith_VindicatorMixin {
    @Inject(method = "populateDefaultEquipmentSlots", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Vindicator;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"), cancellable = true)
    public void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean() && SpawnsWithFeatureConfig.APPLY_TO_VINDICATOR_SPAWN.getAsBoolean()){
            Vindicator self = (Vindicator) (Object) this;

            List<ItemStack> loot = SpawnsWithFeature.getEquipmentFromLootTableForSpecificMob(self, Constants.VINDICATOR_SPAWN_LOOT_TABLE);
            SpawnsWithFeature.equipMobWithRolledStacks(loot, self, EquipType.BOTH_HANDS);

            ci.cancel();
        }
    }
}
