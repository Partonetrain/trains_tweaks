package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MobDrops_LivingEntityMixin {
    @Inject(method = "createWitherRose", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$createWitherRose(LivingEntity entitySource, CallbackInfo ci){
        if(entitySource != null && !AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_WITHER_ROSE.getAsBoolean()){
            LivingEntity self = (LivingEntity) (Object) this;
            if (!self.level().isClientSide) {
                if (entitySource.getType().is(Constants.ROSE_KILLER_TAG)) {
                    DamageSource damageSource = self.getLastDamageSource();
                    LootTable lootTable = self.level().getServer().reloadableRegistries().getLootTable(Constants.ROSE_LOOT_TABLE);
                    LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)self.level())).withParameter(LootContextParams.THIS_ENTITY, self).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.ATTACKING_ENTITY, entitySource).withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, entitySource);
                    LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);

                    ObjectArrayList<ItemStack> items = lootTable.getRandomItems(lootparams, self.getLootTableSeed());

                    for(ItemStack stack : items){
                        ItemEntity spawnedStack = self.spawnAtLocation(stack);
                    }
                }
            }
            ci.cancel();
        }
    }

    @Inject(method = "shouldDropLoot", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$shouldDropLoot(CallbackInfoReturnable<Boolean> cir){
        LivingEntity self = (LivingEntity) (Object) this;
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean()
                && MobDropsFeatureConfig.BABIES_DROP_LOOT.getAsBoolean() && self.isBaby()){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "shouldDropExperience", at=@At("HEAD"), cancellable = true)
    public void trains_tweaks$shouldDropExperience(CallbackInfoReturnable<Boolean> cir){
        LivingEntity self = (LivingEntity) (Object) this;
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean()
                && MobDropsFeatureConfig.BABIES_DROP_EXPERIENCE.getAsBoolean() && self.isBaby()){
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "dropFromLootTable", at=@At("TAIL"))
    public void trains_tweaks$dropFromLootTable(DamageSource damageSource, boolean hitByPlayer, CallbackInfo ci){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.GENERIC_DROP_ENABLED.getAsBoolean()) {
            LivingEntity self = (LivingEntity) (Object) this;
            ServerLevel serverlevel = (ServerLevel) self.level();
            LootTable lootTable = serverlevel.getServer().reloadableRegistries().getLootTable(Constants.GENERIC_DROP_TABLE);

            LootParams.Builder builder = (new LootParams.Builder((ServerLevel)self.level())).withParameter(LootContextParams.THIS_ENTITY, self).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
            if (hitByPlayer && self.lastHurtByPlayer != null) {
                builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, self.lastHurtByPlayer).withLuck(self.lastHurtByPlayer.getLuck());
            }

            LootParams lootParams = builder.create(LootContextParamSets.ENTITY);
            lootTable.getRandomItems(lootParams, self.getLootTableSeed(), self::spawnAtLocation);
        }
    }
}
