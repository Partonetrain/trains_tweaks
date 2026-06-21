package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Client only!
@Mixin(TitleScreen.class)
public class _TitleScreenMixin {
    @Inject(method = "init", at=@At("RETURN"))
    public void trains_tweask$init(CallbackInfo ci){
        Constants.LOG.info("Items in registry: " + String.valueOf(CommonClass.countItemsIfNotYetCounted()));
    }
}
