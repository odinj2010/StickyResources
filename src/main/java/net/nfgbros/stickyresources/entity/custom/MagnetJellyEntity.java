package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.nfgbros.stickyresources.util.Magnetism;
import org.joml.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;

public class MagnetJellyEntity extends JellyEntity {
    private static final EntityDataAccessor<Boolean> ATTRACTING = SynchedEntityData.defineId(MagnetJellyEntity.class, EntityDataSerializers.BOOLEAN);
    private final Magnetism magnetism;

    public MagnetJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.magnetism = new Magnetism(this);
        this.xpReward = 5;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTRACTING, true);
    }

    public boolean isAttracting() {
        return this.entityData.get(ATTRACTING);
    }

    public void setAttracting(boolean attracting) {
        this.entityData.set(ATTRACTING, attracting);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Attracting", isAttracting());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setAttracting(pCompound.getBoolean("Attracting"));
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.level().isClientSide && pHand == InteractionHand.MAIN_HAND) {
            boolean currentState = isAttracting();
            setAttracting(!currentState);
            
            // Visual Feedback
            float r = currentState ? 1.0f : 0.0f; // Red if turning OFF (Repel)
            float b = currentState ? 0.0f : 1.0f; // Blue if turning ON (Attract)
            
            ((net.minecraft.server.level.ServerLevel)this.level()).sendParticles(new DustParticleOptions(new Vector3f(r, 0.0f, b), 1.0f), this.getX(), this.getY() + 0.5, this.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
            
            this.playSound(net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();
        magnetism.tick();
    }
}
