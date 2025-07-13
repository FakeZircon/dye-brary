package zircon.dyebrary;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.Identifier;

public class DyebraryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		GetShulkerTextures();
	}

	public static void GetShulkerTextures(){
		//this assumes vanilla dyes get their original ids but also, imagine a dye mod stealing the 0 index, I cannot even think of what all that would break
		Dyebrary.LOGGER.info("Arranging Vanilla Shulker Textures");
		Identifier[] COLORED_TEXTURES = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
				.stream()
				.map(spriteId -> new Identifier("textures/" + spriteId.getTextureId().getPath() + ".png"))
				.toArray(Identifier[]::new);

		for (int i = 0; i < 16; i++){
			//grab vanilla texture ids and put into ModDyeColour.ShulkerTextures
			ModDyeColour.ShulkerTextures.put(ModDyeColour.DyeList.get(i).getName(), COLORED_TEXTURES[i]);
		}
	}
}