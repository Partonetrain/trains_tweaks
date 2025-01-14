package info.partonetrain.trains_tweaks.feature.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GameTimeTrigger extends SimpleCriterionTrigger<GameTimeTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return GameTimeTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player){
        this.trigger(player, instance -> instance.matches(player));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<MinMaxBounds.Ints> gametime) implements SimpleInstance {
        public static final Codec<GameTimeTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337396_ -> p_337396_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(GameTimeTrigger.TriggerInstance::player),
                    MinMaxBounds.Ints.CODEC.optionalFieldOf("gametime").forGetter(GameTimeTrigger.TriggerInstance::gametime)
            ).apply(p_337396_, GameTimeTrigger.TriggerInstance::new));

        public static Criterion<GameTimeTrigger.TriggerInstance> checkGameTime(EntityPredicate.Builder player) {
            return TriggerFeature.GAME_TIME_TRIGGER.createCriterion(new GameTimeTrigger.TriggerInstance(Optional.of(EntityPredicate.wrap(player)), Optional.empty()));
        }


        public boolean matches(ServerPlayer player){
            int gameTime = (int) player.level().getGameTime();
            //long to int conversion -- max value should be 2147483647 (~12.5 real life days)
            if(gametime.isPresent()){
                return gametime.get().matches(gameTime);
            }
            return false;
        }
    }
}
