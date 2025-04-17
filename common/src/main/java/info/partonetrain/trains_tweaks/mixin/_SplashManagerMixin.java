package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

//Client only!
@Mixin(SplashManager.class)
public class _SplashManagerMixin {
    @ModifyReturnValue(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/List;", at=@At("RETURN"))
    public List<String> trains_tweaks$prepare(List<String> original){
        if(original.contains("Vanilla!")){
            int i = original.indexOf("Vanilla!");
            original.set(i, ChatFormatting.BOLD.toString() + ChatFormatting.ITALIC.toString() + "Modded!");
        }
        return original;
    }
}
