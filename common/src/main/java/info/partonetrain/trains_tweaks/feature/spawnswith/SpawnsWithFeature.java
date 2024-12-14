package info.partonetrain.trains_tweaks.feature.spawnswith;

import com.google.common.collect.Maps;
import info.partonetrain.trains_tweaks.ModFeature;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentTable;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpawnsWithFeature extends ModFeature {
    /*
    * As far as I can tell,
    * Mojang extended the loot table system for making mobs spawning with equipment data-driven,
    * changed the signature of SpawnData constructor to include it,
    * and then proceeded to only use it for trial chambers instead of replacing the hardcoded equipment
    * overrides of Mob#populateDefaultEquipmentSlots.
    * I assume they did this because they didn't want to embed difficulty conditions
    * or drop chances, but if I were them (which I'm not, and I admit my naivety here)
    * I would have simply made data-driven equipment tables,
    * instead of how it is now:
    * equipment tables derived from both data-driven loot tables and hardcoded values
    *
    * This tweak is sort of my solution to this conundrum. It's not super elegant,
    * but it provides far more customization than the vanilla game allows.
    */

    public static List<EquipmentSlot> armorSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
    public static List<EquipmentSlot> allSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);

    public SpawnsWithFeature() {
        super("SpawnsWith", SpawnsWithFeatureConfig.SPEC);
    }

    //creates a drop chance map that contains either armor slots or all slots depending on config and the configured drop chance
    public static Map<EquipmentSlot, Float> createDropChanceMap() {
        List<EquipmentSlot> equipmentSlots = SpawnsWithFeatureConfig.GENERIC_TABLE_ONLY_ARMOR.getAsBoolean() ? armorSlots : allSlots;
        float dropChance = (float) SpawnsWithFeatureConfig.GENERIC_TABLE_DROP_CHANCE.getAsDouble();

        Map<EquipmentSlot, Float> map = Maps.newHashMap();
        for(EquipmentSlot e : equipmentSlots){
            map.put(e, dropChance);
        }
        return map;
    }

    //this is used for
    public static List<ItemStack> getEquipmentFromLootTableForSpecificMob(Mob mob, ResourceKey<LootTable> tableKey){
        ServerLevel serverLevel = (ServerLevel) mob.level();
        float luck = serverLevel.getCurrentDifficultyAt(mob.blockPosition()).getEffectiveDifficulty();
        LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(tableKey);
        LootParams params = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, mob.position())
                .withParameter(LootContextParams.THIS_ENTITY, mob)
                .withLuck(luck)
                .create(LootContextParamSets.EQUIPMENT);

        ObjectArrayList<ItemStack> loot = lootTable.getRandomItems(params);
        return loot.stream().toList();
    }
}
