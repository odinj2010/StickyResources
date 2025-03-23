package net.nfgbros.stickyresources.entity.ai.goals.customaigoals.social;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.nfgbros.stickyresources.entity.custom.JellyEntity;
import net.nfgbros.stickyresources.StickyResourcesConfig;
import net.nfgbros.stickyresources.entity.ModEntities;
import net.nfgbros.stickyresources.entity.ModEntities.JellyType;

import java.util.EnumSet;

public class JellyBreedGoal extends Goal {
    private final JellyEntity jelly;
    private JellyEntity partner;
    private int breedingCooldown;

    public JellyBreedGoal(JellyEntity jelly, int i, int i1) {
        this.jelly = jelly;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        // Only breed if both breeding ritual and nesting goals are enabled in the config
        if (!StickyResourcesConfig.JELLY_BREEDING_ACTIVE.get()) {
            return false;
        }

        // Check if the jelly is ready to breed
        if (!jelly.isInLove() || jelly.getAge() != 0) {
            return false;
        }

        // If the jelly already has a mate, only breed with that mate
        if (jelly.getMate() != null) {
            this.partner = jelly.getMate();
            return partner != null && partner.isAlive() && partner.isInLove();
        }

        // Find a suitable partner
        this.partner = findPartner();
        return partner != null;
    }

    @Override
    public boolean canContinueToUse() {
        return partner != null && partner.isAlive() && partner.isInLove() && jelly.isInLove();
    }

    @Override
    public void start() {
        // Reset breeding cooldown
        this.breedingCooldown = 100; // 5-second cooldown

        // Set the mate if it hasn't been set yet
        if (jelly.getMate() == null && partner != null) {
            jelly.setMate(partner);
            partner.setMate(jelly); // Set the mate for the partner as well
        }
    }

    @Override
    public void stop() {
        // Do not reset the partner if it's the mate
        if (partner != null && partner != jelly.getMate()) {
            this.partner = null;
        }
    }

    @Override
    public void tick() {
        // Move towards the partner
        jelly.getLookControl().setLookAt(partner, 10.0F, jelly.getMaxHeadXRot());
        jelly.getNavigation().moveTo(partner, 1.15);

        // Breed if close enough
        if (jelly.distanceToSqr(partner) < 9.0) {
            if (breedingCooldown <= 0) {
                breed();
            } else {
                breedingCooldown--;
            }
        }
    }

    private JellyEntity findPartner() {
        // Find nearby jellies that are ready to breed
        return jelly.level().getEntitiesOfClass(JellyEntity.class, jelly.getBoundingBox().inflate(8.0), this::canBreedWith)
                .stream()
                .filter(JellyEntity::isInLove)
                .findFirst()
                .orElse(null);
    }

