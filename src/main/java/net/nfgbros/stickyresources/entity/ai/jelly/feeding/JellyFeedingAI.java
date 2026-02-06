package net.nfgbros.stickyresources.entity.ai.jelly.feeding;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.Optional;

public class JellyFeedingAI {
    private final JellyEntity jelly;
    private final Level level;
    private int cooldown = 0;

    public JellyFeedingAI(JellyEntity jelly) {
        this.jelly = jelly;
        this.level = jelly.level();
    }

    public void tick() {
        if (level.isClientSide) return;
        
        // Performance Optimization: Only scan every 10 ticks now for faster eating
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        cooldown = 10;

        ModEntities.JellyType type = jelly.getJellyType();

        // 1. Scan for Items (Food or Transformation) - Increased range slightly for consumption
        level.getEntitiesOfClass(ItemEntity.class, jelly.getBoundingBox().inflate(1.5), entity -> {
            ItemStack stack = entity.getItem();
            return !stack.isEmpty() && (type.isPreferredFood(stack) || type.isLoveFood(stack) || type.getTransformation(stack.getItem()).isPresent());
        }).stream().findFirst().ifPresent(this::consumeItem);

        // 2. Scan for Blocks (Grazing)
        if (!jelly.isBaby()) { // Babies shouldn't graze blocks
            BlockPos jellyPos = jelly.blockPosition();
            BlockState blockState = level.getBlockState(jellyPos);
            if (type.isPreferredBlock(blockState)) {
                consumeBlock(jellyPos, blockState);
            }
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ModEntities.JellyType type = jelly.getJellyType();

        if (type.isPreferredFood(itemStack) || type.isLoveFood(itemStack)) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            if (type.isLoveFood(itemStack)) {
                jelly.setInLove(player);
                jelly.spawnHeartParticles();
            } else {
                jelly.heal(2.0f);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    private void consumeItem(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        ModEntities.JellyType type = jelly.getJellyType();

        // 1. Try Transformation
        Optional<ModEntities.JellyType.TransformationData> transformation = type.getTransformation(stack.getItem());
        if (transformation.isPresent()) {
            ModEntities.JellyType.TransformationData data = transformation.get();
            // Check Age Requirement: Must be Adult to transform
            if (!jelly.isBaby() && stack.getCount() >= data.cost()) {
                stack.shrink(data.cost());
                jelly.transformToJelly(data.result());
                if (stack.isEmpty()) itemEntity.discard();
                return;
            }
        }

        // 2. Try Love Food
        if (type.isLoveFood(stack)) {
            stack.shrink(1);
            jelly.setInLove(null);
            jelly.spawnHeartParticles();
            if (stack.isEmpty()) itemEntity.discard();
            return;
        }

        // 3. Try Preferred Food
        if (type.isPreferredFood(stack)) {
            stack.shrink(1);
            jelly.heal(2.0f); // Simple healing for now
            if (stack.isEmpty()) itemEntity.discard();
        }
    }

    private void consumeBlock(BlockPos pos, BlockState blockState) {
        level.destroyBlock(pos, false);
        jelly.heal(1.0f);
    }
}