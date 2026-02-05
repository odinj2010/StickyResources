package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;


public class CakeJellyEntity extends JellyEntity {

    public CakeJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.tickCount % 40 == 0) {
            if (this.getEmotion() == net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions.Emotion.HAPPY) {
                boostNearbyJellies();
            }
        }
    }

    private void boostNearbyJellies() {
        java.util.List<JellyEntity> nearby = this.getNearbyJellies();
        for (JellyEntity other : nearby) {
            // Cake Jellies make everyone feel better!
            if (other.getEmotion() == net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions.Emotion.BOREDOM || 
                other.getEmotion() == net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions.Emotion.SAD) {
                other.setEmotion(net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions.Emotion.HAPPY);
            }
        }
    }


}