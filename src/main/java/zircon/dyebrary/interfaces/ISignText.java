package zircon.dyebrary.interfaces;


import net.minecraft.block.entity.SignText;

public interface ISignText {
    void dye_brary$setTextColour(int signColour);
    int dye_brary$getTextColour();
    SignText dye_brary$withColour(int signColour);
}
