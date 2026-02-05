package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Horny {
    private final JellyEntity jelly;

    public Horny(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.getRandom().nextFloat() < 0.1F) {
            jelly.playEmotionSound();
        }
    }

    public void onEnter() {
        jelly.setEmotion(Emotions.Emotion.HORNY);
    }

    public void onExit() {
        jelly.setEmotion(Emotions.Emotion.HAPPY);
    }
}
