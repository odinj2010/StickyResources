package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.graze;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class GrazeActionHandler {
    private final JellyEntity jelly;
    private final GrazeState grazeState;
    private static final float INJURED_HEALTH_THRESHOLD = 0.5f;
    private boolean isGrazing = false; // Track whether the jelly is currently grazing

    ///
    private int oakSaplingGrazedCount = 0;

    private void incrementOakSaplingGrazedCount() {
        oakSaplingGrazedCount++;
    }

    private int getOakSaplingGrazedCount() {
        return oakSaplingGrazedCount;
    }
    ///



    public GrazeActionHandler(JellyEntity jelly, GrazeState grazeState) {
        this.jelly = jelly;
        this.grazeState = grazeState;
    }

    public void handleGrazing() {
        if (grazeState.targetBlock == null) {
            stopGrazing(); // Stop grazing if there is no target block
            return;
        }

        isGrazing = true; // Set the grazing flag to true

        Level world = jelly.level();
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, jelly.getX(), jelly.getY() + 0.5, jelly.getZ(), 5, 0.2, 0.2, 0.2, 0.1);
        }

        int currentGrazeDuration = getCurrentGrazeDuration();
        if (grazeState.grazeTime >= currentGrazeDuration) {
            handleGrazingFinished();
        } else {
            grazeState.grazeTime++;
        }
    }

    private int getCurrentGrazeDuration() {
        return jelly.getHealth() < jelly.getMaxHealth() * INJURED_HEALTH_THRESHOLD ?
                Mth.floor(StickyResourcesConfig.JELLY_GRAZE_DURATION.get() * 0.5f) :
                StickyResourcesConfig.JELLY_GRAZE_DURATION.get();
    }

    private void handleGrazingFinished() {
        jelly.level().destroyBlock(grazeState.targetBlock, false);
        jelly.heal(jelly.getMaxHealth());
        stopGrazing(); // Stop grazing when finished
    }

    void stopGrazing() {
        isGrazing = false; // Clear the grazing flag
    }
}
