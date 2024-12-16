package info.partonetrain.trains_tweaks.feature.yeet;

import info.partonetrain.trains_tweaks.ModFeature;

public class YeetFeature extends ModFeature {

    public YeetFeature() {
        super("Yeet", YeetFeatureConfig.SPEC);
        compatibleMods.add("snowballsfreezemobs");
    }
}
