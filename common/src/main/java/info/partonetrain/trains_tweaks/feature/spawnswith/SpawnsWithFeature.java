package info.partonetrain.trains_tweaks.feature.spawnswith;

import com.google.common.collect.Maps;
import info.partonetrain.trains_tweaks.CommonClass;
import info.partonetrain.trains_tweaks.Constants;
import info.partonetrain.trains_tweaks.ModFeature;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentTable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.*;

public class SpawnsWithFeature extends ModFeature {
    /*
    * As far as I can tell,
    * Mojang extended the loot table system for making mobs spawning with equipment data-driven,
    * changed the signature of SpawnData constructor to include it,
    * and then proceeded to only use it for trial chambers instead of replacing the hardcoded equipment
    * overrides of Mob#populateDefaultEquipmentSlots and Mob#finalizeSpawn.
    * I assume they did this because they didn't want to embed difficulty conditions
    * or drop chances into a new type of loot table, but if I were them
    * (which I'm not, and I admit my naivety here)
    * I would have simply made data-driven equipment tables,
    * instead of how it is now:
    * - some mobs override the aforementioned methods
    * - trial spawned mobs use equipment tables derived from both data-driven loot tables and hardcoded values
    *
    * This tweak is sort of my solution to this conundrum. It's not super elegant,
    * but it provides far more customization than the vanilla game allows.
    */

    /*
     * update - well they probably didn't because turns out rolling loot tables during worldgen isn't safe
     * who woulda thought?
     */

