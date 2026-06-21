package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Dynamic;
import info.partonetrain.trains_tweaks.CommonClass;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Client only!
@Mixin(WorldOpenFlows.class)
public class _WorldOpenFlowsMixin {

    @Inject(method = "openWorldLoadLevelStem", at=@At(value = "INVOKE", target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Throwable;)V"))
    public void trains_tweaks$openWorldLoadLevelData(LevelStorageSource.LevelStorageAccess levelStorage, Dynamic<?> levelData, boolean safeMode, Runnable onFail, CallbackInfo ci, @Local Exception e){
        CommonClass.worldOpenException = e;
    }
}
