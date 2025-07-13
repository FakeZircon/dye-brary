package zircon.dyebrary.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ShulkerEntity.class)
public class ShulkerMixin extends GolemEntity implements Monster{

    protected ShulkerMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"), cancellable = true)
    public void onWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        //test NBT data writing
        nbt.putString("Test", "Yep!");
    }

}
