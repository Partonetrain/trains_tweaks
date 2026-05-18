package info.partonetrain.trains_tweaks.feature.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
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
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("year", MinMaxBounds.Ints.ANY).forGetter(SystemDateTrigger.TriggerInstance::year),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("month", MinMaxBounds.Ints.between(1, 12)).forGetter(SystemDateTrigger.TriggerInstance::month),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("day", MinMaxBounds.Ints.between(1, 31)).forGetter(SystemDateTrigger.TriggerInstance::day)
            ).apply(p_337396_, SystemDateTrigger.TriggerInstance::new));


        public boolean matches(){
            //I'm not 100% sure how but if any of these fields are not present the conditions seem to return true
            //if it works, it works...
            return year.matches(LocalDate.now().getYear()) && month.matches(LocalDate.now().getMonthValue()) && day.matches(LocalDate.now().getDayOfMonth());
        }
    }
}
