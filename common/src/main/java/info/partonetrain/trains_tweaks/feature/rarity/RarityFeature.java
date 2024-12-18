package info.partonetrain.trains_tweaks.feature.rarity;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class RarityFeature extends ModFeature {
    public RarityFeature() {
        super("Rarity", RarityFeatureConfig.SPEC);
        incompatibleMods.add("customrarity");
    }

    public static Rarity setTaggedRarity(ItemStack stack){
        if(RarityFeatureConfig.DATA_TAG_ENABLED.getAsBoolean()){
            if (stack.is(Constants.COMMON_TAG) && !stack.get(DataComponents.RARITY).equals(Rarity.COMMON)) {
                stack.set(DataComponents.RARITY, Rarity.COMMON);
            }
            else if(stack.is(Constants.UNCOMMON_TAG) && !stack.get(DataComponents.RARITY).equals(Rarity.UNCOMMON)){
                stack.set(DataComponents.RARITY, Rarity.UNCOMMON);
            }
            else if(stack.is(Constants.RARE_TAG) && !stack.get(DataComponents.RARITY).equals(Rarity.RARE)){
                stack.set(DataComponents.RARITY, Rarity.RARE);
            }
            else if(stack.is(Constants.EPIC_TAG) && !stack.get(DataComponents.RARITY).equals(Rarity.EPIC)){
                stack.set(DataComponents.RARITY, Rarity.EPIC);
            }
            else if(RarityFeatureConfig.RESTORE_DEFAULT.getAsBoolean()){
                //avoid calling getRarity here because we may be in that method
                if(!stack.getItem().getDefaultInstance().get(DataComponents.RARITY).equals(stack.get(DataComponents.RARITY))){
                    stack.set(DataComponents.RARITY, stack.getItem().getDefaultInstance().get(DataComponents.RARITY));
                }
            }
        }
        return stack.getOrDefault(DataComponents.RARITY, Rarity.COMMON);
    }
}
