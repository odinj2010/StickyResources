package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyBreedingRitualGoal extends Goal {
    private final JellyEntity jelly;
    private int ritualTimer = 0;

    public JellyBreedingRitualGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return jelly.isInLove() && jelly.getRandom().nextInt(100) < 10; // 10% chance to start ritual
    }

    @Override
    public void start() {
        ritualTimer = 100; // 5-second ritual
    }

    @Override
    public void tick() {
        if (ritualTimer > 0) {
            ritualTimer--;

            // Emit particles and perform animations
            if (jelly.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART, jelly.getX(), jelly.getY() + 1, jelly.getZ(), 1, 0.5, 0.5, 0.5, 0.1);
            }
        } else {
            stop();
        }
    }

    @Override
    public void stop() {
        ritualTimer = 0;
    }
}
