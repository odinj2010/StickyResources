package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import java.util.List;

public class MixedBreedGoal extends BreedGoal {

    public MixedBreedGoal(Animal animal, double speedModifier) {
        super(animal, speedModifier);
    }

    protected Animal getFreePartner() {
        Animal animal = this.animal;
        Level level = this.level;
        TargetingConditions targeting = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
        List<Animal> list = level.getNearbyEntities(Animal.class, targeting, animal, animal.getBoundingBox().inflate(8.0D));
        Animal mate = null;
        double closestDistance = Double.MAX_VALUE;

        for (Animal potentialMate : list) {
            if (animal.canMate(potentialMate) && animal.distanceToSqr(potentialMate) < closestDistance) {
                if (isAcceptableMate(potentialMate)) {
                    mate = potentialMate;
                    closestDistance = animal.distanceToSqr(potentialMate);
                }
            }
        }
        return mate;
    }

    private boolean isAcceptableMate(Animal potentialMate) {
        // Implement your custom logic here
        // Example: Allow breeding between JellyEntity and other custom mobs
        return potentialMate instanceof JellyEntity || potentialMate instanceof JellyOakLogEntity;
    }
}