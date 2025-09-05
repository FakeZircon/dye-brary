package zircon.dyebrary.mixin.entities;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.IBannerBlock;

import java.util.List;

@Mixin(BannerBlockEntity.class)
public abstract class BannerEntityMixin implements IBannerBlock {
    //add non-vanilla base colour, add a patterns from nbt that gives out a ModDyeColour list, look for normal banner declarations and make up a modbanner one
    @Unique
    public ModDyeColour dyebraryBaseCol;

    @Unique
    @Nullable
    public List<Pair<RegistryEntry<BannerPattern>, ModDyeColour>> dyebraryPatterns;

    @Shadow
    @Nullable
    private NbtList patternListNbt;

    @Override
    public List<Pair<RegistryEntry<BannerPattern>, ModDyeColour>> dye_brary$getPatterns(){
        if (this.dyebraryPatterns == null) {
            this.dyebraryPatterns = dye_brary$getPatternsFromNbt(this.dyebraryBaseCol, this.patternListNbt);
        }

        return this.dyebraryPatterns;
    }

    @Override
    public List<Pair<RegistryEntry<BannerPattern>, ModDyeColour>> dye_brary$getPatternsFromNbt(ModDyeColour baseColor, @Nullable NbtList patternListNbt) {
        List<Pair<RegistryEntry<BannerPattern>, ModDyeColour>> list = Lists.<Pair<RegistryEntry<BannerPattern>, ModDyeColour>>newArrayList();
        list.add(Pair.of(Registries.BANNER_PATTERN.entryOf(BannerPatterns.BASE), baseColor));
        if (patternListNbt != null) {
            for (int i = 0; i < patternListNbt.size(); i++) {
                NbtCompound nbtCompound = patternListNbt.getCompound(i);
                RegistryEntry<BannerPattern> registryEntry = BannerPattern.byId(nbtCompound.getString("Pattern"));
                if (registryEntry != null) {
                    int j = nbtCompound.getInt("Color");
                    ModDyeColour patCol;
                    if (j <= 15) {  //correct for vanilla generated banners. Doesn't work currently
                        patCol = ModDyeColour.getByVanilla(DyeColor.byId(j));
                    }
                    else {
                        patCol = ModDyeColour.getByHex(j);
                    }
                    list.add(Pair.of(registryEntry, patCol));
                }
            }
        }

        return list;
    }
}
