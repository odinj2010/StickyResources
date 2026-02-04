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
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, StickyResources.MOD_ID);

    public static final RegistryObject<SoundEvent> METAL_DETECTOR_FOUND_ORE = registerSoundEvents("metal_detector_found_ore");

    public static final RegistryObject<SoundEvent> SOUND_BLOCK_BREAK = registerSoundEvents("sound_block_break");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_STEP = registerSoundEvents("sound_block_step");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_FALL = registerSoundEvents("sound_block_fall");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_PLACE = registerSoundEvents("sound_block_place");
    public static final RegistryObject<SoundEvent> SOUND_BLOCK_HIT = registerSoundEvents("sound_block_hit");

    public static final RegistryObject<SoundEvent> BAR_BRAWL = registerSoundEvents("bar_brawl");

    public static final RegistryObject<SoundEvent> JELLY_SAD = registerSoundEvents("jelly/communication/jelly_sad");
    public static final RegistryObject<SoundEvent> JELLY_HAPPY = registerSoundEvents("jelly/communication/jelly_happy");
    public static final RegistryObject<SoundEvent> JELLY_HORNY = registerSoundEvents("jelly/communication/jelly_horny");
    public static final RegistryObject<SoundEvent> JELLY_NEUTRAL = registerSoundEvents("jelly/communication/jelly_neutral");
    public static final RegistryObject<SoundEvent> JELLY_ANGRY = registerSoundEvents("jelly/communication/jelly_angry");
    public static final RegistryObject<SoundEvent> JELLY_BORED = registerSoundEvents("jelly/communication/jelly_bored");
    public static final RegistryObject<SoundEvent> JELLY_CALM = registerSoundEvents("jelly/communication/jelly_calm");
    public static final RegistryObject<SoundEvent> JELLY_CONFUSION = registerSoundEvents("jelly/communication/jelly_confusion");
    public static final RegistryObject<SoundEvent> JELLY_EXCITEMENT = registerSoundEvents("jelly/communication/jelly_excitement");
    public static final RegistryObject<SoundEvent> JELLY_FEAR = registerSoundEvents("jelly/communication/jelly_fear");
    public static final RegistryObject<SoundEvent> JELLY_JEALOUSY = registerSoundEvents("jelly/communication/jelly_jealousy");
    public static final RegistryObject<SoundEvent> JELLY_LOVE = registerSoundEvents("jelly/communication/jelly_love");
    public static final RegistryObject<SoundEvent> JELLY_SURPRISE = registerSoundEvents("jelly/communication/jelly_surprise");


    public static final ForgeSoundType SOUND_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.SOUND_BLOCK_BREAK, ModSounds.SOUND_BLOCK_STEP, ModSounds.SOUND_BLOCK_PLACE,
            ModSounds.SOUND_BLOCK_HIT, ModSounds.SOUND_BLOCK_FALL);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(StickyResources.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
