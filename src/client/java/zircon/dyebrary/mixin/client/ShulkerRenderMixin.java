package zircon.dyebrary.mixin.client;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import zircon.dyebrary.Dyebrary;

@Mixin(ShulkerEntityRenderer.class)
public class ShulkerRenderMixin extends MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
    private static final Identifier TEXTURE = new Identifier(Dyebrary.MOD_ID, "textures/entity/shulker/shulker_yellow.png");
    private static Identifier[] COLORED_TEXTURES = (Identifier[]) TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES
            .stream()
            .map(spriteId -> new Identifier("textures/" + spriteId.getTextureId().getPath() + ".png"))
            .toArray(Identifier[]::new);

    public ShulkerRenderMixin(EntityRendererFactory.Context context, ShulkerEntityModel<ShulkerEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    public Identifier getTexture(ShulkerEntity entity) {
        return TEXTURE;
    }
}
