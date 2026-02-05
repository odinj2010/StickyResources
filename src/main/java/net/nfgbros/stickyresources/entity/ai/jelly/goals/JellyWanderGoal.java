package net.nfgbros.stickyresources.entity.ai.jelly.goals;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyWanderGoal extends Goal {
    private final JellyEntity jelly;
    private final double speed;
    private double x;
    private double y;
    private double z;

    public JellyWanderGoal(JellyEntity jelly, double speed) {
        this.jelly = jelly;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.jelly.isVehicle() || this.jelly.isBaby()) {
            return false;
        }
        
        // Don't wander if we have other pressing needs (to be expanded in Step 3)
        if (this.jelly.getNavigation().isInProgress()) {
            return false;
        }

        Vec3 vec3 = DefaultRandomPos.getPos(this.jelly, 10, 7);
        if (vec3 == null) {
            return false;
        } else {
            this.x = vec3.x;
            this.y = vec3.y;
            this.z = vec3.z;
            return true;
        }
    }

    @Override
    public void start() {
        this.jelly.getNavigation().moveTo(this.x, this.y, this.z, this.speed);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.jelly.getNavigation().isDone() && !this.jelly.isVehicle();
    }

    @Override
    public void stop() {
        this.jelly.getNavigation().stop();
    }
}
