package info.partonetrain.trains_tweaks.feature.bee;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import info.partonetrain.trains_tweaks.feature.cure.CureFeatureConfig;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeeFeature extends ModFeature {

    public BeeFeature()
    {
        super("Bee", BeeFeatureConfig.SPEC);
    }

}
