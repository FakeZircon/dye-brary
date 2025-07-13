package zircon.dyebrary;

import net.minecraft.util.DyeColor;

public class InitFuncs {
    // somewhat of a misnomer? Will include any added non-vanilla dyes, but we're reading from the vanilla dyes enum
    // todo try and catch dye mod conflicts? That would probably have to be done with MM
    public static void ReadVanillaDyes(){
        Dyebrary.LOGGER.info("Reading existing dyes");
        for (DyeColor dye : DyeColor.values()) {
            ModDyeColour.DyeList.add(new ModDyeColour(dye.getName(), dye.getColorComponents(), dye.getMapColor(), dye.getFireworkColor(), dye.getSignColor()));
        }
    }
}
