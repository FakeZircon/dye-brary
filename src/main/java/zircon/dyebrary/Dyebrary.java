package zircon.dyebrary;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dyebrary implements ModInitializer {
	public static final String MOD_ID = "dyebrary";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//test adding new dye colour
	public static final ModDyeColour PIGEON_BLUE = new ModDyeColour("pigeon_blue", 0x8686f7, MapColor.TERRACOTTA_BLUE, 0x8686f7, 0x8686f7);
	public static final Item PIGEON_BLUE_DYE = registerModDyeItem(new Identifier(MOD_ID, "pigeon_blue_dye"), PIGEON_BLUE);

	public static final Block PIGEON_GLASS = new ModStainedGlassBlock(PIGEON_BLUE, FabricBlockSettings.copyOf(Blocks.WHITE_STAINED_GLASS));

	@Override
	public void onInitialize() {
		LOGGER.info("What A Wonderful Colourful World!");
		BlockItem blockItem = new BlockItem(PIGEON_GLASS, new Item.Settings());
		Registry.register(Registries.ITEM, "dyebrary:pigeon_glass", blockItem);
		Registry.register(Registries.BLOCK, "dyebrary:pigeon_glass", PIGEON_GLASS);
	}

	//helper function for testing only, this should be done by the modder themself for max customization
	private static Item registerModDyeItem(Identifier id, ModDyeColour colour){
		ModDyeItem dye = new ModDyeItem(colour, new FabricItemSettings());
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register( (itemGroup) -> itemGroup.addBefore(Items.BOWL, dye));
		return Registry.register(Registries.ITEM, id, dye);
	}
}