package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.trains_tweaks.CommonClass;
import net.minecraft.ChatFormatting;
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

    @ModifyReturnValue(method = "prepare(Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)Ljava/util/List;",at=@At("RETURN"))
    public List<String> trains_tweaks$prepare(List<String> original){
        if(original.contains("Vanilla!")){ //almost certainly using default splashes.txt
            int i = original.indexOf("Vanilla!");
            original.set(i,ChatFormatting.BOLD + ChatFormatting.ITALIC.toString() + "Modded!");
            //these are like this so the splashes don't appear in Google searches
            //they're just harmless splashes,I promise.
            original.add(RANDOM.nextIntBetweenInclusive(0,original.size() - 1),new String(new byte[]{0x53,0x75,0x70,0x70,0x6F,0x72,0x74,0x20,0x74,0x68,0x65,0x20,0x72,0x69,0x67,0x68,0x74,0x20,0x74,0x6F,0x20,0x44,0x75,0x65,0x20,0x50,0x72,0x6F,0x63,0x65,0x73,0x73,0x21},StandardCharsets.UTF_8));
            original.add(RANDOM.nextIntBetweenInclusive(0,original.size() - 1),new String(new byte[]{0x43,0x68,0x65,0x63,0x6B,0x73,0x20,0x61,0x6E,0x64,0x20,0x62,0x61,0x6C,0x61,0x6E,0x63,0x65,0x73,0x21},StandardCharsets.UTF_8));
            original.add(RANDOM.nextIntBetweenInclusive(0,original.size() - 1),new String(new byte[]{0x44,0x6F,0x20,0x6E,0x6F,0x74,0x20,0x6F,0x62,0x65,0x79,0x20,0x69,0x6E,0x20,0x61,0x64,0x76,0x61,0x6E,0x63,0x65,0x21},StandardCharsets.UTF_8));
            original.add(RANDOM.nextIntBetweenInclusive(0,original.size() - 1),new String(new byte[]{0x4E,0x6F,0x20,0x70,0x65,0x72,0x73,0x6F,0x6E,0x20,0x69,0x73,0x20,0x69,0x6C,0x6C,0x65,0x67,0x61,0x6C,0x21},StandardCharsets.UTF_8));
            original.add(RANDOM.nextIntBetweenInclusive(0,original.size() - 1),new String(new byte[]{0x43,0x6F,0x6E,0x73,0x75,0x6D,0x65,0x72,0x73,0x20,0x70,0x61,0x79,0x20,0x74,0x61,0x72,0x69,0x66,0x66,0x73,0x21},StandardCharsets.UTF_8));
            original.add(RANDOM.nextIntBetweenInclusive(0,original.size() - 1),new String(new byte[]{0x4E, 0x6F, 0x20, 0x4B, 0x69, 0x6E, 0x67, 0x73, 0x21},StandardCharsets.UTF_8));
        }
        CommonClass.printInDev(original.toString());
        return original;
    }
}
