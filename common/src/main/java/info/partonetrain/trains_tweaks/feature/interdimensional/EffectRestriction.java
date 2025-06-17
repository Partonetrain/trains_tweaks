package info.partonetrain.trains_tweaks.feature.interdimensional;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.dimension.DimensionType;

public class EffectRestriction {
    public ResourceKey<MobEffect> effect;
    public ResourceKey<DimensionType> dimensionType;
    public ResourceKey<Advancement> unlockingAdvancement;

    public EffectRestriction(ResourceKey<MobEffect> effect, ResourceKey<DimensionType> dimensionType, ResourceKey<Advancement> unlockingAdvancement){
        this.effect = effect;
        this.dimensionType = dimensionType;
        this.unlockingAdvancement = unlockingAdvancement;
    }
}
