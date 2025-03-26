package net.nfgbros.stickyresources.entity.ai.jelly.emotion;

import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions.Happy;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions.Horny;

public class Emotions {
    private final JellyEntity jelly;
    private Emotion currentEmotion;
    private final Happy happy;
    private final Horny horny;

    public Emotions(JellyEntity jelly) {
        this.jelly = jelly;
        this.happy = new Happy(jelly);
        this.horny = new Horny(jelly);
        this.currentEmotion = Emotion.NEUTRAL; // Default emotion
    }

    public void tick() {
        // Update the jelly's emotional state based on conditions
        updateEmotion();

        // Delegate behavior to the current emotion
        switch (currentEmotion) {
            case HAPPY:
                happy.tick();
                break;
            case HORNY:
                horny.tick();
                break;
            case NEUTRAL:
                // Neutral behavior (default)
                break;
            // Add other emotions here
        }
    }

    private void updateEmotion() {
        // Logic to determine the jelly's current emotion
        if (jelly.isHappy()) {
            setEmotion(Emotion.HAPPY);
        } else if (jelly.isHorny()) {
            setEmotion(Emotion.HORNY);
        } else {
            setEmotion(Emotion.NEUTRAL);
        }
    }

    public void setEmotion(Emotion emotion) {
        if (this.currentEmotion != emotion) {
            // Call onExit for the current emotion
            switch (this.currentEmotion) {
                case HAPPY:
                    happy.onExit();
                    break;
                case HORNY:
                    horny.onExit();
                    break;
                // Handle other emotions
            }

            // Update the current emotion
            this.currentEmotion = emotion;

            // Call onEnter for the new emotion
            switch (this.currentEmotion) {
                case HAPPY:
                    happy.onEnter();
                    break;
                case HORNY:
                    horny.onEnter();
                    break;
                // Handle other emotions
            }
        }
    }

    public Emotion getCurrentEmotion() {
        return currentEmotion;
    }

    public enum Emotion {
        HAPPY,
        HORNY,
        NEUTRAL
        // Add other emotions here
    }
}
