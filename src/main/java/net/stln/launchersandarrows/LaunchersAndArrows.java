package net.stln.launchersandarrows;

import net.fabricmc.api.ModInitializer;

import net.stln.launchersandarrows.item.ItemInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaunchersAndArrows implements ModInitializer {
	public static final String MOD_ID = "launchers_and_arrows";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ItemInit.registerModItems();
	}
}