package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

/**
 * A custom AI goal for the JellyEntity. This goal allows the jelly to play for a short duration.
 * During play, the jelly may bounce or chase other jellies.
 */
public class JellyPlayGoal extends Goal {
    private final JellyEntity jelly;
    private int playTimer = 0;

    /**
     * Constructs a new JellyPlayGoal for the given jelly entity.
     *
     * @param jelly the jelly entity for which this goal is created
     */
    public JellyPlayGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Determines whether this goal can be used at the current time.
     * The jelly has a 5% chance to start playing.
     *
     * @return true if the jelly can start playing, false otherwise
     */
    @Override
    public boolean canUse() {
        return jelly.getRandom().nextInt(100) < 5; // 5% chance to start playing
    }

    /**
     * Called when this goal is started.
     * Sets the play timer to 100 (5 seconds) to indicate a 5-second play session.
     */
    @Override
    public void start() {
        playTimer = 100; // 5-second play session
    }

    /**
     * Called every tick while this goal is active.
     * During play, the jelly may bounce or chase other jellies.
     * If the play timer reaches 0, the goal is stopped.
     */
    @Override
    public void tick() {
        if (playTimer > 0) {
            playTimer--;

            // Bounce or chase other jellies
            if (jelly.getRandom().nextInt(10) == 0) {
                jelly.setDeltaMovement(jelly.getDeltaMovement().add(0, 0.5, 0)); // Bounce
            } else {
                List<JellyEntity> nearby = jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(5.0));
                if (!nearby.isEmpty()) {
                    JellyEntity target = nearby.get(jelly.getRandom().nextInt(nearby.size()));
                    jelly.getNavigation().moveTo(target, 1.0); // Chase another jelly
                }
            }
        } else {
            stop();
        }
    }

    /**
     * Called when this goal is stopped.
     * Resets the play timer to 0 to indicate that the jelly is not playing.
     */
    @Override
    public void stop() {
        playTimer = 0;
    }
}
