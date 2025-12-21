package info.partonetrain.trains_tweaks.feature.jumpy;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.ModFeature;
import info.partonetrain.trains_tweaks.feature.cure.CureFeatureConfig;
import info.partonetrain.trains_tweaks.mixin.Jumpy_MobAccessor;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JumpyFeature extends ModFeature implements IEarlyConfigReader {

    public static boolean configRead = false;
    public static boolean enabled = false;


    public static boolean fixEffect = false;
    public static double fixedEffectModifier = 0.25D;

    public JumpyFeature()
    {
        super("Jumpy", JumpyFeatureConfig.SPEC);
        incompatibleMods.add("bouncyanimals");
    }

    public static void addJumpWhileMovingGoal(Mob mob){
        GoalSelector mobGoalSelector = ((Jumpy_MobAccessor) mob).trains_tweaks$getMobGoalSelector();
        mobGoalSelector.addGoal(JumpyFeatureConfig.JUMP_WHILE_MOVING_PRIORITY.get(), new JumpWhileMovingGoal(mob));
        if(JumpyFeatureConfig.SHOW_DEBUG.getAsBoolean()){
            Constants.LOG.info("Added JumpWhileMovingGoal to " + mob.getCustomName());
        }
    }

    public static void addJumpRandomlyGoal(Mob mob){
        GoalSelector mobGoalSelector = ((Jumpy_MobAccessor) mob).trains_tweaks$getMobGoalSelector();
        mobGoalSelector.addGoal(JumpyFeatureConfig.JUMP_RANDOMLY_PRIORITY.get(), new JumpRandomlyGoal(mob));
        if(JumpyFeatureConfig.SHOW_DEBUG.getAsBoolean()){
            Constants.LOG.info("Added JumpRandomlyGoal to " + mob.getCustomName());
        }
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
            if (allLines.contains("\"Jumpy tweaks\" = true")) {
                enabled = true;
            }
            else {
                enabled = false;
                configRead = true;
                return; //don't bother checking the rest
            }

            if (allLines.contains("\"Jump Boost fix\" = true")) {
                fixEffect = true;
            }
            else {
                fixEffect = false;
            }

            Pattern pattern = Pattern.compile("\"Jump Boost Bonus Per Level\" *= *([-+]?[0-9]*.?[0-9]+)");

            for (String line : allLines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    double value = Double.parseDouble(matcher.group(1));
                    Constants.LOG.info("Jump Boost will add " + value + " to " + Attributes.JUMP_STRENGTH.getRegisteredName());
                    fixedEffectModifier = value;
                }
            }

            configRead = true;

        } catch (IOException e) {
            CommonClass.printEarlyConfigError(this.featureName, e);
            configRead = true;
        }
    }

    @Override
    public boolean isExtraEarly(){
        //readConfigsEarly is called in Jumpy_MobEffectsMixin
        return true;
    }

}
