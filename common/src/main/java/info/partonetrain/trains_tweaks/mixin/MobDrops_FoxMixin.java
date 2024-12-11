package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public class MobDrops_FoxMixin {
    @Inject(method = "populateDefaultEquipmentSlots", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_FOX_SPAWN.getAsBoolean()){
            Fox self = (Fox) (Object) this;
            ItemStack stack;
            ServerLevel serverlevel = (ServerLevel) self.level();
            LootTable lootTable = serverlevel.getServer().reloadableRegistries().getLootTable(Constants.FOX_SPAWN_LOOT_TABLE);
            LootParams lootParams = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.THIS_ENTITY, self).create(LootContextParamSets.GIFT);

            ObjectArrayList<ItemStack> loot = lootTable.getRandomItems(lootParams);
            ItemStack first = loot.getFirst();
            self.setItemSlot(EquipmentSlot.MAINHAND, first);
            loot.remove(first);
            //drop any extra items
            for (ItemStack itemstack : loot) {
                self.spawnAtLocation(itemstack);
            }
            ci.cancel();
        }
    }
}
