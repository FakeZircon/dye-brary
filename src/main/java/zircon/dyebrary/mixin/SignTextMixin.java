package zircon.dyebrary.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.entity.SignText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zircon.dyebrary.mixin.accessors.SignAccessor;
import zircon.dyebrary.interfaces.ISignText;

import java.util.Optional;

@Mixin(SignText.class)
public abstract class SignTextMixin implements ISignText, SignAccessor {
    @Final
    @Shadow
    private static Codec<Text[]> MESSAGES_CODEC;

    @Shadow
    public static Codec<SignText> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            MESSAGES_CODEC.fieldOf("messages").forGetter(signText -> signText.getMessages(false)),
                            MESSAGES_CODEC.optionalFieldOf("filtered_messages").forGetter(signText -> Optional.ofNullable(signText.getMessages(true))),
                            Codec.INT.fieldOf("colour").orElse(0).forGetter(signText -> ((ISignText)signText).dye_brary$getTextColour()),
                            Codec.BOOL.fieldOf("has_glowing_text").orElse(false).forGetter(SignText::isGlowing)
                    )
                    .apply(instance, SignTextMixin::modCreate)
    );

    @Shadow @Final private Text[] messages;

    @Shadow @Final private Text[] filteredMessages;

    @Shadow @Final private boolean glowing;

    @Unique
    private static SignText modCreate(Text[] messages, Optional<Text[]> filteredMessages, int color, boolean glowing){
        Text[] texts = (Text[])filteredMessages.orElseGet(SignAccessor::invokeGetDefaultText);
        SignAccessor.invokeCopyMessages(messages, texts);

        SignText newText = new SignText(messages, texts, DyeColor.BLACK, glowing);
        ((ISignText)newText).dye_brary$setTextColour(color);

        return newText;
    }

    @Unique
    private static SignText fakeInit(Text[] messages, Text[] filteredMessages, int signColor, boolean glowing){
        SignText newText = new SignText(messages, filteredMessages, DyeColor.BLACK, glowing);
        ((ISignText)newText).dye_brary$setTextColour(signColor);
        return newText;
    }

    @Unique
    @Final
    @Mutable
    private int colour;

    @Override
    public void dye_brary$setTextColour(int signColour){
        this.colour = signColour;
    }

    @Override
    public int dye_brary$getTextColour(){
        return this.colour;
    }

    @Override
    public SignText dye_brary$withColour(int signColour){
        return signColour == this.dye_brary$getTextColour() ? (SignText) (Object) this
                : fakeInit(this.messages, this.filteredMessages, signColour, this.glowing);
    }

    @Inject(method = "withMessage(ILnet/minecraft/text/Text;Lnet/minecraft/text/Text;)Lnet/minecraft/block/entity/SignText;", at = @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onWithMessage(int line, Text message, Text filteredMessage, CallbackInfoReturnable<SignText> cir, Text[] texts, Text[] texts2){
        cir.setReturnValue(fakeInit(texts, texts2, this.colour, this.glowing));
        cir.cancel();
    }

    @Inject(method = "withGlowing", at = @At("RETURN"), cancellable = true)
    private void onWithGlowing(boolean glowing, CallbackInfoReturnable<SignText> cir){
        cir.setReturnValue(glowing == this.glowing ? (SignText) (Object) this
                : fakeInit(this.messages, this.filteredMessages, this.colour, glowing));
        cir.cancel();
    }
}
