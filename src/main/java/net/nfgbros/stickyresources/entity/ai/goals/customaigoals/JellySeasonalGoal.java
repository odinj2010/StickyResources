package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * Custom AI goal for the Jelly entity, which handles seasonal behaviors.
 *
 * @author NFGbros
 * @since 1.0.0
 */
public class JellySeasonalGoal extends Goal {
    private final JellyEntity jelly;

    /**
     * Constructs a new JellySeasonalGoal instance.
     *
     * @param jelly The Jelly entity for which this goal is created.
     */
    public JellySeasonalGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return true; // Always active
    }

    @Override
    public void tick() {
        Level world = jelly.level();
        long dayTime = world.getDayTime() % 24000; // Get the current time of day

        // Winter behavior: Hibernate at night
        if (isWinter(world)) {
            if (dayTime > 13000 && dayTime < 23000) { // Nighttime
                jelly.getNavigation().stop(); // Stop moving
            }
        }

        // Spring behavior: Become more active
        if (isSpring(world)) {
            jelly.setSpeed(0.5F); // Increase speed
        }
    }

    /**
     * Checks if the current biome is considered winter.
     *
     * @param world The world in which the Jelly entity is located.
     * @return True if the biome is considered winter, false otherwise.
     */
    private boolean isWinter(Level world) {
        // Check if it's winter (e.g., based on biome or custom logic)
        return world.getBiome(jelly.blockPosition()).get().getBaseTemperature() < 0.15F;
    }

    /**
     * Checks if the current biome is considered spring.
     *
     * @param world The world in which the Jelly entity is located.
     * @return True if the biome is considered spring, false otherwise.
     */
    private boolean isSpring(Level world) {
        // Check if it's spring (e.g., based on biome or custom logic)
        return world.getBiome(jelly.blockPosition()).get().getBaseTemperature() > 0.7F;
    }
}
