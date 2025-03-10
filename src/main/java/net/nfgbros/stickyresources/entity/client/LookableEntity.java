package net.nfgbros.stickyresources.entity.custom;

// Defines a contract for entities that can provide their looking direction
public interface LookableEntity {

    /**
     * Gets the yaw rotation (Y-axis) of the direction the entity is currently looking towards.
     *
     * @return the yaw rotation in degrees
     */
    float getLookingYRot();
}