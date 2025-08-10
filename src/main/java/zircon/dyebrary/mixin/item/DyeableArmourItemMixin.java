package zircon.dyebrary.mixin.item;

import net.minecraft.item.DyeableArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import zircon.dyebrary.interfaces.ModDyeableItem;

@Mixin(DyeableArmorItem.class)
public class DyeableArmourItemMixin implements ModDyeableItem {
}
