package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class CowJellyEntity extends JellyEntity {
    private int eatTick;

    public CowJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new net.minecraft.world.entity.ai.goal.EatBlockGoal(this));
    }

    @Override
    protected void customServerAiStep() {
        this.eatTick = Math.max(0, this.eatTick - 1);
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide) {
            this.eatTick = Math.max(0, this.eatTick - 1);
        }
        super.aiStep();
    }

    @Override
    public void ate() {
        super.ate();
        this.heal(2.0F);
        this.setEmotion(net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions.Emotion.HAPPY);
        if (this.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 0.5D, this.getZ(), 10, 0.5D, 0.5D, 0.5D, 0.0D);
        }
    }

    public void setEatTick(int tick) {
        this.eatTick = tick;
    }

    public int getEatTick() {
        return this.eatTick;
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 10) {
            this.eatTick = 40;
        } else {
            super.handleEntityEvent(pId);
        }
    }
}
