package info.partonetrain.trains_tweaks.feature.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import info.partonetrain.trains_tweaks.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class RandomizeExcludeFunction extends LootItemConditionalFunction {

    public static final MapCodec<RandomizeExcludeFunction> CODEC = RecordCodecBuilder.mapCodec(
            recordCodecBuilder -> commonFields(recordCodecBuilder)
                    .and(
                            recordCodecBuilder.group(
                                    TagKey.hashedCodec(Registries.ITEM).listOf().optionalFieldOf("exclude").forGetter(randomizeFunction -> randomizeFunction.exclude),
                                    Codec.INT.optionalFieldOf("max_tries", 500).forGetter(randomizeFunction -> randomizeFunction.max_tries)
                            )
                    )
                    .apply(recordCodecBuilder, RandomizeExcludeFunction::new)
    );
    private final Optional<List<TagKey<Item>>> exclude;
    private final int max_tries;

    RandomizeExcludeFunction(List<LootItemCondition> conditons, Optional<List<TagKey<Item>>> exclude, int maxTries) {
        super(conditons);
        this.exclude = exclude;
        this.max_tries = maxTries;
    }

    public @NotNull LootItemFunctionType<RandomizeExcludeFunction> getType() {
        return LootFeature.RANDOMIZE_EXCLUDE_FUNCTION;
    }

    public @NotNull ItemStack run(ItemStack stack, LootContext context) {
        RandomSource randomsource = context.getRandom();
        int tries = 0;
        final int MAX_ITEMS = BuiltInRegistries.ITEM.keySet().size();

        while (tries < max_tries){
            Item item = BuiltInRegistries.ITEM.getRandom(randomsource).get().value();

            ItemStack itemStack = item.getDefaultInstance();
            //Constants.LOG.info("RandomizeExcludeFunction: randomly chose a " + itemStack.getDisplayName().getString());

            boolean good = true;
            if(exclude.isPresent()) {
                List<TagKey<Item>> tags = exclude.get();
                for(TagKey<Item> itemTag : tags){
                    if(itemStack.is(itemTag)){
                        //Constants.LOG.info("RandomizeExcludeFunction: " + itemStack.getDisplayName().getString() + " was in " + itemTag.location().toString() + ". try: " + tries);
                        tries++;
                        good = false;
                        break;
                    }
                }
            }
            if(!good){
                continue; //retry
            }

            return stack.transmuteCopy(item); //keeps components applied from other functions.
        }

        Constants.LOG.info("RandomizeExcludeFunction: could not find an item that wasn't in exclude");
        return stack;
    }
}
