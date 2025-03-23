package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

/**
 * A custom AI goal for Jelly entities to seek and interact with symbiotic partners.
 * Water Jelly entities will seek and heal nearby fish, while Honey Jelly entities will seek and feed nearby bees.
 */
public class JellySymbiosisGoal extends Goal {
    private final JellyEntity jelly;
    private LivingEntity symbioticPartner;
    private final TargetingConditions targetingConditions;

    /**
     * Constructs a new JellySymbiosisGoal for the given jelly entity.
     *
     * @param jelly The jelly entity for which this goal is created.
     */
    public JellySymbiosisGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TargetingConditions.forNonCombat().range(10.0);
    }

    @Override
    public boolean canUse() {
        // Find a symbiotic partner (e.g., fish for Water Jelly, bees for Honey Jelly)
        if (jelly.getJellyType() == ModEntities.JellyType.WATER) {
            symbioticPartner = jelly.level().getNearestEntity(LivingEntity.class, targetingConditions, jelly, jelly.getX(), jelly.getY(), jelly.getZ(), jelly.getBoundingBox().inflate(10.0));
            return symbioticPartner != null && symbioticPartner.getType().toString().contains("fish");
        } else if (jelly.getJellyType() == ModEntities.JellyType.HONEY) {
            symbioticPartner = jelly.level().getNearestEntity(LivingEntity.class, targetingConditions, jelly, jelly.getX(), jelly.getY(), jelly.getZ(), jelly.getBoundingBox().inflate(10.0));
            return symbioticPartner != null && symbioticPartner.getType().toString().contains("bee");
        }
        return false;
    }

    @Override
    public void start() {
        jelly.getNavigation().moveTo(symbioticPartner, 1.0);
    }

    @Override
    public void tick() {
        if (jelly.distanceToSqr(symbioticPartner) <= 2.0) {
            // Provide benefits to the symbiotic partner (e.g., heal fish, feed bees)
            if (jelly.getJellyType() == ModEntities.JellyType.WATER) {
                symbioticPartner.heal(1.0F); // Heal fish
            } else if (jelly.getJellyType() == ModEntities.JellyType.HONEY) {
                symbioticPartner.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0)); // Feed bees
            }
            stop();
        }
    }

    @Override
    public void stop() {
        symbioticPartner = null;
    }
}
