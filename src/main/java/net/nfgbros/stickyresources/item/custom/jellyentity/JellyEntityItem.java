package net.nfgbros.stickyresources.item.custom.jellyentity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

public class JellyEntityItem extends Item {
    private final EntityType<? extends JellyEntity> entityType;

    // Constructor to initialize the entity type and item properties
    public JellyEntityItem(Properties pProperties, EntityType<? extends JellyEntity> entityType) {
        super(pProperties);
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        // Only execute on the server side
        if (!pContext.getLevel().isClientSide()) {
            // Create a new instance of the JellyEntity
            JellyEntity jelly = entityType.create(pContext.getLevel());
            if (jelly != null) {
                // Get player rotation, default to 0 if player is null
                float rotation = pContext.getPlayer() != null ? pContext.getPlayer().getYRot() : 0;

                // Set the entity's position and rotation
                jelly.moveTo(
                        pContext.getClickedPos().above().getX() + 0.5, // Adjust X position
                        pContext.getClickedPos().above().getY(),       // Adjust Y position
                        pContext.getClickedPos().above().getZ() + 0.5, // Adjust Z position
                        rotation,                                      // Set entity rotation
                        0                                              // No pitch adjustment
                );

                // Add the entity to the world
                pContext.getLevel().addFreshEntity(jelly);

                return InteractionResult.SUCCESS;
            }
        }
        // Return fail if entity creation or placement fails
        return InteractionResult.FAIL;
    }
}
