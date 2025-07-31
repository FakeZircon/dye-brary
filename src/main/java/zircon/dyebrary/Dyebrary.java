package zircon.dyebrary;

import net.fabricmc.api.ModInitializer;

import net.minecraft.block.MapColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dyebrary implements ModInitializer {
	public static final String MOD_ID = "dyebrary";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("What A Wonderful Colourful World!");
	}

	//test adding new dye colour
	public static final ModDyeColour PIGEON_BLUE = new ModDyeColour("pigeon_blue", 0x8686f7, MapColor.TERRACOTTA_BLUE, 0x8686f7, 0x8686f7);
}