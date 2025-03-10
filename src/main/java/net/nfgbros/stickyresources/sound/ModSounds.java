package net.nfgbros.stickyresources.sound;

import net.nfgbros.stickyresources.StickyResources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    // Deferred register for sound events
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, StickyResources.MOD_ID);

    // Sound event for metal detector finding ore
    public static final RegistryObject<SoundEvent> METAL_DETECTOR_FOUND_ORE = registerSoundEvents("metal_detector_found_ore");

    // Block sound events
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_BREAK = registerSoundEvents("sound_block_break");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_STEP = registerSoundEvents("sound_block_step");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_FALL = registerSoundEvents("sound_block_fall");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_PLACE = registerSoundEvents("sound_block_place");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_HIT = registerSoundEvents("sound_block_hit");

    // General sound events
    public static final RegistryObject<SoundEvent> BAR_BRAWL = registerSoundEvents("bar_brawl");

    // Custom Forge sound type for block sounds
    public static final ForgeSoundType SOUND_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.SOUND_BLOCK_BREAK, ModSounds.SOUND_BLOCK_STEP, ModSounds.SOUND_BLOCK_PLACE,
            ModSounds.SOUND_BLOCK_HIT, ModSounds.SOUND_BLOCK_FALL);

    // Helper method to register sound events
    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(StickyResources.MOD_ID, name)));
    }

    // Method to register all sound events
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
