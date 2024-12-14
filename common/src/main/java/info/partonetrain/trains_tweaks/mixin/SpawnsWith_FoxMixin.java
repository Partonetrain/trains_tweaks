package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Fox.class)
public class SpawnsWith_FoxMixin {
    @Inject(method = "populateDefaultEquipmentSlots", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean() && SpawnsWithFeatureConfig.APPLY_TO_FOX_SPAWN.getAsBoolean()){
            Fox self = (Fox) (Object) this;

            List<ItemStack> loot = SpawnsWithFeature.getEquipmentFromLootTableForSpecificMob(self, Constants.FOX_SPAWN_LOOT_TABLE);
            if(!loot.isEmpty()){
                ItemStack first = loot.getFirst();
                self.setItemSlot(EquipmentSlot.MAINHAND, first);
                self.setGuaranteedDrop(EquipmentSlot.MAINHAND);
                //drop any extra items on the ground
                //this is not tested. It's best if the loot table can only ever generate 1 stack
                for (ItemStack itemstack : loot) {
                    if(!itemstack.equals(first)){
                        self.spawnAtLocation(itemstack);
                    }
                }
            }

            ci.cancel();
        }
    }
}
