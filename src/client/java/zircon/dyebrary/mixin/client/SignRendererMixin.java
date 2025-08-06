package zircon.dyebrary.mixin.client;

import net.minecraft.block.entity.SignText;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.Dyebrary;
import zircon.dyebrary.interfaces.ISignText;

@Mixin(SignBlockEntityRenderer.class)
public class SignRendererMixin {
    @ModifyVariable(method = "getColor", at = @At("STORE"), ordinal = 0)
    private static int modCol(int i, SignText sign){
        return ((ISignText)sign).dye_brary$getTextColour();
    }

    //hate to use "name" here, but I straight up don't think I can do without it
    @ModifyVariable(method = "renderText", at = @At("STORE"), name = "k")
    private static int modGlowCol(int k, BlockPos pos, SignText sign){
        return ((ISignText)sign).dye_brary$getTextColour();
    }
}
