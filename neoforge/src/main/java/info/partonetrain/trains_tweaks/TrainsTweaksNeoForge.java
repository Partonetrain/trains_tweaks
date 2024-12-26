package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedEffects;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeature;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeatureConfig;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class TrainsTweaksNeoForge {

    public static DeferredRegister<MobEffect> MOB_EFFECTS;

    public TrainsTweaksNeoForge(ModContainer container, IEventBus eventBus) {
        for(ModFeature mf : AllFeatures.features){
            if(mf.configSpec != null) {
                container.registerConfig(ModConfig.Type.COMMON, mf.configSpec, mf.getConfigPath());
            }
            if(mf.getFeatureName().equals("AttackSpeed")) {
                if(AttackSpeedFeature.addEffects) {
                    MOB_EFFECTS.register("dexterity", () -> AttackSpeedEffects.d);
                    MOB_EFFECTS.register("clumsy", () -> AttackSpeedEffects.c);
                }
            }
        }
        CommonClass.init();
    }

}