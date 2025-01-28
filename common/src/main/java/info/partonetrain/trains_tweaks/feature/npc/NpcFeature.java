package info.partonetrain.trains_tweaks.feature.npc;

import info.partonetrain.trains_tweaks.ModFeature;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeatureConfig;

public class NpcFeature extends ModFeature {
    public NpcFeature() {
        super("Npc", NpcFeatureConfig.SPEC);
        compatibleMods.add("smarterfarmers");
    }

    public static boolean configParsed = false;
    //amount gained, weight (multiplier), maximum, decay per in-game day, decay per transfer (share penalty)
    public static int[] majorNegativeConfig = new int[5];
    public static int[] minorNegativeConfig = new int[5];
    public static int[] minorPositiveConfig = new int[5];
    public static int[] majorPositiveConfig = new int[5];
    public static int[] tradingConfig = new int[5];

    public static void ParseGossipConfigs(){
        majorPositiveConfig = ParseGossipConfig(NpcFeatureConfig.MAJOR_NEGATIVE.get());
        minorNegativeConfig = ParseGossipConfig(NpcFeatureConfig.MINOR_NEGATIVE.get());
        minorPositiveConfig = ParseGossipConfig(NpcFeatureConfig.MINOR_POSITIVE.get());
        majorPositiveConfig = ParseGossipConfig(NpcFeatureConfig.MAJOR_POSITIVE.get());
        tradingConfig = ParseGossipConfig(NpcFeatureConfig.TRADING.get());
        configParsed = true;
    }

    public static int[] ParseGossipConfig(String string){
        int[] ret = {0, 0, 0, 0, 0};
        String[] strings = string.replace(" ", "").split(",");
        for(int i=0; i<5; i++){
            ret[i] = Integer.parseInt(strings[i]);
        }
        return ret;
    }
}
