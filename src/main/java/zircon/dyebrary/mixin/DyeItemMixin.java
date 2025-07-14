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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.ShulkerMiddleMan;

import java.util.Optional;

@Mixin(DyeItem.class)
public abstract class DyeItemMixin extends Item implements SignChangingItem {
    @Final
    @Shadow
    private final DyeColor color;

    //private final ModDyeColour modColour;

    public DyeItemMixin(DyeColor color, Item.Settings settings) {
        super(settings);
        throw new AssertionError();
    }

//    public DyeItemMixin(ModDyeColour colour, Item.Settings settings){
//        super(settings);
//        this.modColour = colour;
//    }

    @Shadow public abstract boolean useOnSign(World world, SignBlockEntity signBlockEntity, boolean front, PlayerEntity player);

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    public void onUseOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        //todo figure out config file to make this optional
        // sooner todo switch fully over to using moddyecolour but for now we have this
        float[] colComps = this.color.getColorComponents();
        int colHex = ((int)(colComps[0]*255) << 16) + ((int)(colComps[1]*255) << 8) + (int)(colComps[2]*255);

        if (entity instanceof ShulkerEntity shulkerEntity && shulkerEntity.isAlive() && ((ShulkerMiddleMan)shulkerEntity).getModColour() != ModDyeColour.getByHex(colHex)) {
            shulkerEntity.getWorld().playSoundFromEntity(user, shulkerEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!user.getWorld().isClient) {
                ((ShulkerMiddleMan)shulkerEntity).setModVariant(Optional.ofNullable(ModDyeColour.getByHex(colHex)));
                stack.decrement(1);
            }
            cir.setReturnValue(ActionResult.success(user.getWorld().isClient));
        }
    }
}
