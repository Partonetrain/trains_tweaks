package info.partonetrain.trains_tweaks.platform.services;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Whether a mob can roll a SpawnsWith table.
     * Depends on spawn reason, which is checked different
     */
    default boolean canRollSpawnsWithTables(Mob mob) {
        return true;
    }
}