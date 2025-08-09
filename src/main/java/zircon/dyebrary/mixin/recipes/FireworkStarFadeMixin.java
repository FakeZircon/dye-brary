package zircon.dyebrary.mixin.recipes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.FireworkStarFadeRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeItem;
import zircon.dyebrary.interfaces.IDyeItem;

import java.util.List;

@Mixin(FireworkStarFadeRecipe.class)
public class FireworkStarFadeMixin {
    @WrapOperation(method = "matches(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/world/World;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private Item onDyeItemCheck(ItemStack instance, Operation<Item> original){
        if (instance.getItem() instanceof IDyeItem){
            return DyeItem.byColor(DyeColor.WHITE); //return dummy item to pass check
        }
        return original.call(instance);
    }

    @Inject(
            method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift = At.Shift.AFTER))
    private void addCustomColour(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 1) ItemStack itemStack2, @Local List<Integer> list){
        if(itemStack2.getItem() instanceof ModDyeItem){ //check only for modded dyes so vanilla colours aren't added twice
            list.add(((IDyeItem)itemStack2.getItem()).dye_brary$getModColour().getFireworkColor());
        }
    }
}
