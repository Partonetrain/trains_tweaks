package info.partonetrain.trains_tweaks.feature.rarity;

import info.partonetrain.trains_tweaks.ModFeature;

public class RarityFeature extends ModFeature {
    public RarityFeature() {
        super("Rarity", RarityFeatureConfig.SPEC);
        incompatibleMods.add("customrarity");
    }
}
