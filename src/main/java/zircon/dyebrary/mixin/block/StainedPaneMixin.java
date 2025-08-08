package zircon.dyebrary.mixin.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.IStainable;

@Mixin(StainedGlassPaneBlock.class)
public class StainedPaneMixin implements IStainable {
    @Unique
    private ModDyeColour modColour;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(DyeColor color, AbstractBlock.Settings settings, CallbackInfo ci){
        this.modColour = ModDyeColour.getByComp(color.getColorComponents());
    }

    @Override
    public ModDyeColour getModColour() {
        return this.modColour;
    }
}
