package zircon.dyebrary.mixin.item;

import net.minecraft.item.FireworkStarItem;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeColour;

@Mixin(FireworkStarItem.class)
public class FireworkStarTooltipMixin {
    @Inject(method = "getColorText", at = @At("HEAD"), cancellable = true)
    private static void onGetColorText(int color, CallbackInfoReturnable<Text> cir) {
        ModDyeColour dyeColor = ModDyeColour.getByFirework(color);
        cir.setReturnValue(dyeColor == null
                ? Text.translatable("item.minecraft.firework_star.custom_color")
                : Text.translatable("item.minecraft.firework_star." + dyeColor.getName()));
        cir.cancel();
    }
}