    public static List<EquipmentSlot> armorSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
    public static List<EquipmentSlot> allSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);

    public static final String TABLE_PREFIX = "trains_tweaks:equipment/";
    public static final String MAINHAND_SUFFIX = "_main_hand";
    public static final String OFFHAND_SUFFIX = "_off_hand";
    public static final String ARMOR_SUFFIX = "_armor";

    public SpawnsWithFeature() {
        super("SpawnsWith", SpawnsWithFeatureConfig.SPEC);
    }

    public static void rollGenericTable(LivingEntity livingEntity){
        if(livingEntity instanceof Mob mob){
            if(mob.level() instanceof ServerLevel serverLevel) {
                clearVanillaGear(mob, EquipmentTableType.ARMOR);
                LootParams.Builder builder = new LootParams.Builder(serverLevel);
                builder.withLuck(serverLevel.getCurrentDifficultyAt(mob.getOnPos()).getEffectiveDifficulty())
                        .withParameter(LootContextParams.ORIGIN, mob.position())
                        .withParameter(LootContextParams.THIS_ENTITY, mob);
                LootParams paramsWithLuck = builder.create(LootContextParamSets.EQUIPMENT);
                mob.equip(Constants.GENERIC_EQUIPMENT_LOOT_TABLE, paramsWithLuck, createDropChanceMap());
            }
        }
        else{
            Constants.LOG.info(livingEntity.getType().toString() + " was not a LivingEntity");
        }
        //TODO non mob livingentities
    }

    public static ResourceLocation getEntityResourceLocation(LivingEntity livingEntity){
        return BuiltInRegistries.ENTITY_TYPE.getKey(livingEntity.getType());
    }

    public static Map<EquipmentTableType, LootTable> findLootTables(ServerLevel serverLevel, ResourceLocation entityKey){
        Map<EquipmentTableType, LootTable> map = new HashMap<>();
        Map<EquipmentTableType, ResourceLocation> rlsToFind = makeLootTableIds(entityKey);
        for(EquipmentTableType ett : EquipmentTableType.values()){
            LootTable table = serverLevel.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, rlsToFind.get(ett)));
            if(table != LootTable.EMPTY){ //table was found
                map.put(ett, table);
                CommonClass.printInDev("found " + rlsToFind.get(ett).toString());
            }
        }

        return map;
    }

    public static Map<EquipmentTableType, ResourceLocation> makeLootTableIds(ResourceLocation entityKey){
        Map<EquipmentTableType, ResourceLocation> map = new HashMap<>();
        String namespace = entityKey.getNamespace();
        String path = entityKey.getPath();
        ResourceLocation mainhand = ResourceLocation.parse(TABLE_PREFIX + namespace + "/" + path + MAINHAND_SUFFIX);
        ResourceLocation offhand = ResourceLocation.parse(TABLE_PREFIX + namespace + "/" + path + OFFHAND_SUFFIX);
        ResourceLocation armor = ResourceLocation.parse(TABLE_PREFIX + namespace + "/" + path + ARMOR_SUFFIX);
        map.put(EquipmentTableType.MAIN_HAND, mainhand);
        map.put(EquipmentTableType.OFF_HAND, offhand);
        map.put(EquipmentTableType.ARMOR, armor);

        return map;
    }

    //creates a drop chance map for armor slots
    public static Map<EquipmentSlot, Float> createDropChanceMap() {
        float dropChance = (float) SpawnsWithFeatureConfig.EQUIPMENT_TABLE_DROP_CHANCE.getAsDouble();

        Map<EquipmentSlot, Float> map = Maps.newHashMap();
        for(EquipmentSlot e : armorSlots){
            map.put(e, dropChance);
        }
        return map;
    }

    @Deprecated
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

    @Deprecated
    public static void equipMobWithRolledStacks(List<ItemStack> rolledStacks, Mob mob, EquipType equipType){
        List<ItemStack> populatedStacks = new ArrayList<>();
        //clearVanillaGear(mob, equipType);
        if(!rolledStacks.isEmpty()) {
            if(equipType == EquipType.MAIN_HAND_ONLY || equipType == EquipType.BOTH_HANDS) {
                ItemStack first = rolledStacks.get(0);
                mob.setItemSlot(EquipmentSlot.MAINHAND, first);
                mob.setDropChance(EquipmentSlot.MAINHAND, (float) SpawnsWithFeatureConfig.EQUIPMENT_TABLE_DROP_CHANCE.getAsDouble());
                populatedStacks.add(first);

                if (rolledStacks.size() != 1 && equipType == EquipType.BOTH_HANDS) {
                    ItemStack second = rolledStacks.get(1);
                    mob.setItemSlot(EquipmentSlot.OFFHAND, second);
                    mob.setDropChance(EquipmentSlot.OFFHAND, (float) SpawnsWithFeatureConfig.EQUIPMENT_TABLE_DROP_CHANCE.getAsDouble());
                    populatedStacks.add(second);
                }
            }
            else if(equipType == EquipType.OFF_HAND_ONLY){
                ItemStack first = rolledStacks.get(0);
                mob.setItemSlot(EquipmentSlot.OFFHAND, first);
                mob.setDropChance(EquipmentSlot.OFFHAND, (float) SpawnsWithFeatureConfig.EQUIPMENT_TABLE_DROP_CHANCE.getAsDouble());
                populatedStacks.add(first);
            }
            else if(equipType == EquipType.ARMOR_ONLY){
                for(int i = 0; i <= rolledStacks.size() - 1; i++){
                    ItemStack itemStack = rolledStacks.get(i);
                    EquipmentSlot slot = mob.getEquipmentSlotForItem(itemStack);
                    mob.setItemSlot(slot, itemStack);
                    mob.setDropChance(slot, (float) SpawnsWithFeatureConfig.EQUIPMENT_TABLE_DROP_CHANCE.getAsDouble());
                    populatedStacks.add(itemStack);
                }
            }
        }
        //drop any extra items on the ground
        //this is not tested. It's best if the loot table
        //can only ever generate the amount of stacks it expects.
        for (ItemStack itemStack : rolledStacks) {
            if (!populatedStacks.contains(itemStack)) {
                mob.spawnAtLocation(itemStack);
            }
        }
    }

    public static void clearVanillaGear(Mob mob, EquipmentTableType equipmentTableType){
        if(equipmentTableType == EquipmentTableType.MAIN_HAND){
            mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        else if(equipmentTableType == EquipmentTableType.OFF_HAND){
            mob.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        }
        else if(equipmentTableType == EquipmentTableType.ARMOR){
            mob.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
            mob.setItemSlot(EquipmentSlot.CHEST, ItemStack.EMPTY);
            mob.setItemSlot(EquipmentSlot.LEGS, ItemStack.EMPTY);
            mob.setItemSlot(EquipmentSlot.FEET, ItemStack.EMPTY);
        }
    }
}
