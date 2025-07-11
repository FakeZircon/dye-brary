package zircon.dyebrary;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dyebrary implements ModInitializer {
	public static final String MOD_ID = "dyebrary";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("What A Wonderful Colourful World!");
		InitFuncs.ReadVanillaDyes();
	}
}