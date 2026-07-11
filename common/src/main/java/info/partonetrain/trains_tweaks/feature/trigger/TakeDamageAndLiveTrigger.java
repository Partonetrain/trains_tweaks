package info.partonetrain.trains_tweaks.feature.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TakeDamageAndLiveTrigger extends SimpleCriterionTrigger<TakeDamageAndLiveTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return TakeDamageAndLiveTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int damageReceived){
        this.trigger(player, instance -> instance.matches(player, damageReceived));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints damage) implements SimpleInstance {
        public static final Codec<TakeDamageAndLiveTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337396_ -> p_337396_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TakeDamageAndLiveTrigger.TriggerInstance::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("damage", MinMaxBounds.Ints.between(Integer.MIN_VALUE, Integer.MAX_VALUE)).forGetter(TakeDamageAndLiveTrigger.TriggerInstance::damage)
            ).apply(p_337396_, TakeDamageAndLiveTrigger.TriggerInstance::new));


        public boolean matches(ServerPlayer player, int damageReceived){
            if(player.isDeadOrDying() || player.getHealth() <= 0){
                return false;
            }
            return damage.matches(damageReceived);
        }
    }

}
