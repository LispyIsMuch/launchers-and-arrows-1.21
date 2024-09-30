package net.stln.launchersandarrows;

import net.fabricmc.api.ModInitializer;

import net.stln.launchersandarrows.entity.EntityInit;
import net.stln.launchersandarrows.entity.VillagerTradeInit;
import net.stln.launchersandarrows.item.ItemInit;
import net.stln.launchersandarrows.item_group.ItemGroupInit;
import net.stln.launchersandarrows.particle.ParticleInit;
import net.stln.launchersandarrows.sound.SoundInit;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaunchersAndArrows implements ModInitializer {
	public static final String MOD_ID = "launchers_and_arrows";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SoundInit.registerSoundEvents();
		ParticleInit.registerParticleTypes();
		StatusEffectInit.registerStatusEffect();
		ItemInit.registerModItems();
		ItemInit.registerAttributeEffect();
		ItemGroupInit.registerItemGroup();
		EntityInit.registerModEntities();
		VillagerTradeInit.registerVillagerTrades();
	}
}