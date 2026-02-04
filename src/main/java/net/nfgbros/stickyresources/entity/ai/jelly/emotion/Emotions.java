package net.nfgbros.stickyresources.entity.ai.jelly.emotion;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.jellyemotions.*;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.sound.ModSounds;

import java.util.List;
import java.util.function.Supplier;

public class Emotions {
    private final JellyEntity jelly;
    private Emotion currentEmotion;

    private final Angry angry;
    private final Boredom boredom;
    private final Calm calm;
    private final Confusion confusion;
    private final Excitement excitement;
    private final Fear fear;
    private final Happy happy;
    private final Horny horny;
    private final Jealousy jealousy;
    private final Love love;
    private final Sad sad;
    private final Surprise surprise;

    private int boredomTicks = 0;
    private Vec3 lastPos = Vec3.ZERO;

    public Emotions(JellyEntity jelly) {
        this.jelly = jelly;
        this.happy = new Happy(jelly);
        this.horny = new Horny(jelly);
        this.sad = new Sad(jelly);
        this.angry = new Angry(jelly);
        this.boredom = new Boredom(jelly);
        this.calm = new Calm(jelly);
        this.confusion = new Confusion(jelly);
        this.excitement = new Excitement(jelly);
        this.fear = new Fear(jelly);
        this.jealousy = new Jealousy(jelly);
        this.love = new Love(jelly);
        this.surprise = new Surprise(jelly);
        this.currentEmotion = Emotion.NEUTRAL;
    }

    public void tick() {
        if (jelly.level().isClientSide) return;

        updateEmotionTriggers();

        if (jelly.getEmotion() != this.currentEmotion) {
            this.setEmotion(jelly.getEmotion());
        }

        switch (currentEmotion) {
            case HAPPY -> happy.tick();
            case HORNY -> horny.tick();
            case SAD -> sad.tick();
            case ANGRY -> angry.tick();
            case BOREDOM -> boredom.tick();
            case CALM -> calm.tick();
            case CONFUSION -> confusion.tick();
            case EXCITEMENT -> excitement.tick();
            case FEAR -> fear.tick();
            case JEALOUSY -> jealousy.tick();
            case LOVE -> love.tick();
            case SURPRISE -> surprise.tick();
            case NEUTRAL -> {}
        }
    }

    private void updateEmotionTriggers() {
        if (!StickyResourcesConfig.JELLY_EMOTION_TRIGGERS_ACTIVE.get()) return;
        if (jelly.isEmotion(Emotion.LOVE) || jelly.isEmotion(Emotion.SURPRISE)) return;

        List<LivingEntity> enemies = jelly.level().getEntitiesOfClass(LivingEntity.class, jelly.getBoundingBox().inflate(8.0), entity -> entity instanceof Enemy);
        if (!enemies.isEmpty()) {
            jelly.setEmotion(Emotion.FEAR);
            return;
        }

        if (jelly.getHealth() < jelly.getMaxHealth() && jelly.getLastHurtByMob() != null) {
            jelly.setEmotion(Emotion.ANGRY);
            return;
        }

        if (!jelly.isBaby() && jelly.getRandom().nextInt(StickyResourcesConfig.JELLY_WILD_BREED_CHANCE.get()) == 0 && jelly.getMate() == null) {
            jelly.setEmotion(Emotion.HORNY);
            return;
        }

        if (jelly.position().distanceToSqr(lastPos) < 0.01) {
            boredomTicks++;
            if (boredomTicks > 400) {
                jelly.setEmotion(Emotion.BOREDOM);
            }
        } else {
            boredomTicks = 0;
            lastPos = jelly.position();
            if (jelly.isEmotion(Emotion.BOREDOM)) jelly.setEmotion(Emotion.NEUTRAL);
        }

        if (jelly.isEmotion(Emotion.NEUTRAL) && jelly.getRandom().nextInt(1000) == 0) {
            jelly.setEmotion(Emotion.HAPPY);
        }
    }

    public void setEmotion(Emotion emotion) {
        if (this.currentEmotion != emotion) {
            exitCurrentEmotion();
            this.currentEmotion = emotion;
            jelly.setEmotion(emotion);
            enterNewEmotion();
        }
    }

    private void exitCurrentEmotion() {
        switch (this.currentEmotion) {
            case HAPPY -> happy.onExit();
            case HORNY -> horny.onExit();
            case SAD -> sad.onExit();
            case ANGRY -> angry.onExit();
            case BOREDOM -> boredom.onExit();
            case CALM -> calm.onExit();
            case CONFUSION -> confusion.onExit();
            case EXCITEMENT -> excitement.onExit();
            case FEAR -> fear.onExit();
            case JEALOUSY -> jealousy.onExit();
            case LOVE -> love.onExit();
            case SURPRISE -> surprise.onExit();
            default -> {}
        }
    }

    private void enterNewEmotion() {
        switch (this.currentEmotion) {
            case HAPPY -> happy.onEnter();
            case HORNY -> horny.onEnter();
            case SAD -> sad.onEnter();
            case ANGRY -> angry.onEnter();
            case BOREDOM -> boredom.onEnter();
            case CALM -> calm.onEnter();
            case CONFUSION -> confusion.onEnter();
            case EXCITEMENT -> excitement.onEnter();
            case FEAR -> fear.onEnter();
            case JEALOUSY -> jealousy.onEnter();
            case LOVE -> love.onEnter();
            case SURPRISE -> surprise.onEnter();
            default -> {}
        }
    }

    public Emotion getCurrentEmotion() {
        return currentEmotion;
    }

    public enum Emotion {
        NEUTRAL("Neutral", null),
        HAPPY("Happy", ModSounds.JELLY_HAPPY),
        SAD("Sad", ModSounds.JELLY_SAD),
        ANGRY("Angry", ModSounds.JELLY_ANGRY),
        BOREDOM("Boredom", ModSounds.JELLY_BORED),
        CALM("Calm", ModSounds.JELLY_CALM),
        CONFUSION("Confusion", ModSounds.JELLY_CONFUSION),
        EXCITEMENT("Excitement", ModSounds.JELLY_EXCITEMENT),
        FEAR("Fear", ModSounds.JELLY_FEAR),
        JEALOUSY("Jealousy", ModSounds.JELLY_JEALOUSY),
        LOVE("Love", ModSounds.JELLY_LOVE),
        SURPRISE("Surprise", ModSounds.JELLY_SURPRISE),
        HORNY("Horny", null);

        private final String name;
        private final Supplier<SoundEvent> sound;

        Emotion(String name, Supplier<SoundEvent> sound) {
            this.name = name;
            this.sound = sound;
        }

        public String getName() { return name; }
        public Supplier<SoundEvent> getSound() { return sound; }
    }
}
