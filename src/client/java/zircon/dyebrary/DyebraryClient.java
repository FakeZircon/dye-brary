package zircon.dyebrary;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class DyebraryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		GetShulkerTextures();
	}

	public static void GetShulkerTextures(){
		Dyebrary.LOGGER.info("Arranging Vanilla Shulker Textures"); //this is a stop gap solution, there will be missing shulker textures
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