    private boolean canBreedWith(JellyEntity other) {
        JellyType thisType = jelly.getJellyType();
        JellyType otherType = other.getJellyType();

        // Define interbreeding rules
        return (thisType == JellyType.WATER && otherType == JellyType.LAVA) || // Water + Lava = Obsidian
                (thisType == JellyType.LAVA && otherType == JellyType.WATER) || // Lava + Water = Obsidian
                (thisType == JellyType.SAND && otherType == JellyType.LAVA) || // Sand + Lava = Glass
                (thisType == JellyType.LAVA && otherType == JellyType.SAND) || // Lava + Sand = Glass
                (thisType == JellyType.WATER && otherType == JellyType.ICE) || // Water + Ice = Frosted Ice
                (thisType == JellyType.ICE && otherType == JellyType.WATER) || // Ice + Water = Frosted Ice
                (thisType == JellyType.LAVA && otherType == JellyType.OBSIDIAN) || // Lava + Obsidian = Basalt
                (thisType == JellyType.OBSIDIAN && otherType == JellyType.LAVA) || // Obsidian + Lava = Basalt
                (thisType == JellyType.SAND && otherType == JellyType.WATER) || // Sand + Water = Clay
                (thisType == JellyType.WATER && otherType == JellyType.SAND) || // Water + Sand = Clay
                (thisType == JellyType.COAL && otherType == JellyType.LAVA) || // Coal + Lava = Charcoal
                (thisType == JellyType.LAVA && otherType == JellyType.COAL) || // Lava + Coal = Charcoal
                (thisType == JellyType.DIRT && otherType == JellyType.WATER) || // Dirt + Water = Mud
                (thisType == JellyType.WATER && otherType == JellyType.DIRT) || // Water + Dirt = Mud
                (thisType == JellyType.GRASS && otherType == JellyType.DIRT) || // Grass + Dirt = Mycelium
                (thisType == JellyType.DIRT && otherType == JellyType.GRASS) || // Dirt + Grass = Mycelium
                (thisType == JellyType.REDSTONEDUST && otherType == JellyType.LAPIS) || // Redstone + Lapis = Prismarine
                (thisType == JellyType.LAPIS && otherType == JellyType.REDSTONEDUST) || // Lapis + Redstone = Prismarine
                (thisType == JellyType.ENDERPEARL && otherType == JellyType.OBSIDIAN) || // Ender Pearl + Obsidian = End Stone
                (thisType == JellyType.OBSIDIAN && otherType == JellyType.ENDERPEARL) || // Obsidian + Ender Pearl = End Stone
                (thisType == JellyType.RAWIRON && otherType == JellyType.LAVA) || // Raw Iron + Lava = Steel
                (thisType == JellyType.LAVA && otherType == JellyType.RAWIRON) || // Lava + Raw Iron = Steel
                (thisType == JellyType.RAWCOPPER && otherType == JellyType.LAVA) || // Raw Copper + Lava = Bronze
                (thisType == JellyType.LAVA && otherType == JellyType.RAWCOPPER) || // Lava + Raw Copper = Bronze
                (thisType == JellyType.RAWGOLD && otherType == JellyType.LAVA) || // Raw Gold + Lava = Gilded Gold
                (thisType == JellyType.LAVA && otherType == JellyType.RAWGOLD) || // Lava + Raw Gold = Gilded Gold
                //(thisType == JellyType.REDMUSHROOM && otherType == JellyType.BROWNMUSHROOM) || // Red Mushroom + Brown Mushroom = Mushroom Block
                //(thisType == JellyType.BROWNMUSHROOM && otherType == JellyType.REDMUSHROOM) || // Brown Mushroom + Red Mushroom = Mushroom Block
                (thisType == JellyType.HONEY && otherType == JellyType.WATER) || // Honey + Water = Sugar
                (thisType == JellyType.WATER && otherType == JellyType.HONEY) || // Water + Honey = Sugar
                (thisType == JellyType.CHARCOAL && otherType == JellyType.OBSIDIAN) || // Charcoal + Obsidian = Blackstone
                (thisType == JellyType.OBSIDIAN && otherType == JellyType.CHARCOAL) || // Obsidian + Charcoal = Blackstone
                (thisType == JellyType.STRAWBERRY && otherType == JellyType.HONEY) || // Strawberry + Honey = Jam
                (thisType == JellyType.HONEY && otherType == JellyType.STRAWBERRY) || // Honey + Strawberry = Jam
                (thisType == JellyType.PUMPKIN && otherType == JellyType.HONEY) || // Pumpkin + Honey = Pumpkin Pie
                (thisType == JellyType.HONEY && otherType == JellyType.PUMPKIN) || // Honey + Pumpkin = Pumpkin Pie
                (thisType == JellyType.CAKE && otherType == JellyType.HONEY) || // Cake + Honey = Honey Cake
                (thisType == JellyType.HONEY && otherType == JellyType.CAKE) || // Honey + Cake = Honey Cake
                (thisType == otherType); // Same type can always breed
    }


    private void breed() {
        // Spawn the baby jelly
        JellyEntity baby = createBaby();

        if (baby != null) {
            // Reset breeding cooldown for both parents
            jelly.setAge(6000); // 5-minute cooldown
            partner.setAge(6000);
            jelly.resetLove();
            partner.resetLove();

            // Spawn the baby
            baby.setBaby(true);
            baby.moveTo(jelly.getX(), jelly.getY(), jelly.getZ(), 0.0F, 0.0F);
            jelly.level().addFreshEntity(baby);
        }
    }

