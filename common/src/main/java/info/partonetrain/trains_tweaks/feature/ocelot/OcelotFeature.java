package info.partonetrain.trains_tweaks.feature.ocelot;

import info.partonetrain.trains_tweaks.ModFeature;

public class OcelotFeature extends ModFeature {

    public OcelotFeature() {
        super("TameOcelot", OcelotFeatureConfig.SPEC);
        //compatibleMods.add("");
        incompatibleMods.add("ocelotfix");
    }
}
