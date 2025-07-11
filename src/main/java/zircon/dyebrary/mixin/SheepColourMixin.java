package zircon.dyebrary.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepColourMixin extends AnimalEntity implements Shearable {
	@Shadow
	@Final
	private static TrackedData<Byte> COLOR; //somehow need to have a second tracked byte for part2 of RGB565, would allow skipping the map altogether

	//second byte of RGB565 based colour storage. Will need to extend read/writeCustomDataToNbt
	@Final
	private static TrackedData<Byte> COLOR2 = DataTracker.registerData(SheepColourMixin.class, TrackedDataHandlerRegistry.BYTE);

	private SheepColourMixin() {
		super(EntityType.SHEEP, null);
		throw new AssertionError();
	}

	/**
	 * @reason Allowing >16 unique dye colors (128)
	 * @author ADudeCalledLeo
	 */
	@Overwrite
	public DyeColor getColor() {
		byte b = dataTracker.get(COLOR);
		return DyeColor.byId(b & 0x7F);
	}

	/**
	 * @reason Allowing >16 unique dye colors (128)
	 * @author ADudeCalledLeo
	 */
	@Overwrite
	public void setColor(DyeColor color) {
		byte b = dataTracker.get(COLOR);
		dataTracker.set(COLOR, (byte) ((b & 0x80) | color.getId() % 0x7F));
	}

	/**
	 * @reason Allowing >16 unique dye colors (128)
	 * @author ADudeCalledLeo
	 */
	@Overwrite
	public boolean isSheared() {
		return (dataTracker.get(COLOR) & 0x80) != 0;
	}

	/**
	 * @reason Allowing >16 unique dye colors (128)
	 * @author ADudeCalledLeo
	 */
	@Overwrite
	public void setSheared(boolean sheared) {
		byte b = dataTracker.get(COLOR);
		dataTracker.set(COLOR, (byte) ((b & 0x7F) | (sheared ? 0x80 : 0)));
	}

	//this is the last step before sheep color rendering. This is a test function
	@Inject(method = "getRgbColor", at = @At("HEAD"), cancellable = true)
	private static void onGetRgbColor(DyeColor color, CallbackInfoReturnable<float[]> cir){
		if (color == DyeColor.WHITE){
			cir.setReturnValue(new float[]{0.4737254F, 0.3725901F, 0.3105882F});
		}
	}
}