package net.nfgbros.stickyresources.entity.custom;
///
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
///
import net.nfgbros.stickyresources.entity.ModEntities;
///
///
///
public class BoneJellyEntity extends JellyEntity {

    public BoneJellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.JELLY_ENTITIES.get(this.getJellyType()).get().create(level);
    }

    // Add any specific Bone Jelly behaviors here...
}