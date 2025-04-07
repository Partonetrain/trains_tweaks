package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.platform.Services;

import java.util.List;

public final class CommonClass {

    public static void init() {
        Constants.LOG.info("TrainsTweaks has " + AllFeatures.features.size() + " Features");
        determineIncompatibleMods();
    }

    public static void determineIncompatibleMods(){
        for(ModFeature mf : AllFeatures.features){
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

    public static void printEarlyConfigError(String featureName, Exception e){
        Constants.LOG.error(featureName + " config error:" + e.toString());
        Constants.LOG.info("Don't fret! Above error is most likely one-time occurrence from " + featureName + " config file not existing yet");

    }

    public static void printInDev(String s){
        if(Services.PLATFORM.isDevelopmentEnvironment()){
            System.out.println(s);
        }
    }
}