package net.stln.launchersandarrows.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundInit {
    public static final Identifier BOW_RELEASE_ID = Identifier.of("launchers_and_arrows:bow_release");
    public static SoundEvent BOW_RELEASE = SoundEvent.of(BOW_RELEASE_ID);

    public static void registerSoundEvents() {
        Registry.register(Registries.SOUND_EVENT, BOW_RELEASE_ID, BOW_RELEASE);
    }
}
