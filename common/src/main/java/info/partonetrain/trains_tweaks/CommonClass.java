package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import java.util.List;

public final class CommonClass {

    //debugging stuff
    private static int itemCount = 0;
    public static Exception worldOpenException = null;
    //

    public static void init() {
        Constants.LOG.info("TrainsTweaks has " + AllFeatures.features.size() + " Features");
        determineIncompatibleMods();
    }

    public static void determineIncompatibleMods(){
        for(ModFeature mf : AllFeatures.features){
            List<String> incompatibleMods = mf.getIncompatibleMods();
            if(!incompatibleMods.isEmpty()){
                for(String s : incompatibleMods){
                    if(Services.PLATFORM.isModLoaded(s)){ //hopefully this classloads Services as early as possible
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

    public static int countItemsIfNotYetCounted(){
        if(itemCount == 0){
            BuiltInRegistries.ITEM.forEach(item -> {
                itemCount++;
                //Constants.LOG.info("countItemsIfNotYetCounted:" + itemCount);
            });
        }
        return itemCount;
    }

    public static int getItemCount(){
        return itemCount;
    }

    public static void runCommand(ServerLevel level, String command) {
        MinecraftServer server = level.getServer();
        CommandSourceStack source = server.createCommandSourceStack().withLevel(level);
        server.getCommands().performPrefixedCommand(source, command);
    }
}