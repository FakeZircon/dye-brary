package zircon.dyebrary.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.SheepMiddleMan;

@Mixin(SheepWoolFeatureRenderer.class)
public abstract class SheepRenderMixin extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
    public SheepRenderMixin(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> context) {
        super(context);
    }


    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private float[] modCols(float[] x, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity){
        if (((SheepMiddleMan)sheepEntity).dye_brary$getModColour() == ModDyeColour.getByHex(16383998)){
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        }
        float[] colComp = ((SheepMiddleMan)sheepEntity).dye_brary$getModColour().getColorComponents();
        return new float[]{colComp[0]*0.75f, colComp[1]*0.75f, colComp[2]*0.75f};
    }

//    @ModifyVariable(method = "", at = @At(value = "STORE"), ordinal = 11)
//    private float[] modCols(){
//        return sheepEntity.getColor().getColorComponents();
//    }
//    @Inject(method = "render*", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;getRgbColor(Lnet/minecraft/util/DyeColor;)[F", ordinal = 2))
//    private void modCols(
//            MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity, float f, float g, float h, float j, float k, float l
//    ){
//
////        float[] colComp = ((SheepMiddleMan)sheepEntity).dye_brary$getModColour().getColorComponents();
////        return new float[]{colComp[0]*0.75f, colComp[1]*0.75f, colComp[2]*0.75f};
//    }
}
