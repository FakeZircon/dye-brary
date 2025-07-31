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
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.ShulkerMiddleMan;

@Mixin(ShulkerEntityRenderer.class)
public class ShulkerRenderMixin extends MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {

    @Shadow
    @Final
    private static Identifier TEXTURE;

    public ShulkerRenderMixin(EntityRendererFactory.Context context, ShulkerEntityModel<ShulkerEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    public Identifier getTexture(ShulkerEntity shulkerEntity) {
        if (((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour() == null){
            return TEXTURE;
        }
        if (ModDyeColour.ShulkerTextures.containsKey(((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour())){
            return ModDyeColour.ShulkerTextures.get(((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour());
        }
        else {
            return TEXTURE;
        }
    }
}
