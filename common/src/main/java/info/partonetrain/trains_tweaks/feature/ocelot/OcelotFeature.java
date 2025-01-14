package info.partonetrain.trains_tweaks.feature.ocelot;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.CatVariant;

import java.util.function.Predicate;

public class OcelotFeature extends ModFeature {

    public static boolean variantParsed = false;
    public static Holder<CatVariant> parsedCatVariant;

    public static final Predicate<LivingEntity> TARGET = (entity) -> entity.getType().is(Constants.OCELOT_HUNT_TARGETS);

    public OcelotFeature() {
        super("Ocelot", OcelotFeatureConfig.SPEC);
        //compatibleMods.add("");
        incompatibleMods.add("ocelotfix");
    }

    public static void parseCatVariant(){
        if(!variantParsed){
            Holder.Reference<CatVariant> variant;
            try {
                variant = BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(
                        ResourceKey.create(Registries.CAT_VARIANT, ResourceLocation.parse(OcelotFeatureConfig.FORCE_TYPE.get())));
                parsedCatVariant = variant;
                variantParsed = true;
            }
            catch (ResourceLocationException e){
                Constants.LOG.error("Invalid cat variant: " + e);
            }
        }
    }
}
