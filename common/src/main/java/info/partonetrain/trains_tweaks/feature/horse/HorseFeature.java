package info.partonetrain.trains_tweaks.feature.horse;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HorseFeature extends ModFeature implements IEarlyConfigReader {

    public static boolean configRead = false; //unused currently

    public static boolean enabled = false;
    public static double healthBuff = -1.0F;
    public static double speedBuff = -1.0F;
    public static double jumpBuff = -1.0F;

    public HorseFeature()
    {
        super("Horse", HorseFeatureConfig.SPEC);
        //Realistic Horse Genetics and DragN's Livestock Overhaul use different entities
        //However, this may affect other modded mobs that extend from vanilla horses
    }

    /*
    /summon horse ~ ~ ~ {Tame:1b,CustomName:'"Worst Horse"',attributes:[{id:"minecraft:generic.jump_strength",base:0.4},{id:"minecraft:generic.max_health",base:15},{id:"minecraft:generic.movement_speed",base:0.1125}]}
    /summon horse ~ ~ ~ {Tame:1b,CustomName:'"Worst Horse"',attributes:[{id:"minecraft:generic.jump_strength",base:0.4},{id:"minecraft:generic.max_health",base:15},{id:"minecraft:generic.movement_speed",base:0.1125}]}
    /summon horse ~ ~ ~ {Tame:1b,CustomName:'"Best Horse"',attributes:[{id:"minecraft:generic.jump_strength",base:1.0},{id:"minecraft:generic.max_health",base:30},{id:"minecraft:generic.movement_speed",base:0.3375}]}
     */

    //needed since net.minecraft.world.entity.ai.attributes.DefaultAttributes.<clinit> tries to use these values
    public void readConfigsEarly(){
        final String configFileLoc = System.getProperty("user.dir") + "\\config\\trains_tweaks\\" + this.getFeatureName() + ".toml";
        Path configFilePath = Paths.get(configFileLoc); //converts to correct path regardless of platform
        try {
            List<String> allLines = Files.readAllLines(configFilePath);
            if (allLines.contains("\"Horse Tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                configRead = true;
                return; //don't bother checking the rest
            }

            Pattern healthPattern = Pattern.compile("\"Horse Health Minimum\" *= *([-+]?[0-9]*.?[0-9]+)");
            Pattern speedPattern = Pattern.compile("\"Horse Speed Minimum\" *= *([-+]?[0-9]*.?[0-9]+)");
            Pattern jumpPattern = Pattern.compile("\"Horse Jump Minimum\" *= *([-+]?[0-9]*.?[0-9]+)");

            for (String line : allLines) {

                if (healthBuff == -1.0F) {
                    Matcher matcher = healthPattern.matcher(line);
                    if(matcher.matches()){
                        double value = Double.parseDouble(matcher.group(1));
                        if(value != 15.0F){
                            Constants.LOG.info("Horse health will be at least " + value);
                            healthBuff = value;
                        }
                    }
                }
                if (speedBuff == -1.0F) {
                    Matcher matcher = speedPattern.matcher(line);
                    if(matcher.matches()){
                        double value = Double.parseDouble(matcher.group(1));
                        if(value != 0.1125){
                            Constants.LOG.info("Horse speed will be at least " + value);
                            speedBuff = value;
                        }
                    }
                }
                if (jumpBuff == -1.0F) {
                    Matcher matcher = jumpPattern.matcher(line);
                    if(matcher.matches()){
                        double value = Double.parseDouble(matcher.group(1));
                        if(value != 0.4){
                            Constants.LOG.info("Horse jump will be at least " + value);
                            jumpBuff = value;
                        }
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
