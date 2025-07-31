package zircon.dyebrary.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zircon.dyebrary.ModDyeColour;
import zircon.dyebrary.interfaces.ShulkerMiddleMan;

import java.util.Optional;

@Mixin(ShulkerEntity.class)
public class ShulkerMixin extends GolemEntity implements Monster, ShulkerMiddleMan {
    @Unique
    private static final TrackedData<Integer> COLOUR = DataTracker.registerData(ShulkerMixin.class, TrackedDataHandlerRegistry.INTEGER);

    protected ShulkerMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN") )
    public void onInitDataTracker(CallbackInfo ci){
        this.dataTracker.startTracking(COLOUR, 0);
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

    @Inject(method = "spawnNewShulker", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;"), cancellable = true)
    public void onSpawnNewShulker(CallbackInfo ci){
        ShulkerEntity shulkerEntity = EntityType.SHULKER.create(this.getWorld());
        if (shulkerEntity != null) {
            ((ShulkerMiddleMan)shulkerEntity).dye_brary$setModVariant(this.getVariant());
            shulkerEntity.refreshPositionAfterTeleport(this.getPos());
            this.getWorld().spawnEntity(shulkerEntity);
        }
        ci.cancel();
    }

    public void dye_brary$setModVariant(Optional<ModDyeColour> optional) {
        this.dataTracker.set(COLOUR, optional.map(ModDyeColour::getColor).orElse(0));
    }

    /**
     * @author FakeZircon
     * @reason switch return type to modded dye colour class
     */
    @Overwrite
    public Optional<ModDyeColour> getVariant() {
        return Optional.ofNullable(this.dye_brary$getModColour());
    }

    @Override
    public ModDyeColour dye_brary$getModColour() {
        return ModDyeColour.DyeList.getOrDefault(this.dataTracker.get(COLOUR), null);
    }
}
