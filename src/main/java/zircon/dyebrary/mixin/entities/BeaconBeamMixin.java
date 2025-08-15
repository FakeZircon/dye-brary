package zircon.dyebrary.mixin.entities;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zircon.dyebrary.interfaces.IStainable;

@Mixin(BeaconBlockEntity.class)
public class BeaconBeamMixin {
    @Unique
    private static BlockPos currBlockPos;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onTick(World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity, CallbackInfo ci, int i, int j, int k, BlockPos blockPos, BeaconBlockEntity.BeamSegment beamSegment){
        currBlockPos = blockPos;
    }

    @ModifyVariable(method = "tick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/DyeColor;getColorComponents()[F"))
    private static float[] modColComp(float[] fs, World world, BlockPos pos, BlockState state, BeaconBlockEntity blockEntity){
        //weird crash I can't reproduce but might as well guard against
        if(world.getBlockState(currBlockPos).getBlock() instanceof AirBlock) {return new float[]{0, 0, 0};}

        return ((IStainable)world.getBlockState(currBlockPos).getBlock()).getModColour().getColorComponents();
    }
}
