package info.partonetrain.trains_tweaks.platform.services;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.apache.commons.lang3.NotImplementedException;

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
     * Depends on spawn reason, which is checked elsewhere
     */
    default boolean canRollSpawnsWithTables(Mob mob) {
        return true;
    }

    /**
     * Returns the value of amendments' fire_charges_throwable config
     * This should only be called if Amendments is definitely installed.
     */
    default boolean isAmendmentsFireballEnabled() {
        throw new NotImplementedException("Amendments fireball");
    }

    /**
     * Returns true if amendments' freeze_ticks option is greater than 0
     * This should only be called if Amendments is definitely installed.
     */
    default boolean isAmendmentsSnowballEnabled() {
        throw new NotImplementedException("Amendments snowball");
    }
}