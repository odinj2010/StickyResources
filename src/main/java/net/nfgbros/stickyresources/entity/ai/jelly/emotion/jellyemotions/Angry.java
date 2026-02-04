package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Angry {
    private final JellyEntity jelly;

    public Angry(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.1F) {
            jelly.playEmotionSound();
        }

        Vec3 currentMotion = jelly.getDeltaMovement();
        Vec3 boostedMotion = currentMotion.scale(1.1);
        jelly.setDeltaMovement(boostedMotion);
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.ANGRY);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}