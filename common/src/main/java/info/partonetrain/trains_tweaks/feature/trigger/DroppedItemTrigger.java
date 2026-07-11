package info.partonetrain.trains_tweaks.feature.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class DroppedItemTrigger extends SimpleCriterionTrigger<DroppedItemTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<TriggerInstance> codec() {
        return DroppedItemTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer, ItemStack stackBeforeDrop){
        this.trigger(serverPlayer, instance -> instance.matches(serverPlayer, stackBeforeDrop));
    }

    public static record TriggerInstance(Optional<ContextAwarePredicate> player, List<ItemPredicate> itemPredicates) implements SimpleInstance {
        public static final Codec<DroppedItemTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337396_ -> p_337396_.group(
                    EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DroppedItemTrigger.TriggerInstance::player),
                    ItemPredicate.CODEC.listOf().optionalFieldOf("items", List.of()).forGetter(DroppedItemTrigger.TriggerInstance::itemPredicates)
            ).apply(p_337396_, DroppedItemTrigger.TriggerInstance::new));

        public boolean matches(ServerPlayer serverPlayer, ItemStack stackBeforeDrop){
            if(serverPlayer.isDeadOrDying()){ //this is called when items burst out of a dead player lol
                return false;
            }
            for(ItemPredicate ip : itemPredicates){
                if(ip.test(stackBeforeDrop)){
                    return true;
                }
            }
            return false;
        }

    }
}
