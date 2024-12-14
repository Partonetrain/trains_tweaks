package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.cure.CureFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeature;
import info.partonetrain.trains_tweaks.feature.experience.ExperienceFeature;
import info.partonetrain.trains_tweaks.feature.fireresistant.FireResistantFeature;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeature;
import info.partonetrain.trains_tweaks.feature.powderwalking.PowderWalkingFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.tameocelot.TameOcelotFeature;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeature;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeature;

import java.util.ArrayList;
import java.util.List;

public class AllFeatures {
    public static List<ModFeature> list = new ArrayList<>();

    public static final ModFeature CURE_FEATURE = new CureFeature();
    public static final ModFeature DIFFICULTY_FEATURE = new DifficultyFeature();
    public static final ModFeature EXPERIENCE_FEATURE = new ExperienceFeature();
    public static final ModFeature FIRE_RESISTANT_FEATURE = new FireResistantFeature();
    public static final ModFeature MOB_DROPS_FEATURE = new MobDropsFeature();
    public static final ModFeature POWDER_WALKING_FEATURE = new PowderWalkingFeature();
    public static final ModFeature RARITY_FEATURE = new RarityFeature();
    public static final ModFeature SPAWNS_WITH_FEATURE = new SpawnsWithFeature();
    public static final ModFeature TAME_OCELOT_FEATURE = new TameOcelotFeature();
    public static final ModFeature YEET_FEATURE = new YeetFeature();

    static{
        list.add(CURE_FEATURE);
        list.add(DIFFICULTY_FEATURE);
        list.add(EXPERIENCE_FEATURE);
        list.add(FIRE_RESISTANT_FEATURE);
        list.add(MOB_DROPS_FEATURE);
        list.add(POWDER_WALKING_FEATURE);
        list.add(RARITY_FEATURE);
        list.add(SPAWNS_WITH_FEATURE);
        list.add(TAME_OCELOT_FEATURE);
        list.add(YEET_FEATURE);
    }
}
