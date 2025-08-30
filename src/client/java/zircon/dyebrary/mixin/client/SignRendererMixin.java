package zircon.dyebrary.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.entity.SignText;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.interfaces.ISignText;

@Mixin(SignBlockEntityRenderer.class)
public class SignRendererMixin {
    @ModifyVariable(method = "getColor", at = @At("STORE"), ordinal = 0)
    private static int modCol(int i, SignText sign){
        return ((ISignText)sign).dye_brary$getTextColour();
    }

    @WrapOperation(method = "renderText", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;getSignColor()I"))
    private static int modRenderText(DyeColor instance, Operation<Integer> original, @Local(argsOnly = true) SignText sign){
        return ((ISignText)sign).dye_brary$getTextColour();
    }
}
