package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chicken.class)
public class MobDrops_ChickenMixin {
    @Inject(method = "aiStep", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Chicken;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    public void trains_tweaks$aiStep(CallbackInfo ci){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_CHICKEN_EGG.getAsBoolean()) {
            Chicken self = (Chicken) (Object) this;
            ServerLevel serverlevel = (ServerLevel) self.level();
            LootTable lootTable = serverlevel.getServer().reloadableRegistries().getLootTable(Constants.EGG_LOOT_TABLE);
            LootParams lootParams = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.THIS_ENTITY, self).create(LootContextParamSets.GIFT);
            //LootContextParamSets.GIFT is the same one panda sneeze uses.

            for (ItemStack itemstack : lootTable.getRandomItems(lootParams)) {
                self.spawnAtLocation(itemstack);
            }
        }
    }

    //prevent vanilla drop
    @WrapOperation(method = "aiStep", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Chicken;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    public ItemEntity trains_tweaks$aiStep2(Chicken instance, ItemLike itemLike, Operation<ItemEntity> original){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_CHICKEN_EGG.getAsBoolean()){
            return null;
        }
        return original.call(instance, itemLike);
    }
}
