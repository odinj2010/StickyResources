package net.nfgbros.stickyresources.entity.ai.jelly;

import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.ai.jelly.feeding.JellyFeedingAI;
import net.nfgbros.stickyresources.entity.ai.jelly.movement.JellyMovement;
import net.nfgbros.stickyresources.entity.ai.jelly.social.communication.JellyCommunicationAI;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.List;

public class JellyCustomAI {
    private final JellyEntity jelly;
    private final JellyMovement movement;
    private final JellyFeedingAI feedingAI;
    private final Emotions emotions;



    public JellyCustomAI(JellyEntity jelly) {
        this.jelly = jelly;
        this.movement = new JellyMovement(jelly);
        this.feedingAI = new JellyFeedingAI(jelly);
        this.emotions = new Emotions(jelly);

    }

    public void tick(List<JellyEntity> nearbyJellies) {
        movement.tick();
        feedingAI.tick();
        emotions.tick();

    }

}
