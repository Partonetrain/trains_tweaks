package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
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
    @Inject(method = "populateDefaultEquipmentSlots", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Monster;populateDefaultEquipmentSlots(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/DifficultyInstance;)V"), cancellable = true)
    public void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci){
        if(!AllFeatures.SPAWNS_WITH_FEATURE.isIncompatibleLoaded() && SpawnsWithFeatureConfig.ENABLED.getAsBoolean() && SpawnsWithFeatureConfig.APPLY_TO_FOX_SPAWN.getAsBoolean()){
            Zombie self = (Zombie) (Object) this;

            List<ItemStack> loot = SpawnsWithFeature.getEquipmentFromLootTableForSpecificMob(self, Constants.ZOMBIE_SPAWN_LOOT_TABLE);
            if(!loot.isEmpty()) {
                ItemStack first = loot.get(0);
                self.setItemSlot(EquipmentSlot.MAINHAND, first);
                self.setDropChance(EquipmentSlot.MAINHAND, (float) SpawnsWithFeatureConfig.GENERIC_TABLE_DROP_CHANCE.getAsDouble());

                if(loot.size() != 1) {
                    ItemStack second = loot.get(1);
                    self.setItemSlot(EquipmentSlot.OFFHAND, second);
                    self.setDropChance(EquipmentSlot.OFFHAND, (float) SpawnsWithFeatureConfig.GENERIC_TABLE_DROP_CHANCE.getAsDouble());

                    //drop any extra items on the ground
                    //this is not tested. It's best if the loot table can only ever generate 2 stacks
                    for (ItemStack itemstack : loot) {
                        if (!itemstack.equals(first) && !itemstack.equals(second)) {
                            self.spawnAtLocation(itemstack);
                        }
                    }
                }
            }

            ci.cancel();
        }
    }
}
