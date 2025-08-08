package zircon.dyebrary;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import zircon.dyebrary.interfaces.IStainable;

public class ModStainedGlassBlock extends AbstractGlassBlock implements IStainable, Stainable {
    private final ModDyeColour modColour;

    public ModStainedGlassBlock(ModDyeColour color, AbstractBlock.Settings settings) {
        super(settings);
        this.modColour = color;
    }

    @Override
    public ModDyeColour getModColour() {
        return this.modColour;
    }

    //This should never actually be used but will allow the modded blocks to count as "stainable" in beacon beam calcs
    @Override
    public DyeColor getColor() {
        return DyeColor.BLACK;
    }
}
