package info.partonetrain.trains_tweaks.platform;

import info.partonetrain.trains_tweaks.Constants;
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
        if(mob.getType().is(Constants.SPAWNSWITH_IGNORES)){
            Constants.LOG.error(mob.getName().getString() + " WAS in the spawnswith_ignores tag, but still called canRollSpawnsWithTables somehow");
            return false;
        }
        //It's called EntitySpawnReason in the future
        MobSpawnType mst = mob.getSpawnType();
        if(mst != null){
            String mstString = mst.name();
            //no idea why this list contains strings at this point, but it does!
            return SpawnsWithFeatureConfig.APPLIES_TO_SPAWN_TYPES.get().contains(mstString);
        }
        else{
            Constants.LOG.warn(mob.getName().getString() + " was not in the spawnswith_ignores tag and had no MobSpawnType");
            return false;
        }
    }
}