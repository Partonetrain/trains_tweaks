package info.partonetrain.trains_tweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeature;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeatureConfig;
import info.partonetrain.trains_tweaks.platform.Services;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Set;

@Mixin(Villager.class)
public class Npc_VillagerMixin {
    @WrapOperation(method = "wantsToPickUp", at=@At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    public boolean trains_tweaks$wantsToPickUp(Set<Item> instance, Object obj, Operation<Boolean> original){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean() && NpcFeatureConfig.WANTED_ITEMS_TAG.getAsBoolean() && !Services.PLATFORM.isModLoaded("smarterfarmers")) {
            Item item = (Item)obj;
            return item.getDefaultInstance().is(Constants.VILLAGER_WANTS_TAG);
        }
        return original.call(instance, obj);
    }

    @ModifyArg(method = "onReputationEventFrom", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V", ordinal = 0), index = 2)
    public int trains_tweaks$onReputationEventFrom(int gossipValue){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            return NpcFeature.majorPositiveConfig[0];
        }
        return gossipValue;
    }

    @ModifyArg(method = "onReputationEventFrom", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V", ordinal = 1), index = 2)
    public int trains_tweaks$onReputationEventFrom2(int gossipValue){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            return NpcFeature.minorPositiveConfig[0];
        }
        return gossipValue;
    }

    @ModifyArg(method = "onReputationEventFrom", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V", ordinal = 2), index = 2)
    public int trains_tweaks$onReputationEventFrom3(int gossipValue){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            return NpcFeature.tradingConfig[0];
        }
        return gossipValue;
    }

    @ModifyArg(method = "onReputationEventFrom", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V", ordinal = 3), index = 2)
    public int trains_tweaks$onReputationEventFrom4(int gossipValue){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            return NpcFeature.minorNegativeConfig[0];
        }
        return gossipValue;
    } 
    
    @ModifyArg(method = "onReputationEventFrom", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V", ordinal = 4), index = 2)
    public int trains_tweaks$onReputationEventFrom5(int gossipValue){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            return NpcFeature.majorNegativeConfig[0];
        }
        return gossipValue;
    }
}
