package qu.fixedVillagerTrades;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qu.fixedVillagerTrades.config.ModConfig;

public class FixedVillagerTrades implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static final String MOD_ID = "fixed-villager-trades";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ModConfig MOD_CONFIG = new ModConfig();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Finished Initializing " + MOD_ID);
	}

	public static boolean areTradesFixed() {
		return MOD_CONFIG.FIX_TRADES;
	}

	public static float getMaxDiscount() {
		return MOD_CONFIG.MAX_DISCOUNT;
	}
}
