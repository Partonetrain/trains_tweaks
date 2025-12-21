package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedEffects;
import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeatureConfig;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeature;
import info.partonetrain.trains_tweaks.feature.jumpy.JumpyFeatureConfig;
import info.partonetrain.trains_tweaks.feature.kritz.KritzEffects;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import info.partonetrain.trains_tweaks.feature.utilitycommands.KillNonPlayersCommand;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;

@Mod(Constants.MOD_ID)
public class TrainsTweaksNeoForge {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Constants.MOD_ID);
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Constants.MOD_ID);

    //Kritz
    DeferredHolder<Attribute, Attribute> meleeAttributeHolder;
    DeferredHolder<Attribute, Attribute> rangedAttributeHolder;

    public TrainsTweaksNeoForge(ModContainer container, IEventBus eventBus) {
        for(ModFeature mf : AllFeatures.features){
            if(mf instanceof IEarlyConfigReader earlyConfigReader && !earlyConfigReader.isExtraEarly()){
                //extra early = config read during minecraft init instead of mod init
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
            if(mf.getFeatureName().equals("Difficulty")){
                if(DifficultyFeature.enabled){
                    NeoForge.EVENT_BUS.addListener(this::modifyAppliedDamage);
                }
            }
            if(mf.getFeatureName().equals("Jumpy")){
                if(JumpyFeature.enabled){
                    NeoForge.EVENT_BUS.addListener(this::addJumpyGoals);
                }
            }
            if(mf.getFeatureName().equals("Kritz")){
                if(KritzFeature.enabled && KritzFeature.addAttributes){
                    meleeAttributeHolder = ATTRIBUTES.register("melee_crit_chance", () -> new PercentageAttribute("attribute.name.trains_tweaks.melee_crit_chance", KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true));
                    KritzFeature.MELEE_CRIT_CHANCE = meleeAttributeHolder.getDelegate();
                    rangedAttributeHolder = ATTRIBUTES.register("ranged_crit_chance", () -> new PercentageAttribute("attribute.name.trains_tweaks.ranged_crit_chance", KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true));
                    KritzFeature.RANGED_CRIT_CHANCE = rangedAttributeHolder.getDelegate();
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
        MOB_EFFECTS.register(eventBus);
        ATTRIBUTES.register(eventBus);
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

    //Difficulty_LivingEntityMixin technically still happens, but this is needed to actually change the damage output.
    public void modifyAppliedDamage(LivingDamageEvent.Pre event) {
        if(DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getAsDouble() != DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getDefault()){
            DamageSource source = event.getSource();

            if(source.getEntity() instanceof Player player){
                UUID uuid = player.getGameProfile().getId();
                boolean isModified = DifficultyFeature.modifiedPlayers.contains(uuid);
                if(isModified){
                    float newDmg = (float) (event.getOriginalDamage() * DifficultyFeatureConfig.MODIFIED_DIFFICULTY_DAMAGE_MULTIPLIER.getAsDouble());
                    event.setNewDamage(newDmg);
                }
            }
        }
    }

    public void addJumpyGoals(EntityJoinLevelEvent ejle){
        if(ejle.getLevel() instanceof ServerLevel ){
            if(JumpyFeatureConfig.JUMP_WHILE_MOVING.getAsBoolean()) {
                if (ejle.getEntity() instanceof Mob mob && mob.getType().is(Constants.JUMPS_WHILE_MOVING_TAG)) {
                    JumpyFeature.addJumpWhileMovingGoal(mob);
                }
            }

            if(JumpyFeatureConfig.JUMP_RANDOMLY.getAsBoolean()) {
                if (ejle.getEntity() instanceof Mob mob && mob.getType().is(Constants.JUMPS_RANDOMLY_TAG)) {
                    JumpyFeature.addJumpRandomlyGoal(mob);
                }
            }
        }
    }

}