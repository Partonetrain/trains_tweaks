package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.kritz.KritzFeature;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Attributes.class)
public class Fabric_Kritz_AttributesMixin {
    @Inject(method = "<clinit>", at=@At("TAIL"))
    private static void trains_tweaks$clinit(CallbackInfo ci){
        if(KritzFeature.enabled && KritzFeature.addEffects) {
            Attribute ma = new RangedAttribute(Constants.MELEE_CRIT_ATTRIBUTE_ID.toString(), KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true);
            Attribute ra = new RangedAttribute(Constants.MELEE_CRIT_ATTRIBUTE_ID.toString(), KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true);
            KritzFeature.MELEE_CRIT_CHANCE = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Constants.DEXTERITY_EFFECT_ID, ma);
            KritzFeature.RANGED_CRIT_CHANCE = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Constants.CLUMSY_EFFECT_ID, ra);
        }
    }
}
