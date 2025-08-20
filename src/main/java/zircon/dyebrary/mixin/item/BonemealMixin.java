package zircon.dyebrary.mixin.item;

import net.minecraft.item.BoneMealItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BoneMealItem.class)
public class BonemealMixin {
    //todo make bonemeal food easteregg for users with the word "bones" in their name
    //FoodComponents BONEMEAL = new FoodComponent().Builder().hunger;
}
