package info.partonetrain.trains_tweaks.feature.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DayTrigger extends SimpleCriterionTrigger<DayTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return DayTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player){
        this.trigger(player, instance -> instance.matches(player));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MinMaxBounds.Ints> days) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<DayTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337396_ -> p_337396_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DayTrigger.TriggerInstance::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("day").forGetter(DayTrigger.TriggerInstance::days)
            ).apply(p_337396_, DayTrigger.TriggerInstance::new));

        public static Criterion<DayTrigger.TriggerInstance> checkDay(EntityPredicate.Builder player) {
            return TriggerFeature.DAY_TRIGGER.createCriterion(new DayTrigger.TriggerInstance(Optional.of(EntityPredicate.wrap(player)), Optional.empty()));
        }

        public boolean matches(ServerPlayer player){
            long time = player.level().getDayTime();
            int dayNumber = (int) (time / 24000);
            //Constants.LOG.info(String.valueOf(dayNumber));
            if(days.isPresent()){
                return days.get().matches(dayNumber);
            }
            return false;
        }
    }
}
