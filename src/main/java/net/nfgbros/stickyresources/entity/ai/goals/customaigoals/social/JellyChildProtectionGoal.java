package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.List;

public class JellyChildProtectionGoal extends Goal {
    private final JellyEntity parent;
    private JellyEntity child;
    private final double speedModifier;
    private final float childDistance;
    private final float parentDistance;
    private BlockPos nestPos;

    public JellyChildProtectionGoal(JellyEntity parent, double speedModifier, float childDistance, float parentDistance) {
        this.parent = parent;
        this.speedModifier = speedModifier;
        this.childDistance = childDistance;
        this.parentDistance = parentDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only use if the parent has a nest and there is a child in the family
        if (parent.nestPos == null) return false;
        List<JellyEntity> familyMembers = parent.getFamilyMembers();
        if (familyMembers.isEmpty()) return false;
        for (JellyEntity member : familyMembers) {
            if (member.isBaby()) {
                this.child = member;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue if the parent still has a nest and the child is still a baby
        return parent.nestPos != null && child != null && child.isBaby();
    }

    @Override
    public void start() {
        this.nestPos = parent.nestPos;
    }

    @Override
    public void tick() {
        if (nestPos == null) {
            return;
        }
        // Check if the child is too far from the nest
        if (child.distanceToSqr(nestPos.getX(), nestPos.getY(), nestPos.getZ()) < childDistance * childDistance) {
            // Child is within the safe zone, do nothing
            return;
        }

        // Child is too far, move the child closer to the nest
        PathNavigation childNavigation = child.getNavigation();
        if (!childNavigation.isDone()) {
            return; // Child is already moving, don't interrupt
        }
        childNavigation.moveTo(nestPos.getX(), nestPos.getY(), nestPos.getZ(), speedModifier);

        // Check if the parent is too far from the nest
        if (parent.distanceToSqr(nestPos.getX(), nestPos.getY(), nestPos.getZ()) > parentDistance * parentDistance) {
            // Parent is too far, move the parent closer to the nest
            PathNavigation parentNavigation = parent.getNavigation();
            if (!parentNavigation.isDone()) {
                return; // Parent is already moving, don't interrupt
            }
            parentNavigation.moveTo(nestPos.getX(), nestPos.getY(), nestPos.getZ(), speedModifier);
        }
    }
}