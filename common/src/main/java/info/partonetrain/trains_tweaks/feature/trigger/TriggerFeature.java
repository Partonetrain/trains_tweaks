package info.partonetrain.trains_tweaks.feature.trigger;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TriggerFeature extends ModFeature implements IEarlyConfigReader {

    public TriggerFeature() {
        super("Trigger", TriggerFeatureConfig.SPEC);
    }

    public static boolean enabled = false;
    public static boolean gameTimeEnabled = false;
    public static boolean dayEnabled = false;
    public static boolean systemDateEnabled = false;
    public static boolean glideEnabled = false;
    public static boolean takeDamageAndLiveEnabled = false;
    public static boolean systemDayOfWeekEnabled = false;
    public static boolean dropItemEnabled = false;

    public static GameTimeTrigger GAME_TIME_TRIGGER;
    public static DayTrigger DAY_TRIGGER;
    public static SystemDateTrigger SYSTEM_DATE_TRIGGER;
    public static GlideTrigger GLIDE_TRIGGER;
    public static TakeDamageAndLiveTrigger TAKE_DAMAGE_AND_LIVE_TRIGGER;
    public static SystemDayOfWeekTrigger SYSTEM_DAY_OF_WEEK_TRIGGER;
    public static DroppedItemTrigger DROPPED_ITEM_TRIGGER;

    public void readConfigsEarly(){

        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\" + this.getFeatureName() + ".toml";
        Path configFilePath = Paths.get(configFileLoc);
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Trigger tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                return; //don't bother checking the rest
            }
            if (allLines.contains("\"Game Time trigger\" = true")) {
                gameTimeEnabled = true;
            }
            else {
                gameTimeEnabled = false;
            }
            if (allLines.contains("\"Day trigger\" = true")) {
                dayEnabled = true;
            }
            else {
                dayEnabled = false;
            }
            if (allLines.contains("\"System Date trigger\" = true")) {
                systemDateEnabled = true;
            }
            else {
                systemDateEnabled = false;
            }
            if (allLines.contains("\"Glide trigger\" = true")) {
                glideEnabled = true;
            }
            else {
                glideEnabled = false;
            }
            if (allLines.contains("\"Take Damage and Live trigger\" = true")) {
                takeDamageAndLiveEnabled = true;
            }
            else {
                takeDamageAndLiveEnabled = false;
            }
            if (allLines.contains("\"System Day of Week trigger\" = true")) {
                systemDayOfWeekEnabled = true;
            }
            else {
                systemDayOfWeekEnabled = false;
            }
            if (allLines.contains("\"Dropped Item trigger\" = true")) {
                dropItemEnabled = true;
            }
            else {
                dropItemEnabled = false;
            }

        } catch (IOException e) {
            CommonClass.printEarlyConfigError(this.featureName, e);
        }
    }

    @Override
    public boolean isExtraEarly(){
        //readConfigsEarly is called in Trigger_CriteriaTriggersMixin
        return true;
    }
}
