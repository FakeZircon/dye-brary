package zircon.dyebrary.mixin.block;

import net.minecraft.block.BeaconBlock;
import org.spongepowered.asm.mixin.Mixin;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.IStainable;

@Mixin(BeaconBlock.class)
public class BeaconMixin implements IStainable {
    @Override
    public ModDyeColour getModColour() {
        return ModDyeColour.getByHex(16383998);
        //return ModDyeColour.getByHex(0x8686f7); //pigeon blue test
    }
}
