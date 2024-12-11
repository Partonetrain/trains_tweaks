package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.platform.Services;

import java.util.List;

public final class CommonClass {

    public static void init() {
        Constants.LOG.info("TrainsTweaks has " + AllFeatures.list.size() + " Features");
        determineIncompatibleMods();
    }

    public static void determineIncompatibleMods(){
        for(ModFeature mf : AllFeatures.list){
            List<String> incompatibleMods = mf.getIncompatibleMods();
            if(!incompatibleMods.isEmpty()){
                for(String s : incompatibleMods){
                    if(Services.PLATFORM.isModLoaded(s)){
                        mf.setIncompatibleLoaded(false);
                        Constants.LOG.info("Feature " + mf.getFeatureName() + " was disabled due " +
                                "incompatible mod " + s);
                    }
                }
            }
        }
    }
}