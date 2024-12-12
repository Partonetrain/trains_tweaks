package info.partonetrain.trains_tweaks.feature.experience;

import info.partonetrain.trains_tweaks.ModFeature;

public class ExperienceFeature extends ModFeature {
    public ExperienceFeature() {
        super("Experience", ExperienceFeatureConfig.SPEC);
        incompatibleMods.add("linearlevels");
        incompatibleMods.add("fixed-levels");
    }
}
