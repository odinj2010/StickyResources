package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.sound.ModSounds;

import java.util.EnumSet;

public class JellyCommunicationGoal extends Goal {
    private final JellyEntity jelly;
    private int communicationCooldown = 0;

    public JellyCommunicationGoal(JellyEntity jelly) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (communicationCooldown > 0) {
            communicationCooldown--;
            return false;
        }

        // Check if there are nearby jellies
        return jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(10)).size() > 1;
    }

    @Override
    public void start() {
        if (jelly.level() instanceof ServerLevel serverLevel) {
            // Emit particles to communicate
            serverLevel.sendParticles(ParticleTypes.NOTE, jelly.getX(), jelly.getY() + 1, jelly.getZ(), 5, 0.5, 0.5, 0.5, 0.1);

            // Play the communication sound
            SoundEvent sound = ModSounds.JELLY_COMMUNICATE.get();
            serverLevel.playSound(null, jelly.getX(), jelly.getY(), jelly.getZ(), sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        communicationCooldown = 200; // 10-second cooldown
    }

    @Override
    public boolean canContinueToUse() {
        return false; // One-time communication
    }
}
