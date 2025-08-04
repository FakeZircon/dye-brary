package zircon.dyebrary;

import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignChangingItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zircon.dyebrary.interfaces.IDyeItem;
import zircon.dyebrary.interfaces.ISignText;
import zircon.dyebrary.interfaces.SheepMiddleMan;
import zircon.dyebrary.interfaces.ShulkerMiddleMan;

import java.util.Optional;

public class ModDyeItem extends Item implements SignChangingItem, IDyeItem {
    private final ModDyeColour modColor;

    public ModDyeItem(ModDyeColour colour, Settings settings) {
        super(settings);
        this.modColor = colour;
    }

    @Override
    public ModDyeColour dye_brary$getModColour() {
        return this.modColor;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand){
        //todo figure out config file to make this optional
        if (entity instanceof ShulkerEntity shulkerEntity && shulkerEntity.isAlive() && ((ShulkerMiddleMan)shulkerEntity).dye_brary$getModColour() != this.modColor) {
            shulkerEntity.getWorld().playSoundFromEntity(user, shulkerEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!user.getWorld().isClient) {
                ((ShulkerMiddleMan)shulkerEntity).dye_brary$setModVariant(Optional.ofNullable(this.modColor));
                stack.decrement(1);
            }
            return ActionResult.success(user.getWorld().isClient);
        }

        //sheep section
        else if (entity instanceof SheepEntity sheepEntity && sheepEntity.isAlive() && !sheepEntity.isSheared() && ((SheepMiddleMan)sheepEntity).dye_brary$getModColour() != this.modColor) {
            sheepEntity.getWorld().playSoundFromEntity(user, sheepEntity, SoundEvents.ITEM_DYE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!user.getWorld().isClient) {
                ((SheepMiddleMan)sheepEntity).dye_brary$setModColour(this.modColor);
                stack.decrement(1);
            }

            return ActionResult.success(user.getWorld().isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public boolean useOnSign(World world, SignBlockEntity signBlockEntity, boolean front, PlayerEntity player){
        if (signBlockEntity.changeText(text -> ((ISignText)text).dye_brary$withColour(this.modColor.getSignColor()), front)) {
            world.playSound(null, signBlockEntity.getPos(), SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
        //return false;
    }
}
