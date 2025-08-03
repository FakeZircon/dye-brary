package zircon.dyebrary.mixin.client;

import net.minecraft.block.entity.SignText;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.interfaces.ISignText;

@Mixin(SignBlockEntityRenderer.class)
public class SignRendererMixin {
    @ModifyVariable(method = "getColor", at = @At("STORE"), ordinal = 0)
    private static int modCol(int i, SignText sign){
        return ((ISignText)sign).dye_brary$getTextColour();
        //return 8816375;
    }
}
