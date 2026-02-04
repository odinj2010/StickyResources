package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Love {
    private final JellyEntity jelly;

    public Love(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.08F) {
            jelly.playEmotionSound();
        }

        if (jelly.getMate() != null) {
            jelly.spawnHeartParticles();
            Vec3 matePos = jelly.getMate().position();
            if (jelly.distanceToSqr(matePos) > 16.0) {
                jelly.getNavigation().moveTo(jelly.getMate(), 0.2);
            }
        }
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.LOVE);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.NEUTRAL);
    }
}