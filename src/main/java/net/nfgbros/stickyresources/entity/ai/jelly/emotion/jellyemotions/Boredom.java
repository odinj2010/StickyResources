package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Boredom {
    private final JellyEntity jelly;

    public Boredom(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.05F) {
            jelly.playEmotionSound();
        }

        Vec3 currentMotion = jelly.getDeltaMovement();
        Vec3 slowedMotion = currentMotion.scale(0.7);
        jelly.setDeltaMovement(slowedMotion);

        if (jelly.getRandom().nextFloat() < 0.02F) {
            double angle = (jelly.getRandom().nextDouble() - 0.5) * Math.PI;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            double newX = currentMotion.x * cos - currentMotion.z * sin;
            double newZ = currentMotion.x * sin + currentMotion.z * cos;
            Vec3 newMotion = new Vec3(newX, currentMotion.y, newZ);
            jelly.setDeltaMovement(newMotion);
        }
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.BOREDOM);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}