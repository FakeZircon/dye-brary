package zircon.dyebrary.mixin.client;

import net.minecraft.block.entity.SignText;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.interfaces.ISignText;

@Mixin(AbstractSignEditScreen.class)
public class SignEditScreenMixin {
    @Shadow private SignText text;

    @ModifyVariable(method = "renderSignText", at = @At("STORE"), ordinal = 0)
    private int modTextCol(int i){
        return ((ISignText)this.text).dye_brary$getTextColour();
    }
}
