package net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions;

import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class Horny {
    private final JellyEntity jelly;

    public Horny(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        // Simulate horny behavior, such as seeking a mate

    }

    public void onEnter() {
        // Actions to perform when the jelly enters the horny state
        jelly.setEmotion("Horny");
    }

    public void onExit() {

    }
}
