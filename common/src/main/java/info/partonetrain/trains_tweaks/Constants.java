package info.partonetrain.trains_tweaks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Constants {

	public static final String MOD_ID = "trains_tweaks";
	public static final String MOD_NAME = "Train's Tweaks";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	//Cure
	public static final TagKey<Item> CURING_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "curing_items"));
	public static final TagKey<Item> INSTANT_CURING_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "instant_curing_items"));
	//FireResistant
	public static final TagKey<Item> FIRE_RESISTANT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "fire_resistant"));
	public static final TagKey<Item> NOT_FIRE_RESISTANT_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "not_fire_resistant"));
	//Loot
	public static final ResourceLocation ENCHANT_TREASURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "enchant_treasure");
	public static final ResourceLocation ENCHANT_CURSE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "enchant_curse");
	//MobDrops
	public static ResourceKey<LootTable> STAR_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entities/extended_wither_drop"));
	public static ResourceKey<LootTable> ROSE_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gameplay/wither_rose_drop"));
	public static ResourceKey<LootTable> EGG_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gameplay/chicken_lay"));
	public static TagKey<EntityType<?>> ROSE_KILLER_TAG = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "causes_wither_rose_drop"));
	//PowderWalking
	public static final TagKey<Item> POWDER_WALKER_ARMOR_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "powder_walking_armor"));
	public static final TagKey<Item> POWDER_WALKER_ITEM_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "powder_walking_item"));
	//Rarity
	public static final TagKey<Item> COMMON_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "common"));
	public static final TagKey<Item> UNCOMMON_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "uncommon"));
	public static final TagKey<Item> RARE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rare"));
	public static final TagKey<Item> EPIC_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "epic"));
	//SpawnsWith
	public static ResourceKey<LootTable> GENERIC_EQUIPMENT_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/generic"));
	public static ResourceKey<LootTable> ABSTRACT_SKELETON_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/abstract_skeleton"));
	public static ResourceKey<LootTable> DROWNED_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/drowned"));
	public static ResourceKey<LootTable> FOX_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/fox"));
	public static ResourceKey<LootTable> PIGLIN_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/piglin"));
	public static ResourceKey<LootTable> PIGLIN_BRUTE_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/piglin_brute"));
	public static ResourceKey<LootTable> PILLAGER_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/pillager"));
	public static ResourceKey<LootTable> VEX_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/vex"));
	public static ResourceKey<LootTable> VINDICATOR_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/vindicator"));
	public static ResourceKey<LootTable> WITHER_SKELETON_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/wither_skeleton"));
	public static ResourceKey<LootTable> ZOMBIE_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/zombie"));
	public static ResourceKey<LootTable> ZOMBIFIED_PIGLIN_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "equipment/zombified_piglin"));

}