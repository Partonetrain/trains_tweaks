package info.partonetrain.trains_tweaks.feature.zzz;

import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Collection;
import java.util.List;

public class ZzzFeature extends ModFeature {

    public ZzzFeature() {
        super("Zzz", ZzzFeatureConfig.SPEC);
    }

    public static void healPlayers(List<ServerPlayer> players){
        for (ServerPlayer sp : players) {
            sp.setHealth(sp.getMaxHealth());
        }
    }

    public static void removeDebuffs(List<ServerPlayer> players){
        for(ServerPlayer sp : players){
            Collection<MobEffectInstance> effects = sp.getActiveEffects();
            for(MobEffectInstance mei : effects){
                if(mei.getEffect().value().getCategory() == MobEffectCategory.HARMFUL
                        && !mei.isInfiniteDuration()){
                    sp.removeEffect(mei.getEffect());
                }
            }
        }
    }

    //Client only
    public static int getSleepNewColor(int sleepTimer) {
        float sleepPercentage = (float) sleepTimer / ZzzFeatureConfig.SLEEP_REQUIRED_TICKS.getAsInt();
        if (sleepPercentage > 1.0F) {
            sleepPercentage = 1.0F - (sleepTimer - ZzzFeatureConfig.SLEEP_REQUIRED_TICKS.getAsInt()) / 10.0F;
        }

        int newColor = ((int)(220.0F * sleepPercentage) << 24 | 1052704);
        if(ZzzFeatureConfig.CLIENT_SLEEP_COLOR.getAsInt() != 0){
            newColor += ZzzFeatureConfig.CLIENT_SLEEP_COLOR.getAsInt();
        }
        return newColor;
    }
}
