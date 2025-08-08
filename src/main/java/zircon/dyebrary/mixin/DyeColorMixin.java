package zircon.dyebrary.mixin;

import net.minecraft.block.MapColor;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zircon.dyebrary.ModDyeColour;

@Mixin(DyeColor.class)
public class DyeColorMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(String string, int i, int id, String name, int color, MapColor mapColor, int fireworkColor, int signColor, CallbackInfo ci){
        //init vanillish ModDyes here so they are registered in time for block and item creation
        new ModDyeColour(string, color, mapColor, fireworkColor, signColor);
    }
}
