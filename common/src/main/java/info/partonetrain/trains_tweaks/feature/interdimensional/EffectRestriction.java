package info.partonetrain.trains_tweaks.feature.interdimensional;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.dimension.DimensionType;

public class EffectRestriction {
    public ResourceKey<MobEffect> effect;
    public ResourceKey<DimensionType> dimension;
    public ResourceKey<Advancement> unlockingAdvancement;

    public EffectRestriction(ResourceKey<MobEffect> effect, ResourceKey<DimensionType> dimension, ResourceKey<Advancement> unlockingAdvancement){
        this.effect = effect;
        this.dimension = dimension;
        this.unlockingAdvancement = unlockingAdvancement;
    }
}
