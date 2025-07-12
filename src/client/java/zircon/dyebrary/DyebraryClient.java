package zircon.dyebrary;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class DyebraryClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//EntityRendererRegistry.register(EntityType.SHULKER, ShulkerRenderer::new);
	}
}