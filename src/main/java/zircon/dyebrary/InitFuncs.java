package zircon.dyebrary;

import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class InitFuncs {
    // somewhat of a misnomer? Will include any added non-vanilla dyes, but we're reading from the vanilla dyes enum
    // todo try and catch dye mod conflicts? That would probably have to be done with MM
    public static void ReadVanillaDyes(){
        Dyebrary.LOGGER.info("Reading existing dyes");
        for (DyeColor dye : DyeColor.values()) {
            ModDyeColour.DyeList.add(new ModDyeColour(dye.getName(), dye.getColorComponents(), dye.getMapColor(), dye.getFireworkColor(), dye.getSignColor()));
        }
        //ModDyeColour.DyeList.forEach(dye -> Dyebrary.LOGGER.info(dye.getName()));
    }

    public static void GetShulkerTextures(){
        Dyebrary.LOGGER.info("Arranging Vanilla Shulker Textures"); //this is a stop gap solution, there will be missing shulker textures
        for (DyeColor dye : DyeColor.values()){
            //grab vanilla texture ids and put into ModDyeColour.ShulkerTextureList
        }
//        Identifier[] COLORED_TEXTURES = (Identifier[])TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
//                .stream()
//                .map(spriteId -> new Identifier("textures/" + spriteId.getTextureId().getPath() + ".png"))
//                .toArray(Identifier[]::new);
    }
}
