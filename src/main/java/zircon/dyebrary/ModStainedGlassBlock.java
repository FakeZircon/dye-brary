package zircon.dyebrary;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import zircon.dyebrary.interfaces.IStainable;

public class ModStainedGlassBlock extends AbstractGlassBlock implements IStainable {
    private final ModDyeColour modColour;

    public ModStainedGlassBlock(ModDyeColour color, AbstractBlock.Settings settings) {
        super(settings);
        this.modColour = color;
    }

    @Override
    public ModDyeColour getModColour() {
        return this.modColour;
    }
}
