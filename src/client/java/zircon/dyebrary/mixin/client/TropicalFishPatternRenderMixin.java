package zircon.dyebrary.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TropicalFishColorFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TropicalFishEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.interfaces.ITropicalFishCols;

@Mixin(TropicalFishColorFeatureRenderer.class)
public class TropicalFishPatternRenderMixin {
    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/TropicalFishEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private float[] getModPatternCol(float[] fs, @Local(argsOnly = true) TropicalFishEntity tropicalFishEntity){
        return ((ITropicalFishCols)tropicalFishEntity).getPatternColComps();
    }
}
