package zircon.dyebrary.mixin.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.ITropicalFishCols;

@Mixin(TropicalFishEntity.class)
public abstract class TropicalFishMixin extends SchoolingFishEntity implements ITropicalFishCols {
    @Unique
    private static final TrackedData<Integer> BASE_COL = DataTracker.registerData(TropicalFishMixin.class, TrackedDataHandlerRegistry.INTEGER);

    @Unique
    private static final TrackedData<Integer> PATT_COL = DataTracker.registerData(TropicalFishMixin.class, TrackedDataHandlerRegistry.INTEGER);

    public TropicalFishMixin(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN") )
    public void onInitDataTracker(CallbackInfo ci){
        this.dataTracker.startTracking(BASE_COL, 16383998);
        this.dataTracker.startTracking(PATT_COL, 16383998);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void onWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putInt("Base", this.dataTracker.get(BASE_COL));
        nbt.putInt("Pattern", this.dataTracker.get(PATT_COL));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void onReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("Base", NbtElement.NUMBER_TYPE)) {
            this.dataTracker.set(BASE_COL, nbt.getInt("Base"));
        }
        if (nbt.contains("Pattern", NbtElement.NUMBER_TYPE)) {
            this.dataTracker.set(PATT_COL, nbt.getInt("Pattern"));
        }
    }

    @Override
    public float[] getBaseColComps(){
        ModDyeColour col = ModDyeColour.getByHex(this.dataTracker.get(BASE_COL));
        return col != null ? col.getColorComponents() : ModDyeColour.getByHex(16383998).getColorComponents();
    }

    @Override
    public float[] getPatternColComps(){
        ModDyeColour col = ModDyeColour.getByHex(this.dataTracker.get(PATT_COL));
        return col != null ? col.getColorComponents() : ModDyeColour.getByHex(16383998).getColorComponents();
    }
}
