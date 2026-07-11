package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.IEarlyConfigReader;
import info.partonetrain.trains_tweaks.feature.trigger.*;
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
        IEarlyConfigReader ecr = (IEarlyConfigReader) (AllFeatures.TRIGGER_FEATURE);
        ecr.readConfigsEarly();
        if(TriggerFeature.enabled){
            if(TriggerFeature.gameTimeEnabled){
                TriggerFeature.GAME_TIME_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.GAME_TIME_TRIGGER, new GameTimeTrigger());
            }
            if(TriggerFeature.dayEnabled){
                TriggerFeature.DAY_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.DAY_TRIGGER, new DayTrigger());
            }
            if(TriggerFeature.systemDateEnabled){
                TriggerFeature.SYSTEM_DATE_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.SYSTEM_DATE_TRIGGER, new SystemDateTrigger());
            }
            if(TriggerFeature.glideEnabled){
                TriggerFeature.GLIDE_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.GLIDE_TRIGGER, new GlideTrigger());
            }
            if(TriggerFeature.takeDamageAndLiveEnabled){
                TriggerFeature.TAKE_DAMAGE_AND_LIVE_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.TAKE_DAMAGE_AND_LIVE_TRIGGER, new TakeDamageAndLiveTrigger());
            }
            if(TriggerFeature.systemDayOfWeekEnabled){
                TriggerFeature.SYSTEM_DAY_OF_WEEK_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.SYSTEM_DAY_OF_WEEK_TRIGGER, new SystemDayOfWeekTrigger());
            }
            if(TriggerFeature.dropItemEnabled){
                TriggerFeature.DROPPED_ITEM_TRIGGER = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.DROPPED_ITEM_TRIGGER, new DroppedItemTrigger());
            }
        }
    }
}
