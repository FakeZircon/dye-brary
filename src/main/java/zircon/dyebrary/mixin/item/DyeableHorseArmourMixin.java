package zircon.dyebrary.mixin.item;

import net.minecraft.item.DyeableHorseArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import zircon.dyebrary.interfaces.ModDyeableItem;

@Mixin(DyeableHorseArmorItem.class)
public class DyeableHorseArmourMixin implements ModDyeableItem {
}
