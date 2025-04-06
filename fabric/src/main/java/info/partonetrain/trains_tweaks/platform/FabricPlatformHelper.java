package info.partonetrain.trains_tweaks.platform;

import fuzs.puzzleslib.api.core.v1.CommonAbstractions;
import fuzs.puzzleslib.fabric.impl.core.FabricAbstractions;
import fuzs.puzzleslib.impl.attachment.DataAttachmentRegistryImpl;
import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeatureConfig;
import info.partonetrain.trains_tweaks.platform.services.IPlatformHelper;
import net.fabricmc.fabric.impl.attachment.AttachmentTypeImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.LivingEntity;
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
            String mstString = CommonAbstractions.INSTANCE.getMobSpawnType(mob).name();
            return SpawnsWithFeatureConfig.APPLIES_TO_SPAWN_TYPES.get().contains(mstString);
        }
        return true;
    }
}
