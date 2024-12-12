package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.experience.ExperienceFeature;
import info.partonetrain.trains_tweaks.feature.fireresistant.FireResistantFeature;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeature;
import info.partonetrain.trains_tweaks.feature.powderwalking.PowderWalkingFeature;
import info.partonetrain.trains_tweaks.feature.tameocelot.TameOcelotFeature;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeature;

import java.util.ArrayList;
import java.util.List;

public class AllFeatures {
    public static List<ModFeature> list = new ArrayList<>();

    public static final ModFeature EXPERIENCE_FEATURE = new ExperienceFeature();
    public static final ModFeature FIRE_RESISTANT_FEATURE = new FireResistantFeature();
    public static final ModFeature MOB_DROPS_FEATURE = new MobDropsFeature();
    public static final ModFeature POWDER_WALKING_FEATURE = new PowderWalkingFeature();
    public static final ModFeature RARITY_FEATURE = new RarityFeature();
    public static final ModFeature OCELOT_FEATURE = new TameOcelotFeature();

    static{
        list.add(EXPERIENCE_FEATURE);
        list.add(FIRE_RESISTANT_FEATURE);
        list.add(MOB_DROPS_FEATURE);
        list.add(POWDER_WALKING_FEATURE);
        list.add(OCELOT_FEATURE);
        list.add(RARITY_FEATURE);
    }
}
