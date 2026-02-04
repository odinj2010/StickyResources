package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Fear {
    private final JellyEntity jelly;

    public Fear(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        Player threat = jelly.level().getNearestPlayer(jelly, 8.0);
        if (threat != null) {
            if (jelly.getRandom().nextFloat() < 0.1F) {
                jelly.playEmotionSound();
            }
            Vec3 jellyPos = jelly.position();
            Vec3 threatPos = threat.position();
            Vec3 fleeDirection = jellyPos.subtract(threatPos).normalize();
            double fleeSpeed = 0.4;
            jelly.setDeltaMovement(fleeDirection.scale(fleeSpeed));
        } else {
            Vec3 currentMotion = jelly.getDeltaMovement();
            Vec3 dampedMotion = currentMotion.scale(0.95);
            jelly.setDeltaMovement(dampedMotion);
        }
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.FEAR);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}