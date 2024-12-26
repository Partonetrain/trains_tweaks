package info.partonetrain.trains_tweaks.feature.attackspeed;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttackSpeedFeature extends ModFeature {

    public static boolean configRead = false;

    public static boolean enabled = false;
    public static boolean fixEffects = true;
    public static boolean addEffects = true;
    public static double fixedEffectModifier = 0.25D;

    //registered at different times per-platform
    public static Holder<MobEffect> DEXTERITY;
    public static Holder<MobEffect> CLUMSY;

    public AttackSpeedFeature()
    {
        super("AttackSpeed", AttackSpeedFeatureConfig.SPEC);
    }

    //since we're dealing with VERY early loaded classes
    //we need to read from the config file directly
    //there is probably a better way to do this
    public static void readConfigsEarly(){
        //multiple mixins call this method
        //so don't bother trying to read the file more than once
        if(configRead) {
            return;
        }

        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\AttackSpeed.toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Attack Speed tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                configRead = true;
                return; //don't bother checking the rest
            }
            if (allLines.contains("\"Fix Effects\" = true")) {
                fixEffects = true;
            }
            else {
                fixEffects = false;
            }
            if (allLines.contains("\"Add Effects\" = true")) {
                addEffects = true;
            }
            else {
                addEffects = false;
            }

            Pattern pattern = Pattern.compile("\"Fixed Effects Modifier\" *= *([-+]?[0-9]*.?[0-9]+)");

            for (String line : allLines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    double value = Double.parseDouble(matcher.group(1));
                    Constants.LOG.info("Found!: Fixed Effects Modifier = " + value);
                    Constants.LOG.info("Haste will add " + value + " to " + Attributes.BLOCK_BREAK_SPEED.getRegisteredName());
                    fixedEffectModifier = value;
                }
            }
            configRead = true;

        } catch (IOException e) {
            Constants.LOG.error("AttackSpeed config error:" + e);
            Constants.LOG.info("Don't fret! Above error is most likely one-time occurrence from AttackSpeed config file not existing yet");
            configRead = true;
        }
    }
}
