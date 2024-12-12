package info.partonetrain.trains_tweaks.feature.cure;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CureFeature extends ModFeature {

    public static boolean effectsParsed = false;
    public static List<Holder.Reference<MobEffect>> parsedWeakeningEffects = new ArrayList<net.minecraft.core.Holder.Reference<MobEffect>>();

    public CureFeature()
    {
        super("Cure", CureFeatureConfig.SPEC);
    }

    public static void parseWeakeningEffects(){
        if(!effectsParsed){
            List<String> effectIDs = Arrays.stream(CureFeatureConfig.WEAKENING_EFFECTS.get().split(",")).toList();

            try{
                for(String effectID : effectIDs){//
                    parsedWeakeningEffects.add(BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(ResourceKey.create(Registries.MOB_EFFECT, ResourceLocation.parse(effectID))));
                }
                effectsParsed = true;
                Constants.LOG.info("Parsed " + effectIDs.size() + " effects");
            }
            catch (ResourceLocationException e){
                Constants.LOG.error("Invalid effect: " + e);
            }
        }
    }
}
