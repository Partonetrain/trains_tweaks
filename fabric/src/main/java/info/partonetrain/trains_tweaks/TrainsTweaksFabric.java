package info.partonetrain.trains_tweaks;

import net.fabricmc.api.ModInitializer;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;

public class TrainsTweaksFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        for(ModFeature mf : AllFeatures.features){
            if(mf.configSpec != null){
                NeoForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON,
                        mf.configSpec, mf.getConfigPath());
            }
        }
        CommonClass.init();
    }
}
