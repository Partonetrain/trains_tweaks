package info.partonetrain.trains_tweaks.platform;

import fuzs.puzzleslib.api.core.v1.CommonAbstractions;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import info.partonetrain.trains_tweaks.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.mehvahdjukaar.amendments.configs.CommonConfigs;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean canRollSpawnsWithTables(Mob mob) {
        //It's called EntitySpawnReason in the future
        if(isModLoaded("puzzleslib")){
            MobSpawnType mst = CommonAbstractions.INSTANCE.getMobSpawnType(mob);
            if(mst != null){
                String mstString = mst.name();
                //no idea why this list contains strings at this point, but it does!
                return SpawnsWithFeatureConfig.APPLIES_TO_SPAWN_TYPES.get().contains(mstString);
            }
            else{
                return false;
            }

        }
        return true; //don't bother checking of no mod capable of checking is installed
    }

    @Override
    public boolean isAmendmentsFireballEnabled() {
        return CommonConfigs.SNOWBALL_FREEZE.get() > 0;
    }

    @Override
    public boolean isAmendmentsSnowballEnabled() {
        return CommonConfigs.THROWABLE_FIRE_CHARGES.get();
    }
}
