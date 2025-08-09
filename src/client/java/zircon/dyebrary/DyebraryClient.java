package zircon.dyebrary;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import static zircon.dyebrary.Dyebrary.PIGEON_BLUE;
import static zircon.dyebrary.ModDyeColour.ShulkerTextures;

public class DyebraryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		GetShulkerTextures();
		//test adding new shulker texture for new dye colour
		ModDyeColour.AddShulkerTexture(PIGEON_BLUE, new Identifier(Dyebrary.MOD_ID, "textures/entity/shulker/shulker_pigeon_blue.png"));
	}

//	public static void AddShulkerTexture(ModDyeColour colour, Identifier textureID){
//		ShulkerTextures.put(colour, textureID);
//	}

	public static void GetShulkerTextures(){
		//this assumes vanilla dyes get their original ids but also, imagine a dye mod stealing the 0 index, I cannot even think of what all that would break
		Dyebrary.LOGGER.info("Arranging Vanilla Shulker Textures");
		Identifier[] COLORED_TEXTURES = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
				.stream()
				.map(spriteId -> new Identifier("textures/" + spriteId.getTextureId().getPath() + ".png"))
				.toArray(Identifier[]::new);

		//only do this for vanilla dyes so we don't overindex COLORED_TEXTURES
		for (int i = 0; i < 16; i++){
			float[] colComps = DyeColor.values()[i].getColorComponents();
			int colHex = ((int)(colComps[0]*255) << 16) + ((int)(colComps[1]*255) << 8) + (int)(colComps[2]*255);
			ShulkerTextures.put(ModDyeColour.getByHex(colHex), COLORED_TEXTURES[i]);
		}
	}
}