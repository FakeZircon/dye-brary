package zircon.dyebrary.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.TropicalFishEntityRenderer;
import net.minecraft.entity.passive.TropicalFishEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.interfaces.ITropicalFishCols;

@Mixin(TropicalFishEntityRenderer.class)
public class TropicalFishBaseRenderMixin {
    @ModifyVariable(method = "render(Lnet/minecraft/entity/passive/TropicalFishEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("STORE"), ordinal = 0)
    private float[] getModBaseCol(float[] fs, @Local(argsOnly = true) TropicalFishEntity tropicalFishEntity){
        return ((ITropicalFishCols)tropicalFishEntity).getBaseCol().getColorComponents();
    }
}
