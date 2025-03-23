package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for the Jelly entity to migrate to a random location during the day and clear weather.
 *
 * @author NFGbros
 */
public class JellyMigrationGoal extends Goal {
    private final JellyEntity jelly;
    private int migrationCooldown = 0;

    /**
     * Constructs a new JellyMigrationGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyMigrationGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (migrationCooldown > 0) {
            migrationCooldown--;
            return false;
        }

        Level world = jelly.level();
        if (world.isDay() && !world.isRaining()) {
            migrationCooldown = 1200; // 1 minute cooldown
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        // Choose a random location within 20 blocks
        double x = jelly.getX() + (jelly.getRandom().nextDouble() - 0.5) * 20;
        double z = jelly.getZ() + (jelly.getRandom().nextDouble() - 0.5) * 20;
        jelly.getNavigation().moveTo(x, jelly.getY(), z, 1.0);
    }

    @Override
    public boolean canContinueToUse() {
        return !jelly.getNavigation().isDone();
    }

    @Override
    public void stop() {
        migrationCooldown = 1200; // Reset cooldown
    }
}
