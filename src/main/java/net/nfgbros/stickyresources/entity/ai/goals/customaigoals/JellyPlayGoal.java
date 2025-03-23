package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

public class JellyPlayGoal extends Goal {
    private final JellyEntity jelly;
    private int playTimer = 0;

    public JellyPlayGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return jelly.getRandom().nextInt(100) < 5; // 5% chance to start playing
    }

    @Override
    public void start() {
        playTimer = 100; // 5-second play session
    }

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

    @Override
    public void stop() {
        playTimer = 0;
    }
}
