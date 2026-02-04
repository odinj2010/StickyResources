package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Excitement {
    private final JellyEntity jelly;

    public Excitement(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.1F) {
            jelly.playEmotionSound();
        }

        Vec3 currentMotion = jelly.getDeltaMovement();
        Vec3 randomBoost = new Vec3(
                (jelly.getRandom().nextDouble() - 0.5) * 0.2,
                (jelly.getRandom().nextDouble() - 0.5) * 0.2,
                (jelly.getRandom().nextDouble() - 0.5) * 0.2
        );
        Vec3 boostedMotion = currentMotion.add(randomBoost).scale(1.1);
        jelly.setDeltaMovement(boostedMotion);
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.EXCITEMENT);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}