package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social;

import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;
import java.util.Random;

public class JellyEmotionGoal extends Goal {
    private final JellyEntity jelly;
    private final Random random;
    private Emotion currentEmotion;
    private int emotionChangeCooldown = 0;
    private static final int EMOTION_CHANGE_COOLDOWN_MAX = 200; // 10 seconds

    public JellyEmotionGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.random = new Random();
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only change emotion if the jelly is not a baby and the cooldown is over
        return !jelly.isBaby() && emotionChangeCooldown <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue if the cooldown is not over
        return emotionChangeCooldown > 0;
    }

    @Override
    public void start() {
        // Change the emotion
        changeEmotion();
        // Reset the cooldown
        emotionChangeCooldown = EMOTION_CHANGE_COOLDOWN_MAX;
    }

    @Override
    public void tick() {
        // Decrease the cooldown
        emotionChangeCooldown--;
    }

    @Override
    public void stop() {
        // Reset the cooldown
        emotionChangeCooldown = 0;
    }

    private void changeEmotion() {
        // Randomly select a new emotion
        Emotion[] emotions = Emotion.values();
        Emotion newEmotion = emotions[random.nextInt(emotions.length)];
        // Set the new emotion
        jelly.setEmotion(newEmotion);
    }
}