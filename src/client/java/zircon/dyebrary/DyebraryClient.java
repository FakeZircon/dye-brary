package zircon.dyebrary;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class DyebraryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		GetShulkerTextures();
		//test adding new shulker texture for new dye colour
		//ModDyeColour.AddShulkerTexture(ModDyeColour.getByHex(0xf7f7f7), new Identifier(Dyebrary.MOD_ID, "textures/entity/shulker/shulker_test.png"));
	}

	public static void GetShulkerTextures(){
		//this assumes vanilla dyes get their original ids but also, imagine a dye mod stealing the 0 index, I cannot even think of what all that would break
		Dyebrary.LOGGER.info("Arranging Vanilla Shulker Textures");
		Identifier[] COLORED_TEXTURES = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
				.stream()
				.map(spriteId -> new Identifier("textures/" + spriteId.getTextureId().getPath() + ".png"))
				.toArray(Identifier[]::new);

		//broke this when I changed Dyelist to a hashmap
//		for (int i = 0; i < 16; i++){
//			//grab vanilla texture ids and put into ModDyeColour.ShulkerTextures
//			ModDyeColour.ShulkerTextures.put(ModDyeColour.DyeList., COLORED_TEXTURES[i]);
//		}
		for (int i = 0; i < 16; i++){
			float[] colComps = DyeColor.values()[i].getColorComponents();
			int colHex = ((int)(colComps[0]*255) << 16) + ((int)(colComps[1]*255) << 8) + (int)(colComps[2]*255);
			ModDyeColour.ShulkerTextures.put(ModDyeColour.DyeList.get(colHex), COLORED_TEXTURES[i]);
		}
	}
}