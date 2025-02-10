package info.partonetrain.trains_tweaks.feature.utilitycommands;

import com.mojang.brigadier.Command;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;
import info.partonetrain.trains_tweaks.feature.trigger.DayTrigger;
import info.partonetrain.trains_tweaks.feature.trigger.GameTimeTrigger;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityCommandsFeature extends ModFeature implements IEarlyConfigReader {

    public static boolean enabled = false;
    public static boolean addKillNonPlayer = false;

    public UtilityCommandsFeature() {
        super("UtilityCommands", UtilityCommandsFeatureConfig.SPEC);
    }

    public void readConfigsEarly(){

        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\" + this.getFeatureName() + ".toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
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

        } catch (IOException e) {
            Constants.LOG.error("UtilityCommands config error:" + e);
            Constants.LOG.info("Don't fret! Above error is most likely one-time occurrence from UtilityCommands config file not existing yet");
        }
    }
}
