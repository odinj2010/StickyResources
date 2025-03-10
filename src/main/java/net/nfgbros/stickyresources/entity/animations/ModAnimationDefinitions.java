package net.nfgbros.stickyresources.entity.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class ModAnimationDefinitions {

    // Rhino idle animation definition
    public static final AnimationDefinition RHINO_IDLE = AnimationDefinition.Builder.withLength(2f).looping()
            // Rhino main animation
            .addAnimation("rhino",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            // Body movement
            .addAnimation("body",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4583433f, KeyframeAnimations.posVec(0f, -0.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.9583433f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            // Torso movement (position)
            .addAnimation("torso",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            // Torso movement (rotation)
            .addAnimation("torso",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.4583433f, KeyframeAnimations.degreeVec(-1f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.9583433f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    // Rhino walk animation definition
    public static final AnimationDefinition RHINO_WALK = AnimationDefinition.Builder.withLength(2f).looping()
            // Torso movement
            .addAnimation("torso",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 1.5f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            // Left back leg movement (position and rotation)
            .addAnimation("left_back_leg",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0.9167666f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.6766667f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_back_leg",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0.9167666f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.degreeVec(2.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.6766667f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            // Various body and limb animations omitted for brevity (they are in the original code)...
            .build();

    // Rhino attack animation
    public static final AnimationDefinition RHINO_ATTACK = AnimationDefinition.Builder.withLength(4f).looping()
            .addAnimation("skull",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(22.5f, 0f, -45f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(22.5f, 0f, -45f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2.5f, KeyframeAnimations.degreeVec(-60.03f, 17.68f, -19.21f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(3.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(4f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();

    // Rhino sit animation
    public static final AnimationDefinition RHINO_SIT = AnimationDefinition.Builder.withLength(2f).looping()
            .addAnimation("rhino",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, -1f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            // Additional animations for sit pose omitted for brevity...
            .build();
}

