package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Confusion {
    private final JellyEntity jelly;

    public Confusion(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.1F) {
            jelly.playEmotionSound();
        }

        Vec3 currentMotion = jelly.getDeltaMovement();
        double jitterX = (jelly.getRandom().nextDouble() - 0.5) * 0.1;
        double jitterY = (jelly.getRandom().nextDouble() - 0.5) * 0.1;
        double jitterZ = (jelly.getRandom().nextDouble() - 0.5) * 0.1;
        Vec3 jitter = new Vec3(jitterX, jitterY, jitterZ);
        Vec3 confusedMotion = currentMotion.add(jitter);
        jelly.setDeltaMovement(confusedMotion);
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.CONFUSION);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}