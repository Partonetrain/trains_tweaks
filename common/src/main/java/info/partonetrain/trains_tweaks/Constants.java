package info.partonetrain.trains_tweaks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Constants {

	public static final String MOD_ID = "trains_tweaks";
	public static final String MOD_NAME = "Train's Tweaks";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	//AttackSpeed
	public static final ResourceLocation DEXTERITY_EFFECT_ID = getResourceLocation("dexterity");
	public static final ResourceLocation CLUMSY_EFFECT_ID = getResourceLocation("clumsy");
	//Cure
	public static final TagKey<Item> CURING_TAG = TagKey.create(Registries.ITEM, getResourceLocation("curing_items"));
	public static final TagKey<Item> INSTANT_CURING_TAG = TagKey.create(Registries.ITEM, getResourceLocation("instant_curing_items"));
	//FireResistant
	public static final TagKey<Item> FIRE_RESISTANT_TAG = TagKey.create(Registries.ITEM, getResourceLocation("fire_resistant"));
	public static final TagKey<Item> NOT_FIRE_RESISTANT_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "not_fire_resistant"));
	//Loot
	public static final ResourceLocation ENCHANT_TREASURE = getResourceLocation( "enchant_treasure");
	public static final ResourceLocation ENCHANT_CURSE = getResourceLocation( "enchant_curse");
	public static final ResourceLocation ENCHANT_MAX = getResourceLocation( "enchant_max");
	public static final ResourceLocation ENCHANT_ALL = getResourceLocation( "enchant_all");
	//MobDrops
	public static ResourceKey<LootTable> STAR_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "entities/extended_wither_drop"));
	public static ResourceKey<LootTable> ROSE_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "gameplay/wither_rose_drop"));
	public static ResourceKey<LootTable> EGG_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "gameplay/chicken_lay"));
	public static ResourceKey<LootTable> ARMADILLO_SHED_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "gameplay/armadillo_shed"));
	public static ResourceKey<LootTable> BRUSH_ARMADILLO_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "gameplay/brush_armadillo"));
	public static ResourceKey<LootTable> TURTLE_GROW_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "gameplay/turtle_grow"));
	public static ResourceKey<LootTable> TURTLE_BRUSH_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "gameplay/brush_turtle"));
	public static TagKey<EntityType<?>> ROSE_KILLER_TAG = TagKey.create(Registries.ENTITY_TYPE, getResourceLocation( "causes_wither_rose_drop"));
	//Ocelot
	public static TagKey<EntityType<?>> OCELOT_HUNT_TARGETS = TagKey.create(Registries.ENTITY_TYPE, getResourceLocation( "ocelot_hunt_targets"));
	public static ResourceLocation OCELOT_SIZE_MODIFIER = getResourceLocation("scale");
	//PowderWalking
	public static final TagKey<Item> POWDER_WALKER_ARMOR_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "powder_walking_armor"));
	public static final TagKey<Item> POWDER_WALKER_ITEM_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "powder_walking_item"));
	//Rarity
	public static final TagKey<Item> COMMON_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "common"));
	public static final TagKey<Item> UNCOMMON_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "uncommon"));
	public static final TagKey<Item> RARE_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "rare"));
	public static final TagKey<Item> EPIC_TAG = TagKey.create(Registries.ITEM, getResourceLocation( "epic"));
	//SpawnsWith
	public static ResourceKey<LootTable> GENERIC_EQUIPMENT_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation("equipment/generic"));
	public static ResourceKey<LootTable> ABSTRACT_SKELETON_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/abstract_skeleton"));
	public static ResourceKey<LootTable> DROWNED_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/drowned"));
	public static ResourceKey<LootTable> FOX_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/fox"));
	public static ResourceKey<LootTable> PIGLIN_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/piglin"));
	public static ResourceKey<LootTable> PIGLIN_BRUTE_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/piglin_brute"));
	public static ResourceKey<LootTable> PILLAGER_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/pillager"));
	public static ResourceKey<LootTable> VEX_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/vex"));
	public static ResourceKey<LootTable> VINDICATOR_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/vindicator"));
	public static ResourceKey<LootTable> WITHER_SKELETON_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/wither_skeleton"));
	public static ResourceKey<LootTable> ZOMBIE_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/zombie"));
	public static ResourceKey<LootTable> ZOMBIFIED_PIGLIN_SPAWN_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, getResourceLocation( "equipment/zombified_piglin"));
	//Vehicle
	public static TagKey<Block> BOAT_BREAKS_TAG = TagKey.create(Registries.BLOCK, getResourceLocation("boat_breaks"));
	//Zzz
	public static TagKey<DamageType> SLEEP_THROUGH_DAMAGE_TAG = TagKey.create(Registries.DAMAGE_TYPE, getResourceLocation( "does_not_wake"));


	private static @NotNull ResourceLocation getResourceLocation(String path) {
		return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID ,path);
	}
}