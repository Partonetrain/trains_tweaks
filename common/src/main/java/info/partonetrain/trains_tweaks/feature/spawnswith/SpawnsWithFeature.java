package info.partonetrain.trains_tweaks.feature.spawnswith;

import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.commands.arguments.ResourceOrIdArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.LootCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class SpawnsWithFeature extends ModFeature {
    /*Mojang made a whole system for making
    * mobs spawning with equipment data-driven,
    * changed the signature of SpawnData constructor to include it,
    * and then proceeded to only use it for trial chambers
    * instead of replacing the hardcoded equipment in overrides of
    * Mob#populateDefaultEquipmentSlots.
    *
    * This tweak sort of remedies that
    */

    public SpawnsWithFeature() {
        super("SpawnsWith", SpawnsWithFeatureConfig.SPEC);
    }

    public static List<ItemStack> getEquipmentFromLootTable(Mob mob, ResourceKey<LootTable> tableKey){
        ServerLevel serverLevel = (ServerLevel) mob.level();
        LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(tableKey);
        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, mob.position())
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .create(LootContextParamSets.EQUIPMENT);

        ObjectArrayList<ItemStack> loot = lootTable.getRandomItems(params);
        return loot.stream().toList();
    }
}
