package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.spawnswith.EquipType;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Zombie.class)
public class SpawnsWith_ZombieMixin {
    //for some reason INVOKE_ASSIGN targeting super.populateDefaultEquipmentSlots fails
    @Inject(method = "populateDefaultEquipmentSlots", at= @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextFloat()F"), cancellable = true)
    public void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean() && SpawnsWithFeatureConfig.APPLY_TO_ZOMBIE_SPAWN.getAsBoolean()){
            Zombie self = (Zombie) (Object) this;

            List<ItemStack> loot = SpawnsWithFeature.getEquipmentFromLootTableForSpecificMob(self, Constants.ZOMBIE_SPAWN_LOOT_TABLE);
            SpawnsWithFeature.equipMob(loot, self, EquipType.BOTH_HANDS);

            ci.cancel();
        }
    }
}
