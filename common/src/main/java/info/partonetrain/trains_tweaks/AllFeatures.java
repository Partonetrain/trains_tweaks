package info.partonetrain.trains_tweaks;

import info.partonetrain.trains_tweaks.feature.attackspeed.AttackSpeedFeature;
import info.partonetrain.trains_tweaks.feature.bee.BeeFeature;
import info.partonetrain.trains_tweaks.feature.cure.CureFeature;
import info.partonetrain.trains_tweaks.feature.difficulty.DifficultyFeature;
import info.partonetrain.trains_tweaks.feature.experience.ExperienceFeature;
import info.partonetrain.trains_tweaks.feature.fireresistant.FireResistantFeature;
import info.partonetrain.trains_tweaks.feature.mobdrops.MobDropsFeature;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeature;
import info.partonetrain.trains_tweaks.feature.powderwalking.PowderWalkingFeature;
import info.partonetrain.trains_tweaks.feature.spawnswith.SpawnsWithFeature;
import info.partonetrain.trains_tweaks.feature.ocelot.OcelotFeature;
import info.partonetrain.trains_tweaks.feature.rarity.RarityFeature;
import info.partonetrain.trains_tweaks.feature.trigger.TriggerFeature;
import info.partonetrain.trains_tweaks.feature.utilitycommands.UtilityCommandsFeature;
import info.partonetrain.trains_tweaks.feature.vehicle.VehicleFeature;
import info.partonetrain.trains_tweaks.feature.wolf.WolfFeature;
import info.partonetrain.trains_tweaks.feature.xplosiv.XplosivFeature;
import info.partonetrain.trains_tweaks.feature.yeet.YeetFeature;
import info.partonetrain.trains_tweaks.feature.zzz.ZzzFeature;

import java.util.ArrayList;
import java.util.List;

public class AllFeatures {

    public static List<ModFeature> features = new ArrayList<>();

    public static final ModFeature ATTACK_SPEED_FEATURE = new AttackSpeedFeature();
    public static final ModFeature BEE_FEATURE = new BeeFeature();
    public static final ModFeature CURE_FEATURE = new CureFeature();
    public static final ModFeature DIFFICULTY_FEATURE = new DifficultyFeature();
    public static final ModFeature EXPERIENCE_FEATURE = new ExperienceFeature();
    public static final ModFeature FIRE_RESISTANT_FEATURE = new FireResistantFeature();
    public static final ModFeature MOB_DROPS_FEATURE = new MobDropsFeature();
    public static final ModFeature NPC_FEATURE = new NpcFeature();
    public static final ModFeature OCELOT_FEATURE = new OcelotFeature();
    public static final ModFeature POWDER_WALKING_FEATURE = new PowderWalkingFeature();
    public static final ModFeature RARITY_FEATURE = new RarityFeature();
    public static final ModFeature SPAWNS_WITH_FEATURE = new SpawnsWithFeature();
    public static final ModFeature TRIGGER_FEATURE = new TriggerFeature();
    public static final ModFeature UTILITY_COMMANDS_FEATURE = new UtilityCommandsFeature();
    public static final ModFeature VEHICLE_FEATURE = new VehicleFeature();
    public static final ModFeature WOLF_FEATURE = new WolfFeature();
    public static final ModFeature XPLOSIV_FEATURE = new XplosivFeature();
    public static final ModFeature YEET_FEATURE = new YeetFeature();
    public static final ModFeature ZZZ_FEATURE = new ZzzFeature();

    static{
        features.add(ATTACK_SPEED_FEATURE);
        features.add(BEE_FEATURE);
        features.add(CURE_FEATURE);
        features.add(DIFFICULTY_FEATURE);
        features.add(EXPERIENCE_FEATURE);
        features.add(FIRE_RESISTANT_FEATURE);
        features.add(MOB_DROPS_FEATURE);
        features.add(NPC_FEATURE);
        features.add(OCELOT_FEATURE);
        features.add(POWDER_WALKING_FEATURE);
        features.add(RARITY_FEATURE);
        features.add(SPAWNS_WITH_FEATURE);
        features.add(TRIGGER_FEATURE);
        features.add(UTILITY_COMMANDS_FEATURE);
        features.add(VEHICLE_FEATURE);
        features.add(WOLF_FEATURE);
        features.add(XPLOSIV_FEATURE);
        features.add(YEET_FEATURE);
        features.add(ZZZ_FEATURE);
    }
}
