package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeature;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeatureConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//Client only!
@Mixin(Gui.class)
public class Zzz_GuiMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @ModifyArg(method = "renderSleepOverlay", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lnet/minecraft/client/renderer/RenderType;IIIII)V"), index = 5)
    public int trains_tweaks$renderSleepOverlay(int color){
        if(!AllFeatures.ZZZ_FEATURE.isIncompatibleLoaded() && ZzzFeatureConfig.ENABLED.getAsBoolean()) {
            LocalPlayer lp = this.minecraft.player;
            if(lp.isSleeping()){
                return ZzzFeature.getSleepNewColor(lp.getSleepTimer());
            }
        }
        return color;
    }

}
