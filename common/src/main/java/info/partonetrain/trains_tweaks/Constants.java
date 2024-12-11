package info.partonetrain.trains_tweaks;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Constants {

	public static final String MOD_ID = "trains_tweaks";
	public static final String MOD_NAME = "Train's Tweaks";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceKey<LootTable> STAR_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "entities/extended_wither_drop"));
	public static ResourceKey<LootTable> ROSE_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gameplay/wither_rose_drop"));
	public static ResourceKey<LootTable> EGG_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "gameplay/chicken_lay"));

	public static TagKey<EntityType<?>> ROSE_KILLER_TAG = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "causes_wither_rose_drop"));
}