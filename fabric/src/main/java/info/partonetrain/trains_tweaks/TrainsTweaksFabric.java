package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.experience.ExperienceFeatureConfig;
import net.fabricmc.api.ModInitializer;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;

public class TrainsTweaksFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        for(ModFeature mf : AllFeatures.list){
            if(mf.configSpec != null){
                NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON,
                        mf.configSpec, mf.getConfigPath());
            }
        }
        CommonClass.init();
    }
}
