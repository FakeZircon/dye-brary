package zircon.dyebrary.mixin.entities;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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

    // add nbt colour data to bucket item
    @Inject(method = "copyDataToStack", at = @At("RETURN"))
    public void onCopyDataToStack(ItemStack stack, CallbackInfo ci, @Local NbtCompound nbtCompound){
        nbtCompound.putInt("Base", this.dataTracker.get(BASE_COL));
        nbtCompound.putInt("Pattern", this.dataTracker.get(PATT_COL));
    }

    //grab colour data from bucketed fish
    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtCompound;getInt(Ljava/lang/String;)I"))
    private void getColData(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir){
        this.setBaseCol(entityNbt.getInt("Base"));
        this.setPatternCol(entityNbt.getInt("Pattern"));
    }

    //New fish spawning
    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DyeColor;values()[Lnet/minecraft/util/DyeColor;"))
    private void randomFishCols(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir, @Local Random random){
        ModDyeColour col1 = (ModDyeColour) Util.getRandom(ModDyeColour.DyeList.values().toArray(), random);
        ModDyeColour col2 = (ModDyeColour) Util.getRandom(ModDyeColour.DyeList.values().toArray(), random);

        this.setBaseCol(col1.getColor());
        this.setPatternCol(col2.getColor());
    }


    @Override
    public ModDyeColour getBaseCol(){
        ModDyeColour col = ModDyeColour.getByHex(this.dataTracker.get(BASE_COL));
        return col != null ? col : ModDyeColour.getByHex(16383998);
    }

    @Override
    public ModDyeColour getPatternCol(){
        ModDyeColour col = ModDyeColour.getByHex(this.dataTracker.get(PATT_COL));
        return col != null ? col : ModDyeColour.getByHex(16383998);
    }

    @Override
    public void setBaseCol(int col){
        this.dataTracker.set(BASE_COL, col);
    }
    @Override
    public void setPatternCol(int col){
        this.dataTracker.set(PATT_COL, col);
    }
}
