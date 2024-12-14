package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeature;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeatureConfig;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireChargeItem.class)
public class Yeet_FireChargeItemMixin extends Item {

    public Yeet_FireChargeItemMixin(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!AllFeatures.YEET_FEATURE.isIncompatibleLoaded() && YeetFeatureConfig.ENABLED.getAsBoolean() && YeetFeatureConfig.THROW_FIRE_CHARGES.getAsBoolean()) {
            if (!player.getCooldowns().isOnCooldown(Items.FIRE_CHARGE) && !level.isClientSide()) {
                Vec3 look = new Vec3(player.position().x, player.getEyePosition().y(), player.position().z);
                SmallFireball smallFireball = new SmallFireball(level, player, look);
                smallFireball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.0F, 2.5F);
                smallFireball.setPos(player.getX(), player.getEyePosition().y(), player.getZ()); //needed to make it shoot out of face
                level.addFreshEntity(smallFireball);
            }
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 2.0F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + 1.0F));
            ItemStack itemStack = player.getItemInHand(interactionHand);
            player.getCooldowns().addCooldown(Items.FIRE_CHARGE, YeetFeatureConfig.FIRE_CHARGES_COOLDOWN.getAsInt());
            player.awardStat(Stats.ITEM_USED.get(this));
            itemStack.consume(1, player);
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
