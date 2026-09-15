package info.partonetrain.trains_tweaks.feature.utilitycommands;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UtilityCommandsFeature extends ModFeature implements IEarlyConfigReader {

    public static boolean enabled = false;
    public static boolean vanillaDebugCommands = false;
    public static boolean addKillNonPlayer = false;
    public static boolean addShowScheduled = false;
    public static boolean addSetHunger = false;

    public UtilityCommandsFeature() {
        super("UtilityCommands", UtilityCommandsFeatureConfig.SPEC);
    }

    public void readConfigsEarly(){
        Path configFilePath = CommonClass.platformlessPath(this.featureName);
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Utility Commands\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                return; //don't bother checking the rest
            }
            if (allLines.contains("\"Kill Non Players\" = true")) {
                addKillNonPlayer = true;
            }
            else {
                addKillNonPlayer = false;
            }
            if (allLines.contains("\"Vanilla Debug Commands\" = true")) {
                vanillaDebugCommands = true;
            }
            else {
                vanillaDebugCommands = false;
            }
            if (allLines.contains("\"Show Scheduled Functions\" = true")) {
                addShowScheduled = true;
            }
            else {
                addShowScheduled = false;
            }
            if (allLines.contains("\"Set Hunger\" = true")) {
                addSetHunger = true;
            }
            else {
                addSetHunger = false;
            }

        } catch (IOException e) {
            CommonClass.printEarlyConfigError(this.featureName, e);
        }
    }
}
