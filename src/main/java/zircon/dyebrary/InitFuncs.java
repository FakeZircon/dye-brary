package zircon.dyebrary;

import net.minecraft.util.DyeColor;

public class InitFuncs {
    // somewhat of a misnomer? Will include any added non-vanilla dyes, but we're reading from the vanilla dyes enum
    // todo try and catch dye mod conflicts? That would probably have to be done with MM
    public static void ReadVanillaDyes(){
        Dyebrary.LOGGER.info("Reading existing dyes");
        for (DyeColor dye : DyeColor.values()) {
            //color components are not straight forward, cause why would it be :/
            float[] colComp = dye.getColorComponents();
            int dyeVal = ((int)(colComp[0]*255) << 16) + ((int)(colComp[1]*255) << 8) + (int)(colComp[2]*255);
            //Dyebrary.LOGGER.info(String.format("%h", dyeVal));
            ModDyeColour.DyeList.add(new ModDyeColour(dye.getName(), dyeVal, dye.getMapColor(), dye.getFireworkColor(), dye.getSignColor()));
        }
        //ModDyeColour.DyeList.forEach(dye -> Dyebrary.LOGGER.info(dye.getName()));
    }
}
