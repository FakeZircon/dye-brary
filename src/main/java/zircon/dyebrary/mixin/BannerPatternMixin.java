package zircon.dyebrary.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import zircon.dyebrary.ModDyeColour;

@Mixin(BannerPattern.Patterns.class)
public class BannerPatternMixin {
    //these are used by the game to generate banners as loot. Was gonna say I don't need to modify all the methods, but I should probably still should make them usable. Sigh.
    //convert vanilla version to modded, there can be only 1 renderer (for my sanity)
    @ModifyArg(method = "toNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;putInt(Ljava/lang/String;I)V"))
    public int fixNBT(int value, @Local Pair<RegistryEntry<BannerPattern>, DyeColor> pair){
        return ModDyeColour.getByVanilla(pair.getSecond()).getColor();
    }


}
