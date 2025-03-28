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

    //Sets rarity component, and then returns stack's rarity
    public static Rarity setTaggedRarity(ItemStack stack){
        boolean preventEnchantments = RarityFeatureConfig.PREVENT_ENCHANTMENT_ALTERING.getAsBoolean();
        if(RarityFeatureConfig.DATA_TAG_ENABLED.getAsBoolean()){
            if(stack.is(Constants.COMMON_TAG) && (!stack.get(DataComponents.RARITY).equals(Rarity.COMMON) || stack.isEnchanted() && !stack.get(DataComponents.RARITY).equals(Rarity.UNCOMMON)) ){
                stack.set(DataComponents.RARITY, (!preventEnchantments && stack.isEnchanted()) ? Rarity.UNCOMMON : Rarity.COMMON);
            }
            else if(stack.is(Constants.UNCOMMON_TAG) && (!stack.get(DataComponents.RARITY).equals(Rarity.UNCOMMON) || stack.isEnchanted() && !stack.get(DataComponents.RARITY).equals(Rarity.RARE)) ){
                stack.set(DataComponents.RARITY, (!preventEnchantments && stack.isEnchanted()) ? Rarity.RARE : Rarity.UNCOMMON);
            }
            else if(stack.is(Constants.RARE_TAG) && (!stack.get(DataComponents.RARITY).equals(Rarity.RARE) || stack.isEnchanted() && !stack.get(DataComponents.RARITY).equals(Rarity.EPIC)) ){
                stack.set(DataComponents.RARITY, (!preventEnchantments && stack.isEnchanted()) ? Rarity.EPIC : Rarity.RARE);
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

    //returns which rarity the stack is in the corresponding tag of
    public static Rarity getTaggedRarity(ItemStack stack){
        if(RarityFeatureConfig.ITEMSTACK_CHECK_TAG.getAsBoolean()){
            if (stack.is(Constants.COMMON_TAG)) {
                return Rarity.COMMON;
            }
            else if(stack.is(Constants.UNCOMMON_TAG)){
                return Rarity.UNCOMMON;
            }
            else if(stack.is(Constants.RARE_TAG)){
                return Rarity.RARE;
            }
            else if(stack.is(Constants.EPIC_TAG)){
                return Rarity.EPIC;
            }
        }
        return Rarity.COMMON;
    }
}
