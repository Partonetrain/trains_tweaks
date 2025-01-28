package info.partonetrain.trains_tweaks.mixin;

import info.partonetrain.trains_tweaks.AllFeatures;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeature;
import info.partonetrain.trains_tweaks.feature.npc.NpcFeatureConfig;
import net.minecraft.world.entity.ai.gossip.GossipType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GossipType.class)
public class Npc_GossipTypeMixin {
    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipType;<init>(Ljava/lang/String;ILjava/lang/String;IIII)V", ordinal = 0))
    private static void trains_tweaks$clinit(Args args){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            int[] settings = NpcFeature.majorNegativeConfig;
            //value (amount gained) is set in Npc_VillagerMixin
            //enums are weird - first two args are always name and index
            args.set(3, settings[1]);
            args.set(4, settings[2]);
            args.set(5, settings[3]);
            args.set(6, settings[4]);
        }
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipType;<init>(Ljava/lang/String;ILjava/lang/String;IIII)V", ordinal = 1))
    private static void trains_tweaks$clinit2(Args args){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            int[] settings = NpcFeature.minorNegativeConfig;
            args.set(3, settings[1]);
            args.set(4, settings[2]);
            args.set(5, settings[3]);
            args.set(6, settings[4]);
        }
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipType;<init>(Ljava/lang/String;ILjava/lang/String;IIII)V", ordinal = 2))
    private static void trains_tweaks$clinit3(Args args){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            int[] settings = NpcFeature.minorPositiveConfig;
            args.set(3, settings[1]);
            args.set(4, settings[2]);
            args.set(5, settings[3]);
            args.set(6, settings[4]);
        }
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipType;<init>(Ljava/lang/String;ILjava/lang/String;IIII)V", ordinal = 3))
    private static void trains_tweaks$clinit4(Args args){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            int[] settings = NpcFeature.majorPositiveConfig;
            args.set(3, settings[1]);
            args.set(4, settings[2]);
            args.set(5, settings[3]);
            args.set(6, settings[4]);
        }
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/gossip/GossipType;<init>(Ljava/lang/String;ILjava/lang/String;IIII)V", ordinal = 4))
    private static void trains_tweaks$clinit5(Args args){
        if(!AllFeatures.NPC_FEATURE.isIncompatibleLoaded() && NpcFeatureConfig.ENABLED.getAsBoolean()) {
            if(!NpcFeature.configParsed) NpcFeature.ParseGossipConfigs();
            int[] settings = NpcFeature.tradingConfig;
            args.set(3, settings[1]);
            args.set(4, settings[2]);
            args.set(5, settings[3]);
            args.set(6, settings[4]);
        }
    }
}
