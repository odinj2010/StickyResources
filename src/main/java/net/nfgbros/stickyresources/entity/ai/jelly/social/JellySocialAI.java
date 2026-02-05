package net.nfgbros.stickyresources.entity.ai.jelly.social;

import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import java.util.List;

public class JellySocialAI {
    private final JellyEntity jelly;
    private int socialTick = 0;

    public JellySocialAI(JellyEntity jelly) {
        this.jelly = jelly;
    }

    public void tick() {
        if (jelly.level().isClientSide) return;

        if (socialTick > 0) {
            socialTick--;
            return;
        }
        socialTick = 100 + jelly.getRandom().nextInt(200);

        handleSocialChirp();
    }

    private void handleSocialChirp() {
        if (!jelly.canChirp()) return;

        List<JellyEntity> nearby = jelly.getNearbyJellies();
        if (nearby.isEmpty()) return;

        // If we are happy/calm/excited, chirp to our friends
        switch (jelly.getEmotion()) {
            case HAPPY, CALM, EXCITEMENT, LOVE -> {
                jelly.playEmotionSound();
                // Trigger a response from one nearby friend
                nearby.get(jelly.getRandom().nextInt(nearby.size())).onReceiveSocialChirp(jelly);
            }
            case ANGRY, FEAR -> {
                // Warning chirp!
                jelly.playEmotionSound();
            }
        }
    }
}
