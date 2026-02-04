package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Surprise {
    private final JellyEntity jelly;
    private boolean triggered;

    public Surprise(JellyEntity jelly) {
        this.jelly = jelly;
        this.triggered = false;
    }

    public void tick() {
        if (!triggered) {
            jelly.playEmotionSound();
            Vec3 currentMotion = jelly.getDeltaMovement();
            jelly.setDeltaMovement(currentMotion.add(0, 0.3, 0));
            triggered = true;
        }
        Vec3 currentMotion = jelly.getDeltaMovement();
        Vec3 dampedMotion = currentMotion.scale(0.95);
        jelly.setDeltaMovement(dampedMotion);
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.SURPRISE);
        triggered = false;
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}