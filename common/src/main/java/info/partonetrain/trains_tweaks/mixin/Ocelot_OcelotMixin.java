package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.ocelot.OcelotFeatureConfig;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ocelot.class)
public class Ocelot_OcelotMixin {
    @Inject(at = @At("RETURN"), method = "mobInteract")
    private void trains_tweaks$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if(OcelotFeatureConfig.ENABLED.getAsBoolean() || AllFeatures.OCELOT_FEATURE.isIncompatibleLoaded()){
            Ocelot ocelot = (Ocelot)(Object)this;
            if(ocelot.isTrusting())
            {
                Level level = ocelot.level();

                if (!level.isClientSide){
                    Cat cat = ocelot.convertTo(EntityType.CAT, true);
                    cat.tame(player); //this sets owner UUID
                    cat.setOrderedToSit(true);
                    cat.setXRot(ocelot.getXRot());
                    //this doesn't seem to actually work, it always faces south
                    //but as far as I can tell this is only a rendering thing?

                    if(!OcelotFeatureConfig.FORCE_TYPE.get().equals("none")){
                        Holder.Reference<CatVariant> variant;
                        try {
                            variant = BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(
                                    ResourceKey.create(Registries.CAT_VARIANT, ResourceLocation.parse(OcelotFeatureConfig.FORCE_TYPE.get())));

                            cat.setVariant(variant);
                        }
                        catch (ResourceLocationException e){
                            Constants.LOG.error("Invalid cat variant: " + e);
                        }

                    }

                }
            }
        }
    }
}
