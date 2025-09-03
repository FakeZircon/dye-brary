package zircon.dyebrary.mixin.entities;

import net.minecraft.block.entity.BannerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BannerBlockEntity.class)
public abstract class BannerEntityMixin {
    //add non-vanilla base colour, add a patterns from nbt that gives out a ModDyeColour list
}
