package zircon.dyebrary.mixin;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignChangingItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.IDyeItem;
import zircon.dyebrary.interfaces.SheepMiddleMan;
import zircon.dyebrary.interfaces.ShulkerMiddleMan;

import java.util.Optional;

@Mixin(DyeItem.class)
public abstract class DyeItemMixin extends Item implements SignChangingItem, IDyeItem {
    @Final
    @Shadow
    private final DyeColor color;

    @Unique
    private ModDyeColour modColor;

    public DyeItemMixin(DyeColor color, Settings settings) {
        super(settings);
        throw new AssertionError();
    }

    @Override
    public ModDyeColour dye_brary$getModColour() {
        return this.modColor;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(DyeColor color, Settings settings, CallbackInfo ci){
        //init vanillish ModDyes here so they are registered in time for item creation
        this.modColor = new ModDyeColour(color.getName(), color.getColorComponents(), color.getMapColor(), color.getFireworkColor(), color.getSignColor());
    }

    @Shadow public abstract boolean useOnSign(World world, SignBlockEntity signBlockEntity, boolean front, PlayerEntity player);

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    public void onUseOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        //todo figure out config file to make this optional
        if (entity instanceof ShulkerEntity shulkerEntity && shulkerEntity.isAlive() && ((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour() != this.modColor) {
            shulkerEntity.getWorld().playSoundFromEntity(user, shulkerEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!user.getWorld().isClient) {
                ((ShulkerMiddleMan)shulkerEntity).dye_brary$setModVariant(Optional.ofNullable(this.modColor));
                stack.decrement(1);
            }
            cir.setReturnValue(ActionResult.success(user.getWorld().isClient));
            cir.cancel();
        }

        //sheep section
        else if (entity instanceof SheepEntity sheepEntity && sheepEntity.isAlive() && !sheepEntity.isSheared() && ((SheepMiddleMan)sheepEntity).dye_brary$getModColour() != this.modColor) {
            sheepEntity.getWorld().playSoundFromEntity(user, sheepEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!user.getWorld().isClient) {
                ((SheepMiddleMan)sheepEntity).dye_brary$setModColour(this.modColor);
                stack.decrement(1);
            }

            cir.setReturnValue(ActionResult.success(user.getWorld().isClient));
            cir.cancel();
        } else {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
