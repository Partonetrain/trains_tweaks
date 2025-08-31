package info.partonetrain.trains_tweaks.feature.difficulty;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DifficultyFeature extends ModFeature implements IEarlyConfigReader {
    public DifficultyFeature() {
        super("Difficulty", DifficultyFeatureConfig.SPEC);
    }

    public static boolean enabled = false;
    public static boolean configRead = false;
    public static List<UUID> modifiedPlayers = new ArrayList<>();

    public void addModifiedPlayer(String cfgString){
        String[] split = cfgString.replace(" ", "").split(",");
        try{
            for(String s : split){
                modifiedPlayers.add(UUID.fromString(s));
                Constants.LOG.info("found uuid of modified player: " + s);
            }
        }
        catch (IllegalArgumentException e){
            Constants.LOG.error("Modified Difficulty Players is invalid: " + e.getMessage());
        }
    }

    public void readConfigsEarly(){
        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\" + this.getFeatureName() + ".toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Difficulty Tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                configRead = true;
                return; //don't bother checking the rest
            }

            for (String line : allLines) {
                if(line.contains("\"Modified Difficulty Players\" = ")){
                    String[] split = line.split("\"");
                    addModifiedPlayer(split[3]);
                }

            }
            configRead = true;

        } catch (IOException e) {
            CommonClass.printEarlyConfigError(this.featureName, e);
            configRead = true;
        }
    }
}
