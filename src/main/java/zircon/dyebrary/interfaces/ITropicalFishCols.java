package zircon.dyebrary.interfaces;

import zircon.dyebrary.ModDyeColour;

public interface ITropicalFishCols {
    ModDyeColour getBaseCol();
    ModDyeColour getPatternCol();

    void setBaseCol(int col);
    void setPatternCol(int col);
}
