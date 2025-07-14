package zircon.dyebrary.interfaces;

import org.jetbrains.annotations.Nullable;
import zircon.dyebrary.ModDyeColour;

import java.util.Optional;

public interface ShulkerMiddleMan {
    @Nullable
    public ModDyeColour getModColour();
    public void setModVariant(Optional<ModDyeColour> optional);
}

