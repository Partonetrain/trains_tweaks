package info.partonetrain.trains_tweaks.feature.fireresistant;

import info.partonetrain.trains_tweaks.ModFeature;

public class FireResistantFeature extends ModFeature {
    public FireResistantFeature() {
        super("FireResistant", FireResistantFeatureConfig.SPEC);
        compatibleMods.add("lychee"); //use lychee:fire_immune instead
    }
}
