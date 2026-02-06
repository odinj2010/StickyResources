package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.JellyDropManagement;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.ai.jelly.JellyCustomAI;
import net.nfgbros.stickyresources.entity.ai.jelly.emotion.Emotions;
import net.nfgbros.stickyresources.entity.ai.jelly.goals.JellyForageGoal;
import net.nfgbros.stickyresources.entity.ai.jelly.goals.JellyHerdingGoal;
import net.nfgbros.stickyresources.entity.ai.jelly.goals.JellyWanderGoal;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;

public class JellyEntity extends Animal {
    private static final EntityDataAccessor<Boolean> IS_ALPHA = SynchedEntityData.defineId(JellyEntity.class, EntityDataSerializers.BOOLEAN);
    private int dropItemTickCounter = 0;
    private int submergedTicks = 0;
    private long lastChirpTime = 0;
    private final JellyCustomAI customAI;
    private final JellyDropManagement dropManagement;
    private Emotions.Emotion emotion = Emotions.Emotion.NEUTRAL;
    private JellyEntity mate;

    public JellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.customAI = new JellyCustomAI(this);
        this.dropManagement = new JellyDropManagement(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ALPHA, false);
    }

    public boolean isAlpha() {
        return this.entityData.get(IS_ALPHA);
    }

    public void setAlpha(boolean alpha) {
        this.entityData.set(IS_ALPHA, alpha);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsAlpha", this.isAlpha());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setAlpha(pCompound.getBoolean("IsAlpha"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (IS_ALPHA.equals(pKey)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public float getScale() {
        return this.isAlpha() ? 1.25F : super.getScale();
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
                .add(Attributes.MAX_HEALTH, type.maxHealth * StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_HEALTH_MULTIPLIER, 1.0D))
                .add(Attributes.ATTACK_DAMAGE, type.attackDamage * StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_DAMAGE_MULTIPLIER, 1.0D))
                .add(Attributes.MOVEMENT_SPEED, type.movementSpeed * StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_SPEED_MULTIPLIER, 1.0D));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        if (StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_CUSTOM_AI_ACTIVE, true)) {
            // New Custom Goals
            this.goalSelector.addGoal(4, new JellyHerdingGoal(this, 1.0D));
            this.goalSelector.addGoal(5, new JellyForageGoal(this, 1.1D));
            this.goalSelector.addGoal(6, new JellyWanderGoal(this, 0.8D));
            
            // Re-adding essential interactions even with custom AI
            if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_RESOURCE_ONLY_MODE, false)) {
                this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
                this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.SLIME_BALL), false));
                this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
            }
        } else {
            this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 2.5F));
            this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
            if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_RESOURCE_ONLY_MODE, false)) {
                this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
                this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.SLIME_BALL), false));
                this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
                this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
            }
        }
        this.getNavigation().setCanFloat(true);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) {
        if (this.level().isClientSide || this.isBaby()) {
            super.thunderHit(pLevel, pLightning);
            return;
        }
        ModEntities.JellyType type = this.getJellyType();
        if (type == ModEntities.JellyType.DEFAULT) {
            this.transformToJelly(ModEntities.JellyType.ELECTRIC);
        } else if (type == ModEntities.JellyType.RAWIRON) {
            this.transformToJelly(ModEntities.JellyType.MAGNET);
        } else {
            super.thunderHit(pLevel, pLightning);
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        ModEntities.JellyType type = this.getJellyType();
        
        if (type == ModEntities.JellyType.COW && stack.is(Items.BUCKET) && !this.isBaby()) {
            pPlayer.playSound(net.minecraft.sounds.SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack milkBucket = ItemUtils.createFilledResult(stack, pPlayer, Items.MILK_BUCKET.getDefaultInstance());
            pPlayer.setItemInHand(pHand, milkBucket);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        if (!this.level().isClientSide && !this.isBaby() && StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_PLAYER_TRANSFORMATIONS, true)) {
            Optional<ModEntities.JellyType.TransformationData> transformation = type.getTransformation(stack.getItem());
            if (transformation.isPresent()) {
                ModEntities.JellyType.TransformationData data = transformation.get();
                if (stack.getCount() >= data.cost()) {
                    if (!pPlayer.getAbilities().instabuild) {
                        stack.shrink(data.cost());
                    }
                    this.transformToJelly(data.result());
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    public void transformToJelly(ModEntities.JellyType newType) {
        if (this.level().isClientSide) return;
        
        JellyEntity newJelly = ModEntities.JELLY_ENTITIES.get(newType).get().create(this.level());
        if (newJelly != null) {
            newJelly.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            newJelly.setHealth(newJelly.getMaxHealth()); // Option A: Healing to full
            newJelly.clearFire(); // Option B: Clear fire from lightning
            newJelly.setCustomName(this.getCustomName());
            newJelly.setAlpha(this.isAlpha());
            if (this.hasCustomName()) newJelly.setCustomNameVisible(this.isCustomNameVisible());
            
            if (this.isBaby()) {
                newJelly.setAge(this.getAge());
            }

            this.level().addFreshEntity(newJelly);
            this.discard();
            
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + 0.5, this.getZ(), 5, 0.5, 0.5, 0.5, 0);
        }
    }

    public boolean isEmotion(Emotions.Emotion targetEmotion) {
        return this.emotion == targetEmotion;
    }

    public void playEmotionSound() {
        if (this.canChirp() && this.emotion.getSound() != null && this.emotion.getSound().get() != null) {
            this.playSound(this.emotion.getSound().get(), 1.0F, 1.0F);
            this.updateLastChirpTime();
        }
    }

    public void setEmotion(Emotions.Emotion emotion) {
        this.emotion = emotion;
    }

    public Emotions.Emotion getEmotion() {
        return emotion;
    }

    public void setMate(JellyEntity mate) {
        this.mate = mate;
    }

    public JellyEntity getMate() {
        return mate;
    }

    public void spawnHeartParticles() {
        if (this.level().isClientSide) {
            double offsetX = (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth();
            double offsetY = this.getRandom().nextDouble() * this.getBbHeight();
            double offsetZ = (this.getRandom().nextDouble() - 0.5D) * this.getBbWidth();
            this.level().addParticle(ParticleTypes.HEART, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 0.0D, 0.1D, 0.0D);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@Nonnull ServerLevel level, @Nonnull AgeableMob otherParent) {
        JellyEntity other = (JellyEntity) otherParent;
        ModEntities.JellyType type1 = this.getJellyType();
        ModEntities.JellyType type2 = other.getJellyType();
        
        ModEntities.JellyType offspringType = getHybridOffspring(type1, type2);
        
        if (offspringType == null) {
            offspringType = this.random.nextBoolean() ? type1 : type2;
        }

        JellyEntity offspring = ModEntities.JELLY_ENTITIES.get(offspringType).get().create(level);
        if (offspring != null) {
            offspring.setBaby(true);
            if (offspringType == ModEntities.JellyType.CAKE) {
                Player player = level.getNearestPlayer(this, 10.0D);
                if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                    triggerAdvancement(serverPlayer, "jelly_dex/sweet_tooth");
                }
            }
        }
        return offspring;
    }

    private ModEntities.JellyType getHybridOffspring(ModEntities.JellyType t1, ModEntities.JellyType t2) {
        // Elemental & Geological Hybrids
        if (match(t1, t2, ModEntities.JellyType.WATER, ModEntities.JellyType.LAVA)) return ModEntities.JellyType.OBSIDIAN;
        if (match(t1, t2, ModEntities.JellyType.SAND, ModEntities.JellyType.LAVA)) return ModEntities.JellyType.GLASS;
        if (match(t1, t2, ModEntities.JellyType.STONE, ModEntities.JellyType.WATER)) return ModEntities.JellyType.DIRT;
        if (match(t1, t2, ModEntities.JellyType.DIRT, ModEntities.JellyType.WATER)) return ModEntities.JellyType.GRASS;

        // Resource Hybrids
        if (match(t1, t2, ModEntities.JellyType.RAWGOLD, ModEntities.JellyType.WATER)) return ModEntities.JellyType.LAPIS;
        if (match(t1, t2, ModEntities.JellyType.COAL, ModEntities.JellyType.OBSIDIAN)) return ModEntities.JellyType.DIAMOND;
        if (match(t1, t2, ModEntities.JellyType.DIAMOND, ModEntities.JellyType.GRASS)) return ModEntities.JellyType.EMERALD;
        if (match(t1, t2, ModEntities.JellyType.LAPIS, ModEntities.JellyType.RAWGOLD)) return ModEntities.JellyType.RAWSAPPHIRE;
        if (match(t1, t2, ModEntities.JellyType.DIAMOND, ModEntities.JellyType.OBSIDIAN)) return ModEntities.JellyType.AMETHYST;

        // Biological & Sweet Hybrids
        if (match(t1, t2, ModEntities.JellyType.GRASS, ModEntities.JellyType.REDMUSHROOM)) return ModEntities.JellyType.COW;
        if (match(t1, t2, ModEntities.JellyType.WATER, ModEntities.JellyType.DIRT)) return ModEntities.JellyType.PRISMERINE;
        if (match(t1, t2, ModEntities.JellyType.STRAWBERRY, ModEntities.JellyType.HONEY)) return ModEntities.JellyType.CAKE;

        return null;
    }

    private boolean match(ModEntities.JellyType t1, ModEntities.JellyType t2, ModEntities.JellyType target1, ModEntities.JellyType target2) {
        return (t1 == target1 && t2 == target2) || (t1 == target2 && t2 == target1);
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_CUSTOM_AI_ACTIVE, true)) {
            customAI.tick();
        }
        
        if (!this.level().isClientSide) {
            handleEnvironmentalTransformations();
            if (this.isAlpha() && this.tickCount % 40 == 0) {
                applyAlphaAura();
            }
        } else {
            spawnTypeParticles();
        }

        if (this.isBaby()) return;
        dropItemTickCounter++;
        if (dropItemTickCounter >= StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_DROP_MIN_TICKS, 900) + random.nextInt(StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_DROP_MAX_TICKS, 900))) {
            dropManagement.dropJellyItem();
            dropItemTickCounter = 0;
        }
    }

    private void spawnTypeParticles() {
        if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_EMOTION_PARTICLES, true)) return;
        if (this.isAlpha() || this.random.nextInt(15) == 0) {
            ModEntities.JellyType type = this.getJellyType();
            net.minecraft.core.particles.ParticleOptions particle = null;
            
            switch (type) {
                case COAL, CHARCOAL -> particle = ParticleTypes.SMOKE;
                case REDSTONEDUST -> particle = ParticleTypes.ELECTRIC_SPARK;
                case ELECTRIC -> particle = ParticleTypes.ELECTRIC_SPARK;
                case FIRE, LAVA -> particle = ParticleTypes.FLAME;
                case AMETHYST -> particle = ParticleTypes.WITCH;
                case WATER -> particle = ParticleTypes.RAIN;
                case OBSIDIAN -> particle = ParticleTypes.PORTAL;
                case EMERALD -> particle = ParticleTypes.HAPPY_VILLAGER;
                case DIAMOND -> particle = ParticleTypes.SCRAPE;
            }

            if (particle != null) {
                this.level().addParticle(particle, this.getX() + (random.nextDouble() - 0.5) * this.getBbWidth(), this.getY() + random.nextDouble() * this.getBbHeight(), this.getZ() + (random.nextDouble() - 0.5) * this.getBbWidth(), 0, 0, 0);
            }
        }
    }

    private void applyAlphaAura() {
        if (!StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ALPHA_DYNAMICS, true)) return;
        List<JellyEntity> nearby = this.getNearbyJellies();
        for (JellyEntity other : nearby) {
            if (other.getJellyType() == this.getJellyType()) {
                other.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, 100, 0));
                other.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 100, 0));
            }
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, net.minecraft.world.damagesource.DamageSource source) {
        if (!this.level().isClientSide && fallDistance > 5.0F && this.getJellyType() == ModEntities.JellyType.GRAVEL) {
            this.transformToJelly(ModEntities.JellyType.SAND);
            this.playSound(net.minecraft.sounds.SoundEvents.GRAVEL_BREAK, 1.0F, 1.0F);
            
            // Trigger advancement for the nearest player
            Player player = this.level().getNearestPlayer(this, 10.0D);
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                triggerAdvancement(serverPlayer, "jelly_dex/shattered_dreams");
            }
            return false; 
        }
        return super.causeFallDamage(fallDistance, damageMultiplier, source);
    }

    public void triggerAdvancement(net.minecraft.server.level.ServerPlayer player, String advancementId) {
        net.minecraft.advancements.Advancement advancement = player.server.getAdvancements().getAdvancement(new net.minecraft.resources.ResourceLocation(net.nfgbros.stickyresources.StickyResources.MOD_ID, advancementId));
        if (advancement != null) {
            net.minecraft.advancements.AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) {
                for (String criteria : progress.getRemainingCriteria()) {
                    player.getAdvancements().award(advancement, criteria);
                }
            }
        }
    }

    private void handleEnvironmentalTransformations() {
        if (this.isBaby() || !StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ENVIRONMENTAL_TRANSFORMATIONS, true)) return;

        ModEntities.JellyType currentType = this.getJellyType();
        BlockPos pos = this.blockPosition();
        BlockState blockBelow = this.level().getBlockState(pos.below());

        if (currentType == ModEntities.JellyType.WATER) {
            if (this.level().getBiome(pos).value().coldEnoughToSnow(pos) || blockBelow.is(Blocks.ICE) || blockBelow.is(Blocks.PACKED_ICE) || blockBelow.is(Blocks.BLUE_ICE)) {
                if (this.random.nextInt(100) == 0) {
                    this.transformToJelly(ModEntities.JellyType.ICE);
                }
            }
        } else if (currentType == ModEntities.JellyType.DEFAULT) {
            if (this.level().dimension() == Level.NETHER) {
                 if (this.random.nextInt(200) == 0) {
                     this.transformToJelly(ModEntities.JellyType.FIRE);
                 }
            }
            if (this.isInWater()) {
                submergedTicks++;
                if (submergedTicks >= 100) {
                    this.transformToJelly(ModEntities.JellyType.WATER);
                }
            } else {
                submergedTicks = 0;
            }
        } else if (currentType == ModEntities.JellyType.STONE) {
            if (StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ENVIRONMENTAL_GRIEFING, true)) {
                if (this.level().isRainingAt(pos) || this.isInWater()) {
                    if (this.random.nextInt(200) == 0) {
                        this.transformToJelly(ModEntities.JellyType.COBBLESTONE);
                    }
                }
            }
        } else if (currentType == ModEntities.JellyType.SAND) {
            if (this.isOnFire()) {
                if (this.random.nextInt(50) == 0) {
                    this.clearFire();
                    this.transformToJelly(ModEntities.JellyType.GLASS);
                }
            }
        } else if (currentType == ModEntities.JellyType.COBBLESTONE) {
            if (StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_ENVIRONMENTAL_GRIEFING, true)) {
                if (blockBelow.is(Blocks.LAVA) || this.isInLava()) {
                    if (this.random.nextInt(200) == 0) {
                        this.transformToJelly(ModEntities.JellyType.STONE);
                    }
                }
            }
        }

        if (blockBelow.is(Blocks.HONEY_BLOCK)) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, 0.0D, 0.5D));
            if (currentType != ModEntities.JellyType.HONEY && this.random.nextInt(100) == 0) {
                this.transformToJelly(ModEntities.JellyType.HONEY);
            }
        }

        if (blockBelow.is(Blocks.SLIME_BLOCK)) {
            if (this.random.nextInt(10) == 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 1.5, 0));
                this.playEmotionSound();
            }
        }

        if (currentType == ModEntities.JellyType.LOGOAK && this.isOnFire()) {
            this.clearFire();
            this.transformToJelly(ModEntities.JellyType.CHARCOAL);
        }
    }

    public boolean canChirp() {
        return StickyResourcesConfig.safeGet(StickyResourcesConfig.JELLY_CHIRPS_ENABLED, true) && 
               (this.level().getGameTime() - lastChirpTime > 40); // 2 second cooldown
    }

    public void updateLastChirpTime() {
        this.lastChirpTime = this.level().getGameTime();
    }

    public void onReceiveSocialChirp(JellyEntity messenger) {
        if (!this.level().isClientSide && this.canChirp() && this.getRandom().nextFloat() < 0.7f) {
            // Respond after a slight delay
            this.level().getServer().execute(() -> {
                if (this.isAlive()) {
                    this.playEmotionSound();
                }
            });
        }
    }

    public List<JellyEntity> getNearbyJellies() {
        return this.level().getEntitiesOfClass(JellyEntity.class, this.getBoundingBox().inflate(10.0), entity -> entity != this);
    }

        public JellyCustomAI getCommunicationAI() {

            return this.customAI;

        }

    

        public void forceDrop() {

            this.dropManagement.dropJellyItem();

        }

    }

    