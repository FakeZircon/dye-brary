package zircon.dyebrary.mixin.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(Item.class)
public abstract class BonemealEasterEggMixin {
    @Unique
    Pattern pattern = Pattern.compile("bone", Pattern.CASE_INSENSITIVE);

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setCurrentHand(Lnet/minecraft/util/Hand;)V"), cancellable = true)
    private void checkIfBones(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack itemStack){
        Matcher matcher = pattern.matcher(user.getName().getString());
        boolean nameFound = matcher.find();
        if (!nameFound){
            cir.setReturnValue(TypedActionResult.fail(itemStack));
            cir.cancel();
        }
    }
}
