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
import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

public class SystemDateTrigger extends SimpleCriterionTrigger<SystemDateTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return SystemDateTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player){
        this.trigger(player, TriggerInstance::matches);
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints year, MinMaxBounds.Ints month, MinMaxBounds.Ints day) implements SimpleInstance {
        public static final Codec<SystemDateTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337396_ -> p_337396_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SystemDateTrigger.TriggerInstance::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("year", MinMaxBounds.Ints.between(Year.MIN_VALUE, Year.MAX_VALUE))
                            .validate(SystemDateTrigger::validateYear).forGetter(SystemDateTrigger.TriggerInstance::year),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("month", MinMaxBounds.Ints.between(1, 12))
                            .validate(SystemDateTrigger::validateMonth).forGetter(SystemDateTrigger.TriggerInstance::month),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("day", MinMaxBounds.Ints.between(1, 31))
                            .validate(SystemDateTrigger::validateDay).forGetter(SystemDateTrigger.TriggerInstance::day)
            ).apply(p_337396_, SystemDateTrigger.TriggerInstance::new));


        public boolean matches(){
            //I'm not 100% sure how but if any of these fields are not present the conditions seem to return true
            //if it works, it works...
            return year.matches(LocalDate.now().getYear()) && month.matches(LocalDate.now().getMonthValue()) && day.matches(LocalDate.now().getDayOfMonth());
        }
    }

    public static DataResult<MinMaxBounds.Ints> validateYear(MinMaxBounds.Ints year){
        if (year.min().get() < Year.MIN_VALUE || year.max().get() > Year.MAX_VALUE){
            return DataResult.error(() -> "Invalid year: " + year);
        }

        return DataResult.success(year);
    }

    public static DataResult<MinMaxBounds.Ints> validateMonth(MinMaxBounds.Ints month){
        if (month.min().get() < 1 || month.max().get() > 12){
            return DataResult.error(() -> "Invalid month: " + month);
        }

        return DataResult.success(month);
    }

    public static DataResult<MinMaxBounds.Ints> validateDay(MinMaxBounds.Ints day){
        if (day.min().get() < 1 || day.max().get() > 31){
            return DataResult.error(() -> "Invalid day: " + day);
        }
        return DataResult.success(day);
    }
}
