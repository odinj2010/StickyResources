package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.material.FluidState;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.block.ModBlocks;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.ai.goals.JellyCustomAIGoal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.Emotion;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social.JellyBreedGoal;
import net.nfgbros.stickyresources.entity.ai.goals.customaigoals.swarm.JellySwarmGoal;
import net.nfgbros.stickyresources.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class JellyEntity extends Animal {
    private JellyEntity mate;
    private String familyName;
    private List<JellyEntity> familyMembers = new ArrayList<>();
    private boolean inLove = false;
    private boolean isNesting = false;
    public net.minecraft.core.BlockPos nestPos;
    private static final Random RANDOM = new Random();
    private static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZ";
    private static final String VOWELS = "AEIOUY";
    public JellyEntity getMate() {
        return mate;
    }
    public void setMate(JellyEntity mate) {
        this.mate = mate;
    }
    private int dropItemTickCounter = 0;
    private int absorbItemTickCounter = 0;
    private int oakSaplingGrazedCount = 0;
    private int skeletonSkullGrazedCount = 0;
    private int redMushroomGrazedCount = 0;
    private UUID groupId;
    private JellyEntity leader;
    private int hungerLevel = 20; // Initial hunger level (max)
    public static final int MAX_HUNGER = 20; // Maximum hunger level
    public static final int HUNGER_THRESHOLD = 5; // Threshold for hunger effects
    // Getter for hunger level
    public int getHungerLevel() {
        return hungerLevel;
    }
    // Method to feed the jelly
    public void feed(int amount) {
        this.hungerLevel = Math.min(this.hungerLevel + amount, MAX_HUNGER);
        if(this.hungerLevel >= MAX_HUNGER){
            setInLove(true);
        }
    }
    public void resetLove() {
        this.setInLove(false);
        this.setEmotion(Emotion.NEUTRAL);
    }
    public void setInLove(boolean inLove) {
        this.inLove = inLove;
        if(inLove){
            this.setEmotion(Emotion.LOVING);
        }
    }
    public boolean isInLove() {
        return inLove;
    }
    // Method to decrease hunger over time
    public void decreaseHunger() {
        if (this.hungerLevel > 0) {
            this.hungerLevel--;
        }
    }
    public void incrementOakSaplingGrazedCount() {
        oakSaplingGrazedCount++;
    }
    public int getOakSaplingGrazedCount() {
        return oakSaplingGrazedCount;
    }
    public void incrementskeletonSkullGrazedCount() {
        skeletonSkullGrazedCount++;
    }
    public int getskeletonSkullGrazedCount() {
        return skeletonSkullGrazedCount;
    }
    public void incrementredMushroomGrazedCount() {
        redMushroomGrazedCount++;
    }
    public int getredMushroomGrazedCount() {
        return redMushroomGrazedCount;
    }
    private JellyEntity swarmLeader; // Store the leader
    public JellyEntity getSwarmLeader() {
        return swarmLeader;
    }
    public void setSwarmLeader(JellyEntity leader) {
        this.swarmLeader = leader;
    }
    public JellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
    }
    public ModEntities.JellyType getJellyType() {
        return ModEntities.JELLY_ENTITIES.entrySet().stream()
                .filter(entry -> entry.getValue().get() == this.getType())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(ModEntities.JellyType.DEFAULT);
    }
    public static AttributeSupplier.Builder createAttributes(ModEntities.JellyType type) {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new JellyCustomAIGoal(this));

    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    public ModEntities.JellyType.JellySwimBehavior getSwimBehavior() {
        return getJellyType().getSwimBehavior();
    }
    public boolean isInWater() {
        // Check if the jelly is in water
        FluidState fluidState = this.level().getFluidState(this.blockPosition());
        return fluidState.is(FluidTags.WATER);
    }
    public boolean isAboveWater() {
        // Check if the jelly is above water
        return !this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER);
    }
    public boolean isSubmerged() {
        // Check if the jelly is submerged in water
        return this.level().getFluidState(this.blockPosition().above()).is(FluidTags.WATER);
    }
    public void handleSwimBehavior() {
        switch (getSwimBehavior()) {
            case WATER_DAMAGE:
                if (isInWater()) {
                    this.hurt(this.damageSources().drown(), 1.0F);
                }
                break;
            case WATER_LETHAL:
                if (isInWater()) {
                    this.hurt(this.damageSources().drown(), 1000.0F);
                }
                break;
            case DOLPHIN:
                if (isInWater() && isSubmerged()) {
                    // Add dolphin-like behavior here (e.g., surfacing for air)
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.1D, 0.0D));
                }
                break;
            case FISH:
                // Add fish-like behavior here (e.g., swimming freely underwater)
                break;
            case FLOATING:
                if (isInWater()) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).add(0.0D, 0.05D, 0.0D));
                }
                break;
            case SURFACE_SWIMMING:
                if (isInWater() && !isAboveWater()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.1D, 0.0D));
                }
                break;
            case NONE:
            default:
                // No swimming behavior
                break;
        }
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        // Cast the other parent to JellyEntity
        JellyEntity other = (JellyEntity) otherParent;
        // If parents are a water jelly and a lava jelly, produce an obsidian jelly baby.
        if (this.getJellyType() == ModEntities.JellyType.WATER && other.getJellyType() == ModEntities.JellyType.LAVA ||
                this.getJellyType() == ModEntities.JellyType.LAVA && other.getJellyType() == ModEntities.JellyType.WATER) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.OBSIDIAN).get().create(level);        }
        // If parents are a sand jelly and a lava jelly, produce an obsidian jelly baby.
        else if (this.getJellyType() == ModEntities.JellyType.SAND && other.getJellyType() == ModEntities.JellyType.LAVA ||
                this.getJellyType() == ModEntities.JellyType.LAVA && other.getJellyType() == ModEntities.JellyType.SAND) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.GLASS).get().create(level);
        }
        // Otherwise, default to producing offspring of this parent's type.
        return ModEntities.JELLY_ENTITIES.get(this.getJellyType()).get().create(level);
    }
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.SLIME_BALL);
    }



    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        handleSwimBehavior();
        if (this.isBaby()) return;
        // Decrease hunger over time
        if (this.tickCount % 20 == 0) { // Decrease hunger every second
            decreaseHunger();
        }
        // Only drop items if not too hungry
        if (this.hungerLevel > HUNGER_THRESHOLD) {
            dropItemTickCounter++;
            if (dropItemTickCounter >= 1200) {
                dropJellyItem();
                dropItemTickCounter = 0;
            }
        }
        // Check if Jelly needs to switch to swarm behavior dynamically
        if (StickyResourcesConfig.JELLY_SWARMS_ACTIVE.get() == true) {
            int nearbyJellies = countNearbyJellies();
            boolean isSwarming = this.goalSelector.getRunningGoals()
                    .anyMatch(goal -> goal.getGoal() instanceof JellySwarmGoal);
            if (nearbyJellies >= 5 && !isSwarming) {
                this.goalSelector.addGoal(2, new JellySwarmGoal(this, 1.2D));
            } else if (nearbyJellies < 5 && isSwarming) {
                this.goalSelector.removeGoal(new JellySwarmGoal(this, 1.2D)); // Remove swarm goal
            }
        }
        if (this.hasPartner() && this.mate != null) {
            if (this.familyName == null) {
                this.generateFamilyName();
            }
            this.setCustomName(Component.literal(this.familyName));
        }
    }

    public boolean canEat(ItemStack stack) {
        return stack.is(Items.SLIME_BALL);
    }

    public boolean canBeSeenAsEnemy() {
        return false;
    }

    public boolean canBeSeenAsFriend() {
        return true;
    }

    public boolean canBeSeenAsNeutral() {
        return false;
    }

    public boolean canBeSeenAsOther() {
        return false;
    }

    public boolean canBeSeenAsPlayer() {
        return true;
    }

    public boolean canBeSeenAsMob() {
        return true;
    }

    public boolean canBeSeenAsAnimal() {
        return true;
    }

    public boolean canBeSeenAsMonster() {
        return false;
    }

    public boolean canBeSeenAsWaterMob() {
        return true;
    }

    public boolean canBeSeenAsAmbient() {
        return true;
    }

    public boolean canBeSeenAsFlying() {
        return false;
    }

    public boolean canBeSeenAsBoss() {
        return false;
    }

    public boolean canBeSeenAsItem() {
        return false;
    }

    public boolean canBeSeenAsProjectile() {
        return false;
    }

    public boolean canBeSeenAsVehicle() {
        return false;
    }

    public boolean canBeSeenAsLiving() {
        return true;
    }

    public boolean canBeSeenAsEntity() {
        return true;
    }

    // These methods are not overrides from the Animal class
    public boolean canBeSeenAsBlock() {
        return false;
    }

    public boolean canBeSeenAsEffect() {
        return false;
    }

    public boolean canBeSeenAsOtherEntity() {
        return true;
    }

    public boolean canBeSeenAsOtherMob() {
        return true;
    }

    public boolean canBeSeenAsOtherAnimal() {
        return true;
    }

    public boolean canBeSeenAsOtherMonster() {
        return false;
    }

    public boolean canBeSeenAsOtherWaterMob() {
        return true;
    }

    public boolean canBeSeenAsOtherAmbient() {
        return true;
    }

    public boolean canBeSeenAsOtherFlying() {
        return false;
    }

    public boolean canBeSeenAsOtherBoss() {
        return false;
    }

    public boolean canBeSeenAsOtherItem() {
        return false;
    }

    public boolean canBeSeenAsOtherProjectile() {
        return false;
    }

    public boolean canBeSeenAsOtherVehicle() {
        return false;
    }

    public boolean canBeSeenAsOtherLiving() {
        return true;
    }

    public boolean canBeSeenAsOtherBlock() {
        return false;
    }

    public boolean canBeSeenAsOtherEffect() {
        return false;
    }
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    private void dropJellyItem() {
        Level world = this.level();
        ItemStack dropStack = ItemStack.EMPTY;
        ModEntities.JellyType type = this.getJellyType();
        if (type == ModEntities.JellyType.AMETHYST) {
            dropStack = new ItemStack(ModItems.STICKY_AMETHYST.get());
        }
        else if (type == ModEntities.JellyType.BONE) {
            dropStack = new ItemStack(ModItems.STICKY_BONE_MEAL.get());
        }
        else if (type == ModEntities.JellyType.CHARCOAL) {
            dropStack = new ItemStack(ModItems.STICKY_CHARCOAL.get());
        }
        else if (type == ModEntities.JellyType.CAKE) {
            dropStack = new ItemStack(ModItems.JELLY_CAKE.get());
        }
        else if (type == ModEntities.JellyType.COAL) {
            dropStack = new ItemStack(ModItems.STICKY_COAL.get());
        }
        else if (type == ModEntities.JellyType.COBBLESTONE) {
            dropStack = new ItemStack(ModBlocks.STICKY_COBBLESTONE.get());
        }
        else if (type == ModEntities.JellyType.COW) {
            dropStack = new ItemStack(ModItems.STICKY_BEEF.get());
        }
        else if (type == ModEntities.JellyType.DEFAULT) {
            dropStack = new ItemStack(Items.SLIME_BALL);
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
        else if (type == ModEntities.JellyType.GRASS) {
            dropStack = new ItemStack(Items.GRASS);
        }
        else if (type == ModEntities.JellyType.GRAVEL) {
            dropStack = new ItemStack(ModBlocks.STICKY_GRAVEL.get());
        }
        else if (type == ModEntities.JellyType.HONEY) {
            dropStack = new ItemStack(ModBlocks.JELLY_HONEY.get());
        }
        else if (type == ModEntities.JellyType.ICE) {
            dropStack = new ItemStack(ModBlocks.STICKY_ICE.get());
        }
        else if (type == ModEntities.JellyType.LAPIS) {
            dropStack = new ItemStack(ModItems.STICKY_LAPIS_LAZULI.get());
        }
        else if (type == ModEntities.JellyType.LOGOAK) {
            dropStack = new ItemStack(ModBlocks.STICKY_LOG_OAK.get());
        }
        else if (type == ModEntities.JellyType.OBSIDIAN) {
            dropStack = new ItemStack(ModBlocks.STICKY_OBSIDIAN.get());
        }
        else if (type == ModEntities.JellyType.PRISMERINE) {
            dropStack = new ItemStack(ModItems.STICKY_PRISMERINE_CRYSTALS.get());
        }
        else if (type == ModEntities.JellyType.PUMPKIN) {
            dropStack = new ItemStack(ModBlocks.STICKY_PUMPKIN.get());
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
        else if (type == ModEntities.JellyType.ROTTENFLESH) {
            dropStack = new ItemStack(ModItems.STICKY_ROTTON_FLESH.get());
        }
        else if (type == ModEntities.JellyType.REDMUSHROOM) {
            dropStack = new ItemStack(ModItems.STICKY_RED_MUSHROOM.get());
        }
        else if (type == ModEntities.JellyType.SAND) {
            dropStack = new ItemStack(ModBlocks.STICKY_SAND.get());
        }
        else if (type == ModEntities.JellyType.RAWSAPPHIRE) {
            dropStack = new ItemStack(ModItems.STICKY_RAW_SAPPHIRE.get());
        }
        else if (type == ModEntities.JellyType.STONE) {
            dropStack = new ItemStack(ModBlocks.STICKY_STONE.get());
        }
        else if (type == ModEntities.JellyType.STRAWBERRY) {
            dropStack = new ItemStack(ModItems.STICKY_STRAWBERRY.get());
        }
        if (!dropStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, this.getX(), this.getY(), this.getZ(), dropStack);
            world.addFreshEntity(itemEntity);
        }
    }
    public int countNearbyJellies() {
        final int[] count = {0};  // Use an array to allow modification inside lambda
        this.level().getEntitiesOfClass(JellyEntity.class, this.getBoundingBox().inflate(10.0D))  // Adjust range as needed
                .forEach(otherJelly -> {
                    if (otherJelly != this) {
                        count[0]++;
                    }
                });
        return count[0];
    }
    public UUID getGroupId() {
        return this.groupId; // Return the group ID of the jelly entity
    }
    public void setLeader(JellyEntity swarmLeader) {
        this.leader = swarmLeader; // Set the leader of the jelly entity's group
        if (swarmLeader != null) {
            this.groupId = swarmLeader.getGroupId(); // Sync the group ID with the leader's group ID
        } else {
            this.groupId = null; // If no leader, leave the group
        }
    }
    public void onMateDeath() {
        this.mate = null;
        this.familyName = null;
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (this.mate != null) {
            this.mate.onMateDeath();
        }
    }

    private Emotion currentEmotion;

    public Emotion getEmotion() {
        return currentEmotion;
    }

    public void setEmotion(Emotion emotion) {
        this.currentEmotion = emotion;
        // You can add additional logic here based on the emotion
        // For example, play a sound, change the entity's appearance, etc.
        switch (emotion) {
            case HAPPY:
                // Play a happy sound or particle effect
                break;
            case SAD:
                // Play a sad sound or particle effect
                break;
            case ANGRY:
                // Play an angry sound or particle effect
                break;
            case LOVING:
                // Play a loving sound or particle effect
                break;
            case NEUTRAL:
                // Play a neutral sound or particle effect
                break;
        }
    }
    //Nesting
    public boolean isNesting() {
        return isNesting;
    }

    public void setNesting(boolean nesting) {
        isNesting = nesting;
    }
    //Breeding
    public boolean hasPartner() {
        return mate != null;
    }
    public void generateFamilyName() {
        if (this.mate == null) return;
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            nameBuilder.append(CONSONANTS.charAt(RANDOM.nextInt(CONSONANTS.length())));
            nameBuilder.append(VOWELS.charAt(RANDOM.nextInt(VOWELS.length())));
        }
        this.familyName = nameBuilder.toString() + "_" + this.getJellyType().toString();
        this.mate.familyName = this.familyName;
    }
    public String getFamilyName() {
        return familyName;
    }
    public void addFamilyMember(JellyEntity member) {
        if (!familyMembers.contains(member)) {
            familyMembers.add(member);
        }
    }
    public List<JellyEntity> getFamilyMembers() {
        return familyMembers;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

}