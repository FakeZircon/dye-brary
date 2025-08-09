package zircon.dyebrary.mixin.entities;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zircon.dyebrary.Dyebrary;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.IDyeItem;
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

	@Unique
    private static ModDyeColour generateDefaultModColour(Random random) {
		int i = random.nextInt(100);
		if (i < 5) {
			return ModDyeColour.getByHex(16383998);
		} else if (i < 10) {
			return ModDyeColour.getByHex(4673362);
		} else if (i < 15) {
			return ModDyeColour.getByHex(10329495);
		} else if (i < 18) {
			return ModDyeColour.getByHex(8606770);
		} else {
			return random.nextInt(500) == 0 ? ModDyeColour.getByHex(15961002) : ModDyeColour.getByHex(16383998);
		}
	}

	@Inject(method = "initialize", at = @At("RETURN"))
	private void onInitialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir){
		this.dye_brary$setModColour(generateDefaultModColour(world.getRandom()));
	}

	@Inject(method = "createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/SheepEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;setColor(Lnet/minecraft/util/DyeColor;)V"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void onCreateChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<SheepEntity> cir, SheepEntity sheepEntity){
		((SheepMiddleMan)sheepEntity).dye_brary$setModColour(this.getModChildColour(this, (SheepEntity) passiveEntity));
		cir.setReturnValue(sheepEntity);
		cir.cancel();
	}

	@Unique
	private ModDyeColour getModChildColour(AnimalEntity firstParent, AnimalEntity secondParent){
		ModDyeColour col1 = ((SheepMiddleMan)firstParent).dye_brary$getModColour();
		ModDyeColour col2 = ((SheepMiddleMan)secondParent).dye_brary$getModColour();
		RecipeInputInventory recipeInputInventory = createDyeMixer(col1, col2);
		return (ModDyeColour) this.getWorld()
				.getRecipeManager()
				.getFirstMatch(RecipeType.CRAFTING, recipeInputInventory, this.getWorld())
				.map(recipe -> recipe.craft(recipeInputInventory, this.getWorld().getRegistryManager()))
				.map(ItemStack::getItem)
				.filter(IDyeItem.class::isInstance)
				.map(IDyeItem.class::cast)
				.map(IDyeItem::dye_brary$getModColour)
				.orElseGet(() -> this.getWorld().random.nextBoolean() ? col1 : col2);
	}

	@Unique
	private RecipeInputInventory createDyeMixer(ModDyeColour firstDye, ModDyeColour secondDye){
		RecipeInputInventory recipeInputInventory = new CraftingInventory(new ScreenHandler(null, -1) {
			@Override
			public ItemStack quickMove(PlayerEntity player, int slot) {
				return ItemStack.EMPTY;
			}

			@Override
			public boolean canUse(PlayerEntity player) {
				return false;
			}
		}, 2, 1);
		recipeInputInventory.setStack(0, new ItemStack(ModDyeColour.getItemByDye(firstDye)));
		recipeInputInventory.setStack(1, new ItemStack(ModDyeColour.getItemByDye(secondDye)));
		return recipeInputInventory;
	}
}