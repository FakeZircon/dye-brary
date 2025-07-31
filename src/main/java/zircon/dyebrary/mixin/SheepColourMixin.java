package zircon.dyebrary.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.SheepMiddleMan;

@Mixin(SheepEntity.class)
public abstract class SheepColourMixin extends AnimalEntity implements Shearable, SheepMiddleMan {
	@Unique
	private static final TrackedData<Integer> COLOUR = DataTracker.registerData(SheepColourMixin.class, TrackedDataHandlerRegistry.INTEGER);

	@Inject(method = "initDataTracker", at = @At("RETURN") )
	public void onInitDataTracker(CallbackInfo ci){
		this.dataTracker.startTracking(COLOUR, 16383998);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
	public void onWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
		nbt.putInt("Colour", this.dataTracker.get(COLOUR));
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
	public void onReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
		if (nbt.contains("Colour", NbtElement.NUMBER_TYPE)) {
			this.dataTracker.set(COLOUR, nbt.getInt("Colour"));
		}
	}

	private SheepColourMixin() {
		super(EntityType.SHEEP, null);
		throw new AssertionError();
	}

	@Override
	public ModDyeColour dye_brary$getModColour(){
		ModDyeColour col = ModDyeColour.getByHex(dataTracker.get(COLOUR));
		return col != null ? col : ModDyeColour.getByHex(16383998);
	}

	@Override
	public void dye_brary$setModColour(ModDyeColour modColor){
		this.dataTracker.set(COLOUR, modColor.getColor());
	}

	//this is the last step before sheep color rendering. This is a test function
	@Inject(method = "getRgbColor", at = @At("HEAD"), cancellable = true)
	private static void onGetRgbColor(DyeColor color, CallbackInfoReturnable<float[]> cir){
		if (color == DyeColor.WHITE){
			cir.setReturnValue(new float[]{0.4737254F, 0.3725901F, 0.3105882F});
		}
	}

	//todo fix all this cause it's old style. ShulkerMixin stuff has been moved over above ^
//	/**
//	 * @reason Allowing >16 unique dye colors (128)
//	 * @author ADudeCalledLeo
//	 */
//	@Overwrite
//	public DyeColor getColor() {
//		byte b = dataTracker.get(COLOR);
//		return DyeColor.byId(b & 0x7F);
//	}
//
//	/**
//	 * @reason Allowing >16 unique dye colors (128)
//	 * @author ADudeCalledLeo
//	 */
//	@Overwrite
//	public void setColor(DyeColor color) {
//		byte b = dataTracker.get(COLOR);
//		dataTracker.set(COLOR, (byte) ((b & 0x80) | color.getId() % 0x7F));
//	}
//
//	/**
//	 * @reason Allowing >16 unique dye colors (128)
//	 * @author ADudeCalledLeo
//	 */
//	@Overwrite
//	public boolean isSheared() {
//		return (dataTracker.get(COLOR) & 0x80) != 0;
//	}
//
//	/**
//	 * @reason Allowing >16 unique dye colors (128)
//	 * @author ADudeCalledLeo
//	 */
//	@Overwrite
//	public void setSheared(boolean sheared) {
//		byte b = dataTracker.get(COLOR);
//		dataTracker.set(COLOR, (byte) ((b & 0x7F) | (sheared ? 0x80 : 0)));
//	}
}