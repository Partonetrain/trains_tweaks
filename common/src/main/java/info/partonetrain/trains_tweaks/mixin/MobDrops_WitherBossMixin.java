package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherBoss.class)
public class MobDrops_WitherBossMixin extends Monster {
    protected MobDrops_WitherBossMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "dropCustomDeathLoot", at=@At(value="HEAD", target = "Lnet/minecraft/world/entity/boss/wither/WitherBoss;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"), cancellable = true)
    public void trains_tweaks$dropCustomDeathLoot(ServerLevel level, DamageSource damageSource, boolean recentlyHit, CallbackInfo ci){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_WITHER_DEATH.getAsBoolean()){
            ci.cancel();
        }
    }

    @Unique
    @Override
    protected void dropFromLootTable(@NotNull DamageSource damageSource, boolean attackedRecently) {
        super.dropFromLootTable(damageSource, attackedRecently); //drop vanilla loot table
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_WITHER_DEATH.getAsBoolean()){
            WitherBoss self = (WitherBoss)(Object)this;
            LootTable lootTable = self.level().getServer().reloadableRegistries().getLootTable(Constants.STAR_LOOT_TABLE);
            LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)self.level())).withParameter(LootContextParams.THIS_ENTITY, self).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.DAMAGE_SOURCE, damageSource).withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
            LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);

            ObjectArrayList<ItemStack> items = lootTable.getRandomItems(lootparams, self.getLootTableSeed());
            for(ItemStack stack : items){
                ItemEntity spawnedStack = self.spawnAtLocation(stack);
                if (spawnedStack != null) {
                    spawnedStack.setExtendedLifetime();
                }
            }
        }
    }
}
