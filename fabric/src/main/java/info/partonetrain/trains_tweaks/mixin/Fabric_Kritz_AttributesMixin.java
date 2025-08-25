package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
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
        if(!KritzFeature.configRead){
            KritzFeature thisFeature = (KritzFeature) AllFeatures.KRITZ_FEATURE;
            thisFeature.readConfigsEarly();
        }
        if(KritzFeature.enabled && KritzFeature.addAttributes) {
            Attribute ma = new RangedAttribute("attribute.name.trains_tweaks.melee_crit_chance", KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true);
            Attribute ra = new RangedAttribute("attribute.name.trains_tweaks.ranged_crit_chance", KritzFeature.kritChance, 0.0D, 1.00D).setSyncable(true);
            KritzFeature.MELEE_CRIT_CHANCE = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Constants.MELEE_CRIT_ATTRIBUTE_ID, ma);
            KritzFeature.RANGED_CRIT_CHANCE = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Constants.RANGED_CRIT_ATTRIBUTE_ID, ra);
        }
    }
}
