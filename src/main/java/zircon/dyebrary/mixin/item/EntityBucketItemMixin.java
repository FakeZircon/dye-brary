package zircon.dyebrary.mixin.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zircon.dyebrary.ModDyeColour;

@Mixin(EntityBucketItem.class)
public class EntityBucketItemMixin {
    //tooltip stuff, common variants shouldn't be affected
    //TODO make this take namespaces into account once actual usability of this library starts
    @ModifyVariable(method = "appendTooltip", at = @At("STORE"), ordinal = 0)
    private String getBaseName(String name, @Local NbtCompound nbtCompound){
        return "color.minecraft." + ModDyeColour.getByHex(nbtCompound.getInt("Base")).getName();
    }
    @ModifyVariable(method = "appendTooltip", at = @At("STORE"), ordinal = 1)
    private String getPatternName(String name, @Local NbtCompound nbtCompound){
        return "color.minecraft." + ModDyeColour.getByHex(nbtCompound.getInt("Pattern")).getName();
    }
}
