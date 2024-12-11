package info.partonetrain.trains_tweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public abstract class ModFeature {
    protected String featureName;
    protected ModConfigSpec configSpec;

    protected List<String> compatibleMods = new ArrayList<>();  //mod ids that have explicit compatibility with this feature
    protected List<String> incompatibleMods = new ArrayList<>();; //mod ids that cause this feature to automatically disable itself

    protected boolean incompatibleLoaded;

    public ModFeature(String featureName, ModConfigSpec spec){
        this.featureName = featureName;
        this.configSpec = spec;
    }

    public String getFeatureName(){
        return featureName;
    }

    public boolean isIncompatibleLoaded() {
        return incompatibleLoaded;
    }

    public List<String> getCompatibleMods() {
        return compatibleMods;
    }

    public List<String> getIncompatibleMods() {
        return incompatibleMods;
    }

    public void setIncompatibleLoaded(boolean enabled){
        this.incompatibleLoaded = enabled;
    }

    public String getConfigPath(){
        return "trains_tweaks//" + featureName + ".toml";
    }
}
