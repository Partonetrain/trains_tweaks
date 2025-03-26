package info.partonetrain.trains_tweaks.feature.interdimensional;

import info.partonetrain.trains_tweaks.ModFeature;

public class InterdimensionalFeature extends ModFeature {
    public InterdimensionalFeature()
    {
        super("Interdimensional", InterdimensionalFeatureConfig.SPEC);
        incompatibleMods.add("cryingportals");
        incompatibleMods.add("charm");
    }
}