    private JellyEntity createBaby() {
        JellyType thisType = jelly.getJellyType();
        JellyType otherType = partner.getJellyType();

        JellyType babyType = JellyType.DEFAULT; // Default baby type

        // Define baby type based on interbreeding rules
        if ((thisType == JellyType.WATER && otherType == JellyType.LAVA) ||
                (thisType == JellyType.LAVA && otherType == JellyType.WATER)) {
            babyType = JellyType.OBSIDIAN;
        } else if ((thisType == JellyType.SAND && otherType == JellyType.LAVA) ||
                (thisType == JellyType.LAVA && otherType == JellyType.SAND)) {
            babyType = JellyType.GLASS;
        } else if ((thisType == JellyType.COAL && otherType == JellyType.LAVA) ||
                (thisType == JellyType.LAVA && otherType == JellyType.COAL)) {
            babyType = JellyType.CHARCOAL;
        } else if ((thisType == JellyType.REDSTONEDUST && otherType == JellyType.LAPIS) ||
                (thisType == JellyType.LAPIS && otherType == JellyType.REDSTONEDUST)) {
            babyType = JellyType.PRISMERINE;
        } else if (thisType == otherType) {
            babyType = thisType; // Same type produces the same type
        } /** else if ((thisType == JellyType.WATER && otherType == JellyType.ICE) ||
         (thisType == JellyType.ICE && otherType == JellyType.WATER)) {
         babyType = JellyType.FROSTED_ICE;
         }
         else if ((thisType == JellyType.LAVA && otherType == JellyType.OBSIDIAN) ||
         (thisType == JellyType.OBSIDIAN && otherType == JellyType.LAVA)) {
         babyType = JellyType.BASALT;
         } else if ((thisType == JellyType.SAND && otherType == JellyType.WATER) ||
         (thisType == JellyType.WATER && otherType == JellyType.SAND)) {
         babyType = JellyType.CLAY;
         } else if ((thisType == JellyType.DIRT && otherType == JellyType.WATER) ||
         (thisType == JellyType.WATER && otherType == JellyType.DIRT)) {
         babyType = JellyType.MUD;
         } else if ((thisType == JellyType.GRASS && otherType == JellyType.DIRT) ||
         (thisType == JellyType.DIRT && otherType == JellyType.GRASS)) {
         babyType = JellyType.MYCELIUM;
         } else if ((thisType == JellyType.ENDERPEARL && otherType == JellyType.OBSIDIAN) ||
         (thisType == JellyType.OBSIDIAN && otherType == JellyType.ENDERPEARL)) {
         babyType = JellyType.END_STONE;
         } else if ((thisType == JellyType.RAWIRON && otherType == JellyType.LAVA) ||
         (thisType == JellyType.LAVA && otherType == JellyType.RAWIRON)) {
         babyType = JellyType.STEEL;
         } else if ((thisType == JellyType.RAWCOPPER && otherType == JellyType.LAVA) ||
         (thisType == JellyType.LAVA && otherType == JellyType.RAWCOPPER)) {
         babyType = JellyType.BRONZE;
         } else if ((thisType == JellyType.RAWGOLD && otherType == JellyType.LAVA) ||
         (thisType == JellyType.LAVA && otherType == JellyType.RAWGOLD)) {
         babyType = JellyType.GILDED_GOLD;
         } else if ((thisType == JellyType.REDMUSHROOM && otherType == JellyType.BROWNMUSHROOM) ||
         (thisType == JellyType.BROWNMUSHROOM && otherType == JellyType.REDMUSHROOM)) {
         babyType = JellyType.MUSHROOM_BLOCK;
         } else if ((thisType == JellyType.HONEY && otherType == JellyType.WATER) ||
         (thisType == JellyType.WATER && otherType == JellyType.HONEY)) {
         babyType = JellyType.SUGAR;
         } else if ((thisType == JellyType.CHARCOAL && otherType == JellyType.OBSIDIAN) ||
         (thisType == JellyType.OBSIDIAN && otherType == JellyType.CHARCOAL)) {
         babyType = JellyType.BLACKSTONE;
         } else if ((thisType == JellyType.STRAWBERRY && otherType == JellyType.HONEY) ||
         (thisType == JellyType.HONEY && otherType == JellyType.STRAWBERRY)) {
         babyType = JellyType.JAM;
         } else if ((thisType == JellyType.PUMPKIN && otherType == JellyType.HONEY) ||
         (thisType == JellyType.HONEY && otherType == JellyType.PUMPKIN)) {
         babyType = JellyType.PUMPKIN_PIE;
         } else if ((thisType == JellyType.CAKE && otherType == JellyType.HONEY) ||
         (thisType == JellyType.HONEY && otherType == JellyType.CAKE)) {
         babyType = JellyType.HONEY_CAKE;
         }
         */

        // Create the baby jelly
        return ModEntities.JELLY_ENTITIES.get(babyType).get().create((ServerLevel) jelly.level());
    }

}
