package info.partonetrain.trains_tweaks.feature.tameocelot;

import info.partonetrain.trains_tweaks.ModFeature;

public class TameOcelotFeature extends ModFeature {

    public TameOcelotFeature() {
        super("TameOcelot", TameOcelotFeatureConfig.SPEC);
        //compatibleMods.add("");
        incompatibleMods.add("ocelotfix");
    }
}
