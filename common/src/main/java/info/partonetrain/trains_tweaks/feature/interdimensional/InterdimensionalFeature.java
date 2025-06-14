package info.partonetrain.trains_tweaks.feature.interdimensional;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.advancements.AdvancementType;

import java.util.*;

public class InterdimensionalFeature extends ModFeature {

    public static boolean effectRestrictionsParsed = false;
    public static List<EffectRestriction> restrictions = new ArrayList<>();

    public InterdimensionalFeature()
    {
        super("Interdimensional", InterdimensionalFeatureConfig.SPEC);
        incompatibleMods.add("cryingportals");
        incompatibleMods.add("charm");
        incompatibleMods.add("frame_changer");
    }

    public static void parseEffectRestrictions(){
        String cfg = InterdimensionalFeatureConfig.EFFECT_RESTRICTIONS.get();
        List<String> split = List.of(cfg.split(";"));

        for(String s : split) {
            List<String> split2 = List.of(s.split(","));

            try {
                ResourceKey<MobEffect> eff = ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.parse(split2.get(0)));
                ResourceKey<DimensionType> dim = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.parse(split2.get(1)));
                ResourceKey<Advancement> adv = ResourceKey.create(Registries.ADVANCEMENT, ResourceLocation.parse(split2.get(2)));

                restrictions.add(new EffectRestriction(eff, dim, adv));
            } catch (Exception e) {
                Constants.LOG.error("Interdimensional EffectRestriction parse error: " + e.getMessage());
                restrictions.clear();
            }
        }

        Constants.LOG.info("effect restrictions parsed");
        effectRestrictionsParsed = true;
    }

    public static void applyEffectRestrictions(ServerPlayer serverPlayer){
        if(!effectRestrictionsParsed){
            parseEffectRestrictions();
        }

        ServerLevel level = (ServerLevel) serverPlayer.level();
        Optional<Registry<DimensionType>> dimRegistry = level.registryAccess().registry(Registries.DIMENSION_TYPE);
        Optional<Registry<MobEffect>> effectRegistry = level.registryAccess().registry(Registries.MOB_EFFECT);

        for(EffectRestriction er : restrictions){
            ResourceKey<DimensionType> currentDim = ResourceKey.create(Registries.DIMENSION_TYPE, Objects.requireNonNull(dimRegistry.get().getKey(serverPlayer.level().dimensionType())));
            //is the player in the right dimension?
            if(er.dimension == currentDim){
                CommonClass.printInDev("Player IS in dimension " + currentDim.location());
                Collection<MobEffectInstance> effects = serverPlayer.getActiveEffects();
                for(MobEffectInstance mei : effects){
                    ResourceKey<MobEffect> currentEffect = ResourceKey.create(Registries.MOB_EFFECT, Objects.requireNonNull(effectRegistry.get().getKey(mei.getEffect().value())));
                    //does the player have the right effect?
                    if(er.effect == currentEffect){
                        CommonClass.printInDev("Player DOES have effect " + currentEffect.location());
                        PlayerAdvancements advs = serverPlayer.getAdvancements();
                        AdvancementHolder holder = level.getServer().getAdvancements().get(er.unlockingAdvancement.location());
                        //does the player have the advancement?
                        if(advs.getOrStartProgress(holder).isDone()){
                            CommonClass.printInDev("Player already has advancement " + holder.value().name().toString());
                        }
                        else{
                            serverPlayer.sendSystemMessage(makeComponent(mei.getEffect().value(), currentDim, holder, serverPlayer));
                            serverPlayer.removeEffect(mei.getEffect());
                        }
                    }
                }
            }
        }
    }

    public static Component makeComponent(MobEffect me, ResourceKey<DimensionType> dimensionTypeResourceKey, AdvancementHolder unlockingAdvancement, ServerPlayer serverPlayer){
        String cfgString = InterdimensionalFeatureConfig.EFFECT_RESTRICTION_MESSAGE.get();
        if(cfgString.isEmpty()){
            return Component.empty();
        }

        MutableComponent ret = Component.empty();

        Optional<Component> meName = Optional.of(me.getDisplayName());
        Optional<Component> dtName = Optional.of(Component.translatable("dimension." + dimensionTypeResourceKey.location().toString().replace(":", ".")));
        Optional<Component> advName = unlockingAdvancement.value().name();

        if(cfgString.contains("$EFFECT")){
            String[] split = cfgString.split("\\$EFFECT");
            ret.append(split[0]);
            cfgString = split[1];
            ret.append(meName.get());
        }
        if(cfgString.contains("$DIMENSION")){
            String[] split = cfgString.split("\\$DIMENSION");
            ret.append(split[0]);
            cfgString = split[1];
            ret.append(dtName.get());
        }
        if(cfgString.contains("$ADVANCEMENT")){
            String[] split = cfgString.split("\\$ADVANCEMENT");
            ret.append(split[0]);
            ret.append(advName.get());
            //last one so add the rest
            ret.append(Component.literal(split[1]));
        }

        return ret.withStyle(ChatFormatting.ITALIC);
    }
}
