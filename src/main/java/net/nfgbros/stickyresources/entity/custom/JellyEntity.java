package net.nfgbros.stickyresources.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.ai.jelly.JellyCustomAI;
import net.nfgbros.stickyresources.entity.JellyDropManagement;
import net.nfgbros.stickyresources.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JellyEntity extends Animal {
    private int dropItemTickCounter = 0;
    private final JellyCustomAI customAI;
    private final JellyDropManagement dropManagement;
    private String emotion;

    public JellyEntity(EntityType<? extends JellyEntity> entityType, Level level) {
        super(entityType, level);
        this.customAI = new JellyCustomAI(this);
        this.dropManagement = new JellyDropManagement(this);
        this.emotion = "NEUTRAL"; // Default emotion.  Initialize here.
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
                .add(Attributes.ATTACK_DAMAGE, 1D);
    }

    @Override
    protected void registerGoals() {
        if (StickyResourcesConfig.JELLY_CUSTOM_AI_ACTIVE.get() == false) {
            // Register AI goals here if needed
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 2.5F));
            this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
            if (StickyResourcesConfig.JELLY_RESOURCE_ONLY_MODE.get() == false) {
                this.goalSelector.addGoal(2, new BreedGoal(this, 1.15D));
                this.goalSelector.addGoal(3, new TemptGoal(this, 1.15D, Ingredient.of(Items.SLIME_BALL), false));
                this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
                this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.35D));
            }
        }
        // Enable jumping for the jelly's navigation system
        this.getNavigation().setCanFloat(true);
    }

    public boolean isHappy() {
        return "Happy".equals(this.emotion);
    }

    public boolean isHorny() {
        return "Horny".equals(this.emotion);
    }

    public void playHappySound() {
        // Play the happy sound
        this.playSound(ModSounds.JELLY_HAPPY.get(), 1.0F, 1.0F);
    }

    public void playNeutralSound() {
        // Play the neutral sound
        this.playSound(ModSounds.JELLY_NEUTRAL.get(), 1.0F, 1.0F);
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getEmotion() {
        return emotion;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        // Cast the other parent to JellyEntity
        JellyEntity other = (JellyEntity) otherParent;
        // If parents are a water jelly and a lava jelly, produce an obsidian jelly baby.
        if (this.getJellyType() == ModEntities.JellyType.WATER && other.getJellyType() == ModEntities.JellyType.LAVA ||
                this.getJellyType() == ModEntities.JellyType.LAVA && other.getJellyType() == ModEntities.JellyType.WATER) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.OBSIDIAN).get().create(level);
        }
        // If parents are a sand jelly and a lava jelly, produce an obsidian jelly baby.
        else if (this.getJellyType() == ModEntities.JellyType.SAND && other.getJellyType() == ModEntities.JellyType.LAVA ||
                this.getJellyType() == ModEntities.JellyType.LAVA && other.getJellyType() == ModEntities.JellyType.SAND) {
            return ModEntities.JELLY_ENTITIES.get(ModEntities.JellyType.GLASS).get().create(level);
        }
        // Otherwise, default to producing offspring of this parent's type.
        return ModEntities.JELLY_ENTITIES.get(this.getJellyType()).get().create(level);
    }

    @Override
    public void tick() {
        super.tick();
        if (StickyResourcesConfig.JELLY_CUSTOM_AI_ACTIVE.get()) {
            List<JellyEntity> nearbyJellies = getNearbyJellies(); // Get nearby jellies
            customAI.tick(nearbyJellies); // Pass the list to the custom AI
        }
        // If this is a baby, do not drop items
        if (this.isBaby()) return;
        // Drop a jelly item every 20 ticks (10 seconds) when this is a regular jelly entity
        dropItemTickCounter++;
        if (dropItemTickCounter >= 600 + random.nextInt(600)) {
            dropManagement.dropJellyItem();
            dropItemTickCounter = 0;
        }
    }

    private List<JellyEntity> getNearbyJellies() {
        List<JellyEntity> nearbyJellies = new ArrayList<>();
        // Get all entities within a 10-block radius
        for (JellyEntity otherJelly : this.level().getEntitiesOfClass(JellyEntity.class, this.getBoundingBox().inflate(10.0))) {
            if (otherJelly != this) { // Exclude itself
                nearbyJellies.add(otherJelly);
            }
        }
        return nearbyJellies;
    }

    public JellyCustomAI getCommunicationAI() {
        return this.customAI;
    }
}

