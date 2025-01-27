package info.partonetrain.trains_tweaks.feature.npc;

import info.partonetrain.trains_tweaks.ModFeature;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;

public class NpcFeature extends ModFeature {
    public NpcFeature() {
        super("Npc", NpcFeatureConfig.SPEC);
        compatibleMods.add("smarterfarmers");
    }
}
