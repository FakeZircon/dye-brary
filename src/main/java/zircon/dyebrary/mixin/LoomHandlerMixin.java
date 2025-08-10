package zircon.dyebrary.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import zircon.dyebrary.interfaces.IDyeItem;

@Mixin(LoomScreenHandler.class)
public abstract class LoomHandlerMixin extends ScreenHandler {
    @Final
    @Shadow
    private Inventory input;

    @Final
    @Shadow
    Slot dyeSlot;

    protected LoomHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    //slot definition
    @ModifyArgs(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/LoomScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", ordinal = 1))
    private void allowAllDyes(Args args){
        Slot oldDef = args.get(0);
        args.set(0, new Slot(this.input, oldDef.getIndex(), oldDef.x, oldDef.y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof IDyeItem;
            }
        });
    }

    //quickmove
    @Inject(method = "quickMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0), cancellable = true)
    private void moveIDyes(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 1) ItemStack itemStack2){
        if (itemStack2.getItem() instanceof IDyeItem) {
            if (!this.insertItem(itemStack2, this.dyeSlot.id, this.dyeSlot.id + 1, false)) {
                cir.setReturnValue(ItemStack.EMPTY);
                cir.cancel();
            }
        }
    }
}
