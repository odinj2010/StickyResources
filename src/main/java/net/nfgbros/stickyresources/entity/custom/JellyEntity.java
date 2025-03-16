package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class JellyEntity extends Animal {

    private int tickCounter = 0;

    public JellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes(ModEntities.JellyType type) {
        AttributeSupplier.Builder builder = Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);

        return builder;
    }

    public ModEntities.JellyType getJellyType() {
        return ModEntities.JELLY_ENTITIES.entrySet().stream()
                .filter(entry -> entry.getValue().get() == this.getType())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(ModEntities.JellyType.DEFAULT);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.1D, Ingredient.of(Items.SLIME_BALL), false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.JELLY_ENTITIES.get(this.getJellyType()).get().create(level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SLIME_BALL);
    }

    @Override
    public void tick() {
        super.tick();

        // Increment tickCounter every tick
        tickCounter++;

        // Drop an item every 200 ticks
        if (tickCounter >= 200) {
            Level world = this.level();

            ItemStack dropStack = ItemStack.EMPTY; // Default drop

            ModEntities.JellyType type = this.getJellyType();  // Get the JellyType

            if (type == ModEntities.JellyType.DEFAULT) {
                dropStack = new ItemStack(Items.SLIME_BALL);
            }
            else if (type == ModEntities.JellyType.BONE) {
                dropStack = new ItemStack(ModItems.STICKY_BONE_MEAL.get());
            }
            else if (type == ModEntities.JellyType.CHARCOAL) {
                dropStack = new ItemStack(ModItems.STICKY_CHARCOAL.get());
            }
            else if (type == ModEntities.JellyType.COAL) {
                dropStack = new ItemStack(ModItems.STICKY_COAL.get());
            }
            else if (type == ModEntities.JellyType.COBBLESTONE) {
                dropStack = new ItemStack(ModBlocks.STICKY_COBBLESTONE.get());
            }
            else if (type == ModEntities.JellyType.DIAMOND) {
                dropStack = new ItemStack(ModItems.STICKY_DIAMOND.get());
            }
            else if (type == ModEntities.JellyType.DIRT) {
                dropStack = new ItemStack(ModBlocks.STICKY_DIRT.get());
            }
            else if (type == ModEntities.JellyType.EMERALD) {
                dropStack = new ItemStack(ModItems.STICKY_EMERALD.get());
            }
            else if (type == ModEntities.JellyType.ENDERPEARL) {
                dropStack = new ItemStack(ModItems.STICKY_ENDER_PEARL.get());
            }
            else if (type == ModEntities.JellyType.GLASS) {
                dropStack = new ItemStack(ModBlocks.STICKY_GLASS.get());
            }
            else if (type == ModEntities.JellyType.GRAVEL) {
                dropStack = new ItemStack(ModBlocks.STICKY_GRAVEL.get());
            }
            else if (type == ModEntities.JellyType.LAPIS) {
                dropStack = new ItemStack(ModItems.STICKY_LAPIS_LAZULI.get());
            }
            else if (type == ModEntities.JellyType.OAKLOG) {
                dropStack = new ItemStack(ModBlocks.STICKY_LOG_OAK.get());
            }
            else if (type == ModEntities.JellyType.OBSIDIAN) {
                dropStack = new ItemStack(ModBlocks.STICKY_OBSIDIAN.get());
            }
            else if (type == ModEntities.JellyType.PRISMERINE) {
                dropStack = new ItemStack(ModItems.STICKY_PRISMERINE_CRYSTALS.get());
            }
            else if (type == ModEntities.JellyType.RAWCOPPER) {
                dropStack = new ItemStack(ModItems.STICKY_RAW_COPPER.get());
            }
            else if (type == ModEntities.JellyType.RAWGOLD) {
                dropStack = new ItemStack(ModItems.STICKY_RAW_GOLD.get());
            }
            else if (type == ModEntities.JellyType.RAWIRON) {
                dropStack = new ItemStack(ModItems.STICKY_RAW_IRON.get());
            }
            else if (type == ModEntities.JellyType.REDSTONEDUST) {
                dropStack = new ItemStack(ModItems.STICKY_REDSTONE_DUST.get());
            }
            else if (type == ModEntities.JellyType.SAND) {
                dropStack = new ItemStack(ModBlocks.STICKY_SAND.get());
            }
            else if (type == ModEntities.JellyType.SAPPHIRE) {
                dropStack = new ItemStack(ModItems.STICKY_RAW_SAPPHIRE.get());
            }


            ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), dropStack);
            world.addFreshEntity(itemEntity);
            tickCounter = 0;
        }
    }
}