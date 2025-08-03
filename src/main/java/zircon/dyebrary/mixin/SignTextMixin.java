package zircon.dyebrary.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.block.entity.SignText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.Dyebrary;
import zircon.dyebrary.interfaces.ISignText;

import java.util.Optional;

@Mixin(SignText.class)
public abstract class SignTextMixin implements ISignText, SignAccessor {
    @Final
    @Shadow
    private static Codec<Text[]> MESSAGES_CODEC;

    /// this is called twice when changing sign colour by commands, not sure why the second call does not update the colour properly when the first call does
    /// additionally, hand dyeing a sign calls create as well, but I do not know how or if it called retroactively from the blockdata being modified
//    @Shadow
//    public static Codec<SignText> CODEC = RecordCodecBuilder.create(
//            instance -> instance.group(
//                            MESSAGES_CODEC.fieldOf("messages").forGetter(signText -> signText.getMessages(false)),
//                            MESSAGES_CODEC.optionalFieldOf("filtered_messages").forGetter(signText -> Optional.ofNullable(signText.getMessages(true))),
//                            Codec.INT.fieldOf("colour").orElse(0).forGetter(signText -> ((ISignText)signText).dye_brary$getTextColour()),
//                            Codec.BOOL.fieldOf("has_glowing_text").orElse(false).forGetter(SignText::isGlowing)
//                    )
//                    .apply(instance, SignTextMixin::modCreate)
//    );

    @Shadow @Final private Text[] messages;

    @Shadow @Final private Text[] filteredMessages;

    @Shadow @Final private boolean glowing;

    @Unique
    private static SignText modCreate(Text[] messages, Optional<Text[]> filteredMessages, int color, boolean glowing){
        Text[] texts = (Text[])filteredMessages.orElseGet(SignAccessor::invokeGetDefaultText);
        SignAccessor.invokeCopyMessages(messages, texts);

        SignText newText = new SignText(messages, texts, DyeColor.BLACK, glowing);
        //((ISignText)newText).dye_brary$setTextColour(color);

        Dyebrary.LOGGER.info(String.format("%d", color));
        return newText;
    }

    @Inject(method = "create", at = @At("RETURN"))
    private static void onCreate(Text[] messages, Optional<Text[]> filteredMessages, DyeColor color, boolean glowing, CallbackInfoReturnable<SignText> cir){
        Dyebrary.LOGGER.info(String.format("%s", color.asString()));
    }

    @Unique
    private int colour;

    @Override
    public void dye_brary$setTextColour(int signColour){
        this.colour = signColour;
    }

    @Override
    public int dye_brary$getTextColour(){
        return this.colour;
    }

//    @Override
//    public SignText dye_brary$withColour(int signColour){
//        return signColour == this.dye_brary$getTextColour() ? modCreate(this.messages, Optional.ofNullable(this.filteredMessages), this.colour, this.glowing)
//                : modCreate(this.messages, Optional.ofNullable(this.filteredMessages), signColour, this.glowing);
//    }
}
