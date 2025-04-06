package info.partonetrain.trains_tweaks.platform;

import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import info.partonetrain.trains_tweaks.platform.services.IPlatformHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean canRollSpawnsWithTables(Mob mob) {
        //It's called EntitySpawnReason in the future
        //.get() here returns a list of strings for some reason
        String mstString = mob.getSpawnType().name();
        return SpawnsWithFeatureConfig.APPLIES_TO_SPAWN_TYPES.get().contains(mstString);
    }
}