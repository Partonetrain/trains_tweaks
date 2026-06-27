package info.partonetrain.trains_tweaks.feature.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

public class SystemDayOfWeekTrigger extends SimpleCriterionTrigger<SystemDayOfWeekTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return SystemDayOfWeekTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player){
        this.trigger(player, TriggerInstance::matches);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints day_number) implements SimpleInstance {
        public static final Codec<SystemDayOfWeekTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337396_ -> p_337396_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SystemDayOfWeekTrigger.TriggerInstance::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("day_number", MinMaxBounds.Ints.between(1, 7))
                            .validate(SystemDayOfWeekTrigger::validateDay).forGetter(SystemDayOfWeekTrigger.TriggerInstance::day_number)
                ).apply(p_337396_, SystemDayOfWeekTrigger.TriggerInstance::new));


        public boolean matches(){
            //1: monday
            //7: sunday
            return day_number.matches(LocalDate.now().getDayOfWeek().getValue());
        }
    }

    public static DataResult<MinMaxBounds.Ints> validateDay(MinMaxBounds.Ints day){
        try{
            if(day.min().isPresent()){
                DayOfWeek.of(day.min().get());
            }
            if(day.max().isPresent()){
                DayOfWeek.of(day.max().get());
            }
        }
        catch (DateTimeException e){
            return DataResult.error(() -> "Invalid day of week: " + day);
        }

        return DataResult.success(day);
    }
}
