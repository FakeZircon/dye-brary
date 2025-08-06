package zircon.dyebrary.mixin.accessors;

import net.minecraft.block.entity.SignText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SignText.class)
public interface SignAccessor {
    @Invoker("getDefaultText")
    static Text[] invokeGetDefaultText(){
        throw new AssertionError();
    }

    @Invoker("copyMessages")
    static void invokeCopyMessages(Text[] from, Text[] to){
        throw new AssertionError();
    }
}
