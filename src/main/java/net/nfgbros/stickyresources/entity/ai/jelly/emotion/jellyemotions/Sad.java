package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Sad {
    private final JellyEntity jelly;

    public Sad(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.05F) {
            jelly.playEmotionSound();
        }

        Vec3 currentMotion = jelly.getDeltaMovement();
        Vec3 slowedMotion = currentMotion.scale(0.8);
        jelly.setDeltaMovement(slowedMotion);
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.SAD);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}