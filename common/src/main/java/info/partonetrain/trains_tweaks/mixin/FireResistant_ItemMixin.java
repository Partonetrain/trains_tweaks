package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.fireresistant.FireResistantFeatureConfig;
import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class FireResistant_ItemMixin {
    @Inject(at = @At("HEAD"), method = "inventoryTick")
    private void trains_tweaks$inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected, CallbackInfo ci) {
        if(!AllFeatures.FIRE_RESISTANT_FEATURE.isIncompatibleLoaded() && FireResistantFeatureConfig.ENABLED.getAsBoolean()) {
            if (stack.is(Constants.FIRE_RESISTANT_TAG) && stack.is(Constants.NOT_FIRE_RESISTANT_TAG)) {
                Constants.LOG.error("Item " + stack.getItem().getDescriptionId() + " trains_tweaks:fire_resistant and trains_tweaks:not_fire_resistant item tags! This is an error with your datapack");
            }
            else if(!Services.PLATFORM.isModLoaded("lychee") && stack.is(Constants.FIRE_RESISTANT_TAG)){
                stack.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
            }
            else if(stack.is(Constants.NOT_FIRE_RESISTANT_TAG)){
                stack.remove(DataComponents.FIRE_RESISTANT);
            }

        }
    }

}
