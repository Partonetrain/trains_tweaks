package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.fireresistant.FireResistantFeatureConfig;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeature;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeatureConfig;
import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class Rarity_ItemEntityMixin {
    @Inject(method = "tick", at=@At("HEAD"))
    public void trains_tweaks$tick(CallbackInfo ci){
        if(!AllFeatures.RARITY_FEATURE.isIncompatibleLoaded() && RarityFeatureConfig.ENABLED.getAsBoolean() && RarityFeatureConfig.DATA_TAG_ENABLED.getAsBoolean()){
            ItemEntity self = (ItemEntity) (Object) this;
            if(!self.getItem().has(DataComponents.RARITY) && (self.getItem().is(Constants.COMMON_TAG) || self.getItem().is(Constants.UNCOMMON_TAG) || self.getItem().is(Constants.RARE_TAG) || self.getItem().is(Constants.EPIC_TAG))){
                RarityFeature.setTaggedRarity(self.getItem());
            }
        }
    }
}
