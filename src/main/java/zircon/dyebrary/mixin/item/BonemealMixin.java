package zircon.dyebrary.mixin.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class BonemealMixin {
    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=bone_meal")
            ),
            at = @At(value = "INVOKE", target = "net/minecraft/item/BoneMealItem.<init>(Lnet/minecraft/item/Item$Settings;)V", ordinal = 0)
    )
    private static Item.Settings addFoodComps(Item.Settings settings){
        return settings.food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build());
    }
}
