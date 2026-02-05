package net.nfgbros.stickyresources.entity.ai.jelly;

import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.ai.jelly.feeding.JellyFeedingAI;
import net.nfgbros.stickyresources.entity.ai.jelly.mate.MateAI;
import net.nfgbros.stickyresources.entity.ai.jelly.movement.JellyMovement;
import net.nfgbros.stickyresources.entity.ai.jelly.social.JellySocialAI;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class JellyCustomAI {
    private final JellyEntity jelly;
    private final JellyMovement movement;
    private final JellyFeedingAI feedingAI;
    private final Emotions emotions;
    private final MateAI mateAI;
    private final JellySocialAI socialAI;

    public JellyCustomAI(JellyEntity jelly) {
        this.jelly = jelly;
        this.movement = new JellyMovement(jelly);
        this.feedingAI = new JellyFeedingAI(jelly);
        this.emotions = new Emotions(jelly);
        this.mateAI = new MateAI(jelly);
        this.socialAI = new JellySocialAI(jelly);
    }

    public void tick() {
        movement.tick();
        feedingAI.tick();
        emotions.tick();
        mateAI.tick();
        socialAI.tick();
    }
}
