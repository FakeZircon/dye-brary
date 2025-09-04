package zircon.dyebrary.interfaces;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import zircon.dyebrary.ModDyeColour;

import java.util.List;

public interface IBannerBlock {
    List<Pair<RegistryEntry<BannerPattern>, ModDyeColour>> dye_brary$getPatterns();
    List<Pair<RegistryEntry<BannerPattern>, ModDyeColour>> dye_brary$getPatternsFromNbt(ModDyeColour baseColor, @Nullable NbtList patternListNbt);
}
