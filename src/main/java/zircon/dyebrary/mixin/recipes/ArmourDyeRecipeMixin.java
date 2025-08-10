package zircon.dyebrary.mixin.recipes;

import com.google.common.collect.Lists;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.ArmorDyeRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeItem;
import zircon.dyebrary.interfaces.IDyeItem;

import java.util.List;

@Mixin(ArmorDyeRecipe.class)
public class ArmourDyeRecipeMixin {
    @Unique
    List<IDyeItem> dyeList = Lists.newArrayList();

    @WrapOperation(method = "matches(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/world/World;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 1))
    private Item onDyeItemCheck(ItemStack instance, Operation<Item> original){
        if (instance.getItem() instanceof IDyeItem){
            return DyeItem.byColor(DyeColor.WHITE); //return dummy item to pass check
        }
        return original.call(instance);
    }

    @Inject(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", shift = At.Shift.AFTER))
    private void addCustomDyes(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 1) ItemStack itemStack2){
        if(itemStack2.getItem() instanceof ModDyeItem){
            dyeList.add((IDyeItem) itemStack2.getItem());   //add modded dyes to list
        }
    }

    @Definition(id = "DyeItem", type = DyeItem.class)
    @Definition(id = "item", local = @Local(type = Item.class))
    @Expression("item instanceof DyeItem")
    @ModifyExpressionValue(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
    private boolean dontExitOnDyes(boolean original, @Local Item item){
        return original || item instanceof IDyeItem;
    }

    @Inject(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private void addVanillishDyes(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager, CallbackInfoReturnable<ItemStack> cir, @Local Item item){
        dyeList.add(((IDyeItem) item));     //add vanillish dyes
    }

    @WrapWithCondition(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private boolean noMoreCastCrash(List instance, Object e){
        return e instanceof DyeItem;
    }

    @WrapOperation(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/DyeableItem;blendAndSetColor(Lnet/minecraft/item/ItemStack;Ljava/util/List;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack sendToModBlend(ItemStack stack, List<DyeItem> colors, Operation<ItemStack> original){

        return stack;
    }
}
