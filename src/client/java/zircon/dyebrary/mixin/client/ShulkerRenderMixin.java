package zircon.dyebrary.mixin.client;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zircon.dyebrary.Dyebrary;
import zircon.dyebrary.ModDyeColour;

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
        if (shulkerEntity.getColor() == null){
            return TEXTURE;
        }
        if (ModDyeColour.ShulkerTextures.containsKey(shulkerEntity.getColor().getName())){
            return ModDyeColour.ShulkerTextures.get(shulkerEntity.getColor().getName());
        }
        else {
            return TEXTURE;
        }
    }
}
