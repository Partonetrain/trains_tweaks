package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Turtle.class)
public abstract class MobDrops_TurtleMixin extends Mob {
    protected MobDrops_TurtleMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "ageBoundaryReached", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Turtle;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;I)Lnet/minecraft/world/entity/item/ItemEntity;"), cancellable = true)
    public void trains_tweaks$ageBoundaryReached(CallbackInfo ci){
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.APPLY_TO_TURTLE_GROW.getAsBoolean()) {
            Turtle self = (Turtle) (Object) this;
            ServerLevel serverlevel = (ServerLevel) self.level();
            LootTable lootTable = serverlevel.getServer().reloadableRegistries().getLootTable(Constants.TURTLE_GROW_LOOT_TABLE);
            LootParams lootParams = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.THIS_ENTITY, self).create(LootContextParamSets.GIFT);

            for (ItemStack itemstack : lootTable.getRandomItems(lootParams)) {
                self.spawnAtLocation(itemstack, 1);
            }
            ci.cancel();
        }
    }

    @Unique
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(!AllFeatures.MOB_DROPS_FEATURE.isIncompatibleLoaded() && MobDropsFeatureConfig.ENABLED.getAsBoolean() && MobDropsFeatureConfig.ADD_TURTLE_BRUSH.getAsBoolean()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.is(Items.BRUSH) && this.trains_tweaks$brushOffScute()) {
                itemstack.hurtAndBreak(16, player, getSlotForHand(hand));
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                return super.mobInteract(player, hand);
            }
        }
        return InteractionResult.PASS;
    }

    @Unique
    public boolean trains_tweaks$brushOffScute() {
        if (this.isBaby()) {
            return false;
        } else {
            Turtle self = (Turtle) (Object) this;
            ServerLevel serverlevel = (ServerLevel) self.level();
            LootTable lootTable = serverlevel.getServer().reloadableRegistries().getLootTable(Constants.TURTLE_BRUSH_LOOT_TABLE);
            LootParams lootParams = (new LootParams.Builder(serverlevel)).withParameter(LootContextParams.ORIGIN, self.position()).withParameter(LootContextParams.THIS_ENTITY, self).create(LootContextParamSets.GIFT);

            for (ItemStack itemstack : lootTable.getRandomItems(lootParams)) {
                self.spawnAtLocation(itemstack, 1);
            }

            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.playSound(SoundEvents.ARMADILLO_BRUSH);
            return true;
        }
    }

}