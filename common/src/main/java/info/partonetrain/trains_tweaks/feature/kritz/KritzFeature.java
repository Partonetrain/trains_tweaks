package info.partonetrain.trains_tweaks.feature.kritz;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KritzFeature extends ModFeature implements IEarlyConfigReader {

    public static boolean configRead = false;

    public static boolean enabled = false;
    public static boolean addAttributes = true;
    public static boolean addEffects = true;
    public static double kritChance = -0.0625;
    public static double kritMultipler = -1.5;

    //registered at different times per-platform
    public static Holder<Attribute> MELEE_CRIT_CHANCE;
    public static Holder<Attribute> RANGED_CRIT_CHANCE;
    public static Holder<MobEffect> MELEE_CRIT_EFFECT;
    public static Holder<MobEffect> RANGED_CRIT_EFFECT;

    public KritzFeature() {
        super("Kritz", KritzFeatureConfig.SPEC);
        incompatibleMods.add("zenith_attributes");
        incompatibleMods.add("apothic_attributes");
    }

    public void readConfigsEarly(){
        if(configRead){
            return;
        }
        if(this.isIncompatibleLoaded())
        {
            enabled = false;
            return;
        }

        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\" + this.getFeatureName() + ".toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Critical Hit tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                return; //don't bother checking the rest
            }
            if (allLines.contains("\"Add Attributes\" = true")) {
                addAttributes = true;
                if (allLines.contains("\"Add Effects\" = true")) {
                    addEffects = true;
                }
                else {
                    addEffects = false;
                }
            }
            else {
                addAttributes = false;
                addEffects = false;
            }

            Pattern chancePattern = Pattern.compile("\"Crit Chance\" *= *([-+]?[0-9]*.?[0-9]+)");
            Pattern multPattern = Pattern.compile("\"Crit Multiplier\" *= *([-+]?[0-9]*.?[0-9]+)");

            for (String line : allLines) {
                if (kritChance == -0.0625) {
                    Matcher matcher = chancePattern.matcher(line);
                    if (matcher.matches()) {
                        kritChance = Double.parseDouble(matcher.group(1));

                    }
                }
                if (kritMultipler == -1.5) {
                    Matcher matcher = multPattern.matcher(line);
                    if (matcher.matches()) {
                        kritMultipler = Double.parseDouble(matcher.group(1));
                    }
                }
            }

            configRead = true;

        } catch (IOException e) {
            CommonClass.printEarlyConfigError(this.featureName, e);
            configRead = true;
        }
    }
}
