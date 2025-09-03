package zircon.dyebrary.mixin.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.ShulkerMiddleMan;

@Mixin(ShulkerEntityRenderer.class)
public abstract class ShulkerRenderMixin extends MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {

    @Shadow
    @Final
    private static Identifier TEXTURE;

    public ShulkerRenderMixin(EntityRendererFactory.Context context, ShulkerEntityModel<ShulkerEntity> entityModel, float f) {
        super(context, entityModel, f);
    }
    
    @Inject(method = "getTexture(Lnet/minecraft/entity/mob/ShulkerEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    public void onGetTexture(ShulkerEntity shulkerEntity, CallbackInfoReturnable<Identifier> cir){
        if (((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour() == null){
            cir.setReturnValue(TEXTURE);
        }
        if (ModDyeColour.ShulkerTextures.containsKey(((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour())){
            cir.setReturnValue(ModDyeColour.ShulkerTextures.get(((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour()));
        }
        else {
            cir.setReturnValue(TEXTURE);
        }

        cir.cancel();
    }
}
