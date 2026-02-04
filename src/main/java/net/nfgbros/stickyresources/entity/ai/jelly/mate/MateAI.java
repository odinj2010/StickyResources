package net.nfgbros.stickyresources.entity.ai.jelly.mate;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.List;

public class MateAI {
    private final JellyEntity jelly;
    private JellyEntity mate;
    private final Level level;
    private int searchCooldown = 0;
    private int procreateCooldown = 0;

    public MateAI(JellyEntity jelly) {
        this.jelly = jelly;
        this.level = jelly.level();
        this.mate = jelly.getMate();
    }

    public void tick() {
        if (level.isClientSide) return;

        if (mate == null) {
            attemptToFindMate();
        } else {
            handleMateInteraction();
        }
    }

    private void attemptToFindMate() {
        if (!StickyResourcesConfig.JELLY_AUTONOMOUS_BREEDING.get() && !jelly.isEmotion(Emotions.Emotion.LOVE)) {
            return;
        }
        if (!jelly.isEmotion(Emotions.Emotion.HORNY) && !jelly.isEmotion(Emotions.Emotion.LOVE)) {
            return;
        }
        if (jelly.getMate() != null || jelly.isBaby()) {
            return;
        }

        if (searchCooldown > 0) {
            searchCooldown--;
            return;
        }

        AABB searchArea = jelly.getBoundingBox().inflate(8.0);
        List<JellyEntity> nearbyJellies = level.getEntitiesOfClass(JellyEntity.class, searchArea);
        for (JellyEntity potentialMate : nearbyJellies) {
            if (isValidMate(potentialMate)) {
                mate = potentialMate;
                jelly.setMate(mate);
                mate.setMate(jelly);
                jelly.setEmotion(Emotions.Emotion.LOVE);
                mate.setEmotion(Emotions.Emotion.LOVE);
                jelly.spawnHeartParticles();
                mate.spawnHeartParticles();
                break;
            }
        }
        searchCooldown = 100;
    }

    private boolean isValidMate(JellyEntity candidate) {
        if (candidate == jelly || candidate.isBaby()) return false;
        if (!candidate.isEmotion(Emotions.Emotion.HORNY) || candidate.getMate() != null) return false;
        return jelly.distanceToSqr(candidate) <= 64.0;
    }

    private void handleMateInteraction() {
        if (mate == null || !mate.isAlive() || mate.isDeadOrDying()) {
            mate = null;
            jelly.setMate(null);
            return;
        }

        double distSq = jelly.distanceToSqr(mate);

        // 1. Follow Mate
        if (distSq > 16.0) {
            jelly.getNavigation().moveTo(mate, StickyResourcesConfig.JELLY_MATE_SPEED.get());
        } 
        // 2. Procreate if close enough and both in LOVE state
        else if (distSq < 2.5 && jelly.isEmotion(Emotions.Emotion.LOVE) && mate.isEmotion(Emotions.Emotion.LOVE)) {
            if (procreateCooldown <= 0) {
                procreate();
                procreateCooldown = 6000; // 5 minutes cooldown
            }
        }

        if (procreateCooldown > 0) procreateCooldown--;
    }

    private void procreate() {
        if (level instanceof ServerLevel serverLevel) {
            JellyEntity baby = (JellyEntity) jelly.getBreedOffspring(serverLevel, mate);
            if (baby != null) {
                baby.setBaby(true);
                baby.moveTo(jelly.getX(), jelly.getY(), jelly.getZ(), 0.0F, 0.0F);
                serverLevel.addFreshEntity(baby);
                
                // Set cooldowns
                jelly.setAge(6000);
                mate.setAge(6000);

                // Reset parents
                jelly.setEmotion(Emotions.Emotion.NEUTRAL);
                mate.setEmotion(Emotions.Emotion.NEUTRAL);
                jelly.setMate(null);
                mate.setMate(null);
                this.mate = null;
                
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HEART, jelly.getX(), jelly.getY() + 0.5, jelly.getZ(), 10, 0.5, 0.5, 0.5, 0);
            }
        }
    }
}
