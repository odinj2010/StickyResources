package net.nfgbros.stickyresources.entity.ai.jelly.emotion;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
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
    private int moodDuration = 0;
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
        handleEmotionalContagion();
        spawnEmotionParticles();

        if (moodDuration > 0) {
            moodDuration--;
            if (moodDuration == 0 && currentEmotion != Emotion.NEUTRAL) {
                this.setEmotion(Emotion.NEUTRAL);
            }
        }

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

    private void spawnEmotionParticles() {
        if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_EMOTION_PARTICLES, true)) return;

        if (jelly.level() instanceof ServerLevel serverLevel) {
            // Alpha always has a slight aura
            if (StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ALPHA_DYNAMICS, true) && jelly.isAlpha() && jelly.getRandom().nextInt(20) == 0) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, jelly.getX(), jelly.getY() + jelly.getBbHeight() + 0.2, jelly.getZ(), 1, 0.2, 0.1, 0.2, 0);
            }

            if (currentEmotion == Emotion.NEUTRAL) return;
            if (jelly.getRandom().nextInt(15) != 0) return;

            switch (currentEmotion) {
                case HAPPY, EXCITEMENT -> serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, jelly.getX(), jelly.getY() + jelly.getBbHeight() + 0.2, jelly.getZ(), 1, 0.2, 0.1, 0.2, 0);
                case ANGRY -> serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, jelly.getX(), jelly.getY() + jelly.getBbHeight() + 0.2, jelly.getZ(), 1, 0.2, 0.1, 0.2, 0);
                case SAD -> serverLevel.sendParticles(ParticleTypes.DRIPPING_WATER, jelly.getX(), jelly.getY() + jelly.getBbHeight() / 2, jelly.getZ(), 1, 0.1, 0.1, 0.1, 0);
                case FEAR -> serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, jelly.getX(), jelly.getY() + jelly.getBbHeight() / 2, jelly.getZ(), 1, 0.2, 0.2, 0.2, 0.05);
                case LOVE, HORNY -> serverLevel.sendParticles(ParticleTypes.HEART, jelly.getX(), jelly.getY() + jelly.getBbHeight() + 0.2, jelly.getZ(), 1, 0.2, 0.1, 0.2, 0);
            }
        }
    }

    private void handleEmotionalContagion() {
        if (jelly.getRandom().nextInt(100) != 0) return; // Only check contagion occasionally

        List<JellyEntity> nearby = jelly.getNearbyJellies();
        for (JellyEntity other : nearby) {
            Emotion otherEmotion = other.getEmotion();
            if (otherEmotion == Emotion.NEUTRAL || otherEmotion == currentEmotion) continue;

            // Certain emotions are more contagious
            float chance = switch (otherEmotion) {
                case FEAR -> 0.4f;
                case ANGRY -> 0.3f;
                case HAPPY -> 0.2f;
                case EXCITEMENT -> 0.25f;
                default -> 0.05f;
            };

            if (jelly.getRandom().nextFloat() < chance) {
                this.setEmotion(otherEmotion);
                break;
            }
        }
    }

    private void updateEmotionTriggers() {
        if (!StickyResourcesConfig.JELLY_EMOTION_TRIGGERS_ACTIVE.get()) return;

        boolean alphaDynamics = StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ALPHA_DYNAMICS, true);

        // Alpha Promotion Logic
        if (alphaDynamics && !jelly.isAlpha() && !jelly.isBaby() && jelly.getRandom().nextInt(1000) == 0) {
            List<JellyEntity> nearby = jelly.getNearbyJellies();
            boolean alphaNearby = nearby.stream().anyMatch(JellyEntity::isAlpha);
            if (!alphaNearby) {
                jelly.setAlpha(true);
            }
        }
        
        // If we have a strong persistent mood, don't override it with random triggers
        if (moodDuration > 400 && (currentEmotion == Emotion.FEAR || currentEmotion == Emotion.ANGRY)) return;

        if (jelly.isEmotion(Emotion.LOVE) || jelly.isEmotion(Emotion.SURPRISE)) return;

        // Alpha influence: nearby jellies are less likely to be afraid or angry
        boolean isCalmedByAlpha = alphaDynamics && !jelly.isAlpha() && jelly.getNearbyJellies().stream().anyMatch(JellyEntity::isAlpha);

        // Fear Trigger: Only from actual Enemies or if a player recently attacked
        List<LivingEntity> threats = jelly.level().getEntitiesOfClass(LivingEntity.class, jelly.getBoundingBox().inflate(8.0), 
            entity -> entity instanceof Enemy || (entity instanceof Player && jelly.getLastHurtByMob() == entity));
            
        if (!threats.isEmpty()) {
            if (!isCalmedByAlpha || jelly.getRandom().nextFloat() < 0.3f) {
                jelly.setEmotion(Emotion.FEAR);
                return;
            }
        }

        // If fear was active but no threats are nearby anymore, reset it sooner
        if (currentEmotion == Emotion.FEAR && threats.isEmpty() && moodDuration > 100) {
            moodDuration = 100; 
        }

        if (jelly.getHealth() < jelly.getMaxHealth() && jelly.getLastHurtByMob() != null) {
            if (!isCalmedByAlpha || jelly.getRandom().nextFloat() < 0.5f) {
                jelly.setEmotion(Emotion.ANGRY);
                return;
            }
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
            
            // Set persistence duration
            this.moodDuration = switch (emotion) {
                case FEAR -> 600; // 30 seconds
                case ANGRY -> 800;
                case HAPPY, EXCITEMENT -> 400;
                case BOREDOM, SAD -> 1000;
                case SURPRISE -> 100;
                case LOVE -> 1200;
                default -> 0;
            };
            
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
