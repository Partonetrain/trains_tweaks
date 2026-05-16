package info.partonetrain.trains_tweaks.feature.interdimensional;

import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.*;

public class InterdimensionalFeature extends ModFeature {

    public static boolean effectRestrictionsParsed = false;
    public static List<EffectRestriction> restrictions = new ArrayList<>();

    public static List<BlockPos> endGatewaySpawns = new ArrayList<>();

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
        Optional<Registry<DimensionType>> dimTypeRegistry = level.registryAccess().registry(Registries.DIMENSION_TYPE);
        Optional<Registry<MobEffect>> effectRegistry = level.registryAccess().registry(Registries.MOB_EFFECT);

        for(EffectRestriction er : restrictions){
            ResourceKey<DimensionType> currentDimType = ResourceKey.create(Registries.DIMENSION_TYPE, Objects.requireNonNull(dimTypeRegistry.get().getKey(serverPlayer.level().dimensionType())));
            ResourceKey<Level> currentDim = serverPlayer.level().dimension();
            //is the player in the right dimension?
            //dimension type might not have the same resourcelocation as dimension/level, so check them both
            if(er.dimensionType == currentDimType || er.dimensionType.location().equals(currentDim.location())){
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
                            serverPlayer.sendSystemMessage(makeComponent(mei.getEffect().value(), currentDimType, holder, serverPlayer));
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

        //twilightforest (and possibly others) dimension type has _type suffix
        if(dimensionTypeResourceKey.location().toString().contains("_type")){
            String s = dimensionTypeResourceKey.location().toString();
            dimensionTypeResourceKey = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.parse(s.replace("_type", "")));
        }

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

    public static List<BlockPos> parseEndGatewaySpawns(){
        if (endGatewaySpawns.isEmpty()){
            if(InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.get().equals(InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.getDefault()))
            {
                //the config is default. return nothing
                //this is technically already checked for when called...
                return Collections.emptyList();
            }

            //start parsing
            String[] split = InterdimensionalFeatureConfig.DRAGON_FIGHT_GATEWAY_SPAWNS.get().strip().split("[;|]");
            for (String s : split){
                String[] coords = s.split(",");
                try{
                    int x = Integer.parseInt(coords[0].strip());
                    int y = Integer.parseInt(coords[1].strip());
                    int z = Integer.parseInt(coords[2].strip());
                    BlockPos pos = new BlockPos(x, y, z);
                    endGatewaySpawns.add(pos);
                }
                catch(ArrayIndexOutOfBoundsException | NumberFormatException e){
                    Constants.LOG.info("Failed to parse coordinate for end gateway: " + e.getMessage());
                    return Collections.emptyList();
                }
            }
        }
        //at this point endGatewaySpawns is not empty
        //we must have already parsed successfully
        return endGatewaySpawns;
    }
}
