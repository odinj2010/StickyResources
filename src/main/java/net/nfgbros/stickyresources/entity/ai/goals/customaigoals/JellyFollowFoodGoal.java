package net.nfgbros.stickyresources.entity.ai.goals.customaigoals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;

import java.util.EnumSet;

public class JellyFollowFoodGoal extends Goal {
    private final JellyEntity jelly;
    private Player followingPlayer;
    private final double speedModifier;

    public JellyFollowFoodGoal(JellyEntity jelly, double speedModifier) {
        this.jelly = jelly;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Check if a player is holding a slime ball and is close enough
        Player player = jelly.level().getNearestPlayer(jelly, 10.0D);
        if (player == null) {
            return false;
        }

        if (isHoldingFood(player)) {
            followingPlayer = player;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        // Continue following if the player is still holding food and is close enough
        return followingPlayer != null && followingPlayer.isAlive() && isHoldingFood(followingPlayer) && jelly.distanceToSqr(followingPlayer) < 100.0D;
    }

    @Override
    public void start() {
        // Start following the player
        jelly.getNavigation().moveTo(followingPlayer, speedModifier);
    }

    @Override
    public void tick() {
        // Keep following the player
        jelly.getLookControl().setLookAt(followingPlayer, 10.0F, jelly.getMaxHeadXRot());
        jelly.getNavigation().moveTo(followingPlayer, speedModifier);
    }

    @Override
    public void stop() {
        // Stop following the player
        followingPlayer = null;
        jelly.getNavigation().stop();
    }

    private boolean isHoldingFood(Player player) {
        // Check if the player is holding a slime ball
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
        return mainHandItem.is(Items.SLIME_BALL) || offHandItem.is(Items.SLIME_BALL);
    }
}