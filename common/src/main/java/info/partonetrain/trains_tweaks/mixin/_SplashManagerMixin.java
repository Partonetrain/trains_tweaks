package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.nio.charset.StandardCharsets;
import java.util.List;

//Client only!
@Mixin(SplashManager.class)
public class _SplashManagerMixin {
    @Shadow @Final private static RandomSource RANDOM;

    @ModifyReturnValue(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/List;", at=@At("RETURN"))
    public List<String> trains_tweaks$prepare(List<String> original){
        if(original.contains("Vanilla!")){ //almost certainly using default splashes.txt
            int i = original.indexOf("Vanilla!");
            original.set(i, ChatFormatting.BOLD + ChatFormatting.ITALIC.toString() + "Modded!");
            //these are like this so the splashes don't appear in Google searches
            //they're just harmless splashes, I promise.
            if(Minecraft.getInstance().getLanguageManager().getSelected().startsWith("en")){
                original.add(RANDOM.nextIntBetweenInclusive(0, original.size() - 1), new String(new byte[]{83, 117, 112, 112, 111, 114, 116, 32, 116, 104, 101, 32, 114, 105, 103, 104, 116, 32, 116, 111, 32, 68, 117, 101, 32, 80, 114, 111, 99, 101, 115, 115, 33}, StandardCharsets.UTF_8));
                original.add(RANDOM.nextIntBetweenInclusive(0, original.size() - 1), new String(new byte[]{67, 104, 101, 99, 107, 115, 32, 97, 110, 100, 32, 98, 97, 108, 97, 110, 99, 101, 115, 33}, StandardCharsets.UTF_8));
                original.add(RANDOM.nextIntBetweenInclusive(0, original.size() - 1), new String(new byte[]{82, 101, 97, 100, 32, -62, -89, 111, 79, 110, 32, 84, 121, 114, 97, 110, 110, 121, -62, -89, 114, 32, 98, 121, 32, 84, 105, 109, 111, 116, 104, 121, 32, 83, 110, 121, 100, 101, 114, 33}, StandardCharsets.UTF_8));
            }
        }
        System.out.println(original);
        return original;
    }
}
