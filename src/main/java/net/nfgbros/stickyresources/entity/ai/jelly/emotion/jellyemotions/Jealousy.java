package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.List;

public class Jealousy {
    private final JellyEntity jelly;

    public Jealousy(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        JellyEntity mate = jelly.getMate();
        if (mate == null) {
            return;
        }

        AABB detectionArea = mate.getBoundingBox().inflate(5.0);
        List<JellyEntity> intruders = jelly.level().getEntitiesOfClass(JellyEntity.class, detectionArea, entity -> entity != jelly && entity != mate);

        if (!intruders.isEmpty()) {
            if (jelly.getRandom().nextFloat() < 0.1F) {
                jelly.playEmotionSound();
            }
            jelly.getNavigation().moveTo(mate, 0.4);
            Vec3 currentMotion = jelly.getDeltaMovement();
            Vec3 boostedMotion = currentMotion.scale(1.05);
            jelly.setDeltaMovement(boostedMotion);
        } else {
            Vec3 currentMotion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(currentMotion.scale(0.98));
        }
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.JEALOUSY);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}