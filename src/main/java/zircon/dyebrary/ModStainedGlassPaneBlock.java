package zircon.dyebrary;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import zircon.dyebrary.interfaces.IStainable;

public class ModStainedGlassPaneBlock extends PaneBlock implements IStainable, Stainable {
    private final ModDyeColour modColour;

    public ModStainedGlassPaneBlock(ModDyeColour color, AbstractBlock.Settings settings) {
        super(settings);
        this.modColour = color;
        this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
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
