package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedEffects;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeature;
import info.partonetrain.trains_tweaks.feature.kritz.KritzEffects;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import info.partonetrain.trains_tweaks.feature.utilitycommands.KillNonPlayersCommand;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class TrainsTweaksNeoForge {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, Constants.MOD_ID);

    public TrainsTweaksNeoForge(ModContainer container, IEventBus eventBus) {
        for(ModFeature mf : AllFeatures.features){
            if(mf instanceof IEarlyConfigReader earlyConfigReader){
                earlyConfigReader.readConfigsEarly();
            }
            if(mf.configSpec != null) {
                container.registerConfig(ModConfig.Type.COMMON, mf.configSpec, mf.getConfigPath());
            }
            if(mf.getFeatureName().equals("AttackSpeed")) {
                if(AttackSpeedFeature.enabled && AttackSpeedFeature.addEffects) {
                    AttackSpeedFeature.DEXTERITY = MOB_EFFECTS.register("dexterity", () -> AttackSpeedEffects.d);
                    AttackSpeedFeature.CLUMSY = MOB_EFFECTS.register("clumsy", () -> AttackSpeedEffects.c);
                }
            }
            if(mf.getFeatureName().equals("Kritz")){
                if(KritzFeature.enabled && KritzFeature.addAttributes){
                    KritzFeature.MELEE_CRIT_CHANCE = ATTRIBUTES.register("melee_crit_chance", () -> new PercentageAttribute("attribute.name.trains_tweaks.melee_crit_chance", KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true)).getDelegate();
                    KritzFeature.RANGED_CRIT_CHANCE = ATTRIBUTES.register("ranged_crit_chance", () -> new PercentageAttribute("attribute.name.trains_tweaks.ranged_crit_chance", KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true)).getDelegate();
                    //add to player
                    eventBus.addListener(this::registerAttributesToPlayer);
                }
                if(KritzFeature.enabled && KritzFeature.addEffects){
                    KritzFeature.MELEE_CRIT_EFFECT = MOB_EFFECTS.register("melee_fury", () -> KritzEffects.me);
                    KritzFeature.RANGED_CRIT_EFFECT = MOB_EFFECTS.register("ranged_fury", () -> KritzEffects.re);
                }
            }
            if(mf.getFeatureName().equals("UtilityCommands") && UtilityCommandsFeature.enabled){
                NeoForge.EVENT_BUS.addListener(this::registerCommands);
            }

        }
        CommonClass.init();
    }

    public void registerCommands(RegisterCommandsEvent event) {
        if(UtilityCommandsFeature.addKillNonPlayer){
            KillNonPlayersCommand.register(event.getDispatcher());
        }
    }

    public void registerAttributesToPlayer(EntityAttributeModificationEvent event){
        event.add(EntityType.PLAYER, KritzFeature.MELEE_CRIT_CHANCE);
        event.add(EntityType.PLAYER, KritzFeature.RANGED_CRIT_CHANCE);
    }

}