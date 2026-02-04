package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Calm {
    private final JellyEntity jelly;

    public Calm(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.05F) {
            jelly.playEmotionSound();
        }

        Vec3 currentMotion = jelly.getDeltaMovement();
        Vec3 calmMotion = currentMotion.scale(0.98);
        jelly.setDeltaMovement(calmMotion);
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.CALM);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}