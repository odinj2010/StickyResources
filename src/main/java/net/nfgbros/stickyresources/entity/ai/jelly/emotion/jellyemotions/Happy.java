package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Happy {
    private final JellyEntity jelly;

    public Happy(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        // Simulate happy behavior, such as faster movement or playful actions
        if (jelly.getRandom().nextFloat() < 0.1F) {
            jelly.playHappySound();
        }
    }

    public void onEnter() {
        // Actions to perform when the jelly enters the happy state
        jelly.setEmotion("Happy");
    }

    public void onExit() {

    }
}
