package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.trigger.DayTrigger;
import info.partonetrain.trains_tweaks.feature.trigger.GameTimeTrigger;
import info.partonetrain.trains_tweaks.feature.trigger.TriggerFeature;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CriteriaTriggers.class)
public class Trigger_CriteriaTriggersMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void trains_tweaks$clinit(CallbackInfo ci){
        //NeoForge doesn't complain about this, but it might in the future!
        TriggerFeature.GAME_TIME_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.GAME_TIME_TRIGGER, new GameTimeTrigger());
        TriggerFeature.DAY_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.DAY_TRIGGER, new DayTrigger());
    }
}
