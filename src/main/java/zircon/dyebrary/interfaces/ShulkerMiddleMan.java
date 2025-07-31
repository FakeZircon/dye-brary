package zircon.dyebrary.interfaces;

import org.jetbrains.annotations.Nullable;
import zircon.dyebrary.ModDyeColour;

import java.util.Optional;

public interface ShulkerMiddleMan {
    @Nullable ModDyeColour dye_brary$getModColour();
    void dye_brary$setModVariant(Optional<ModDyeColour> optional);
}

