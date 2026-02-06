package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.List;

public class Fear {
    private final JellyEntity jelly;
    private int navigationCooldown = 0;

    public Fear(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (navigationCooldown > 0) {
            navigationCooldown--;
        }

        // Find the nearest actual threat (Enemy or player who hurt us)
        LivingEntity threat = jelly.level().getNearestEntity(
            jelly.level().getEntitiesOfClass(LivingEntity.class, jelly.getBoundingBox().inflate(10.0),
            entity -> entity instanceof Enemy || (entity instanceof Player && jelly.getLastHurtByMob() == entity)),
            net.minecraft.world.entity.ai.targeting.TargetingConditions.forNonCombat(),
            jelly, jelly.getX(), jelly.getY(), jelly.getZ()
        );

        if (threat != null) {
            if (jelly.getRandom().nextFloat() < 0.05F) {
                jelly.playEmotionSound();
            }

            // Move naturally using Pathfinding instead of setting raw velocity
            if (navigationCooldown == 0 && jelly.getNavigation().isDone()) {
                Vec3 fleePos = DefaultRandomPos.getPosAway(jelly, 16, 7, threat.position());
                if (fleePos != null) {
                    jelly.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.3D);
                    navigationCooldown = 40; // Don't recalculate every tick
                }
            }
        }
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.FEAR);
    }

    public void onExit() {
        jelly.getNavigation().stop();
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}
