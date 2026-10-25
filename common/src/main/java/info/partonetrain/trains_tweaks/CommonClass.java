package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.platform.Services;
import io.netty.handler.codec.serialization.ClassResolvers;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class CommonClass {

    //debugging stuff
    private static int itemCount = 0;
    public static Exception worldOpenException = null;
    public static Map<String, List<Exception>> createAeronauticsErrors = new HashMap<>();

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

    //ACTUALLY converts to correct path regardless of platform. This did not happen before 1.2.1.
    //oops trains_tweaks didn't work on linux for two whole years
    public static Path platformlessPath(String featureName){
        return Paths.get(System.getProperty("user.dir"), "config", "trains_tweaks", featureName + ".toml");
    }

    public static void logExceptionDuringMixin(String mixinName, Exception e){
        if(Arrays.toString(e.getStackTrace()).contains("dev.simulated_team.simulated")){
            List<Exception> exceptions = createAeronauticsErrors.getOrDefault(mixinName, new ArrayList<>());
            exceptions.add(e);
            createAeronauticsErrors.put(mixinName, exceptions);
        }
        else{
            Constants.LOG.error(mixinName + " caught RuntimeException: " + e.getMessage());
            printInDev(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void writeCreateAeronaticsErrorLog(){
        long caErrors = CommonClass.createAeronauticsErrors.entrySet().size();
        if(caErrors > 0){
            List<String> logLines = new ArrayList<>();
            logLines.add("Train's Tweaks Suppressed Errors from Create Aeronautics");
            logLines.add("\n");
            int errNo = 0;
            for(var entry: CommonClass.createAeronauticsErrors.entrySet()){
                logLines.add("* Errors that occured in " + entry.getKey() + " *");
                logLines.add("\n");
                for(var value : entry.getValue()){
                    errNo++;
                    logLines.add(errNo + ": ");
                    logLines.add("Exception: " + value.getClass().getName());
                    logLines.add("Message: " + value.getMessage());
                    logLines.add("Stacktrace: ");
                    logLines.add("---");
                    for(StackTraceElement ste : value.getStackTrace()){
                        logLines.add(ste.toString());
                    }
                    logLines.add("---");
                }
                errNo = 0;
            }

            Path logPath = Paths.get(System.getProperty("user.dir"), "logs", "trains_tweaks_create_aeronautics_errors.txt");
            try {
                Files.write(logPath, logLines);
                Constants.LOG.error("Suppressed " + caErrors + " types of errors from Create Aeronautics during startup. Saved to logs/trains_tweaks_create_aeronautics_errors.txt. See https://github.com/Partonetrain/trains_tweaks/issues/12 for more information.");
            }
            catch (IOException e){
                Constants.LOG.error("Could not write to logs/trains_tweaks_create_aeronautics_errors.txt: " + e.toString());
            }

        }
    }
}