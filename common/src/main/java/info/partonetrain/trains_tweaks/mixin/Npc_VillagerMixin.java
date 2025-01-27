package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeatureConfig;
import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(Villager.class)
public class Npc_VillagerMixin {
    @WrapOperation(method = "wantsToPickUp", at= @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    public boolean trains_tweaks$wantsToPickUp(Set<Item> instance, Object obj, Operation<Boolean> original){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()&& NpcFeatureConfig.WANTED_ITEMS_TAG.getAsBoolean() && !Services.PLATFORM.isModLoaded("smarterfarmers")) {
            Item item = (Item)obj;
            return item.getDefaultInstance().is(Constants.VILLAGER_WANTS_TAG);
        }
        return original.call(instance, obj);
    }
}
