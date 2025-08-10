package zircon.dyebrary.interfaces;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ModDyeableItem extends DyeableItem {
    static ItemStack modBlendAndSetColor(ItemStack stack, List<IDyeItem> colors) {
        ItemStack itemStack = ItemStack.EMPTY;
        int[] is = new int[3];
        int i = 0;
        int j = 0;
        ModDyeableItem dyeableItem = null;
        Item item = stack.getItem();
        if (item instanceof ModDyeableItem) {
            dyeableItem = (ModDyeableItem)item;
            itemStack = stack.copyWithCount(1);
            if (dyeableItem.hasColor(stack)) {
                int k = dyeableItem.getColor(itemStack);
                float f = (k >> 16 & 0xFF) / 255.0F;
                float g = (k >> 8 & 0xFF) / 255.0F;
                float h = (k & 0xFF) / 255.0F;
                i += (int)(Math.max(f, Math.max(g, h)) * 255.0F);
                is[0] += (int)(f * 255.0F);
                is[1] += (int)(g * 255.0F);
                is[2] += (int)(h * 255.0F);
                j++;
            }

            for (IDyeItem dyeItem : colors) {
                float[] fs = dyeItem.dye_brary$getModColour().getColorComponents();
                int l = (int)(fs[0] * 255.0F);
                int m = (int)(fs[1] * 255.0F);
                int n = (int)(fs[2] * 255.0F);
                i += Math.max(l, Math.max(m, n));
                is[0] += l;
                is[1] += m;
                is[2] += n;
                j++;
            }
        }

        if (dyeableItem == null) {
            return ItemStack.EMPTY;
        } else {
            int k = is[0] / j;
            int o = is[1] / j;
            int p = is[2] / j;
            float h = (float)i / j;
            float q = Math.max(k, Math.max(o, p));
            k = (int)(k * h / q);
            o = (int)(o * h / q);
            p = (int)(p * h / q);
            int var26 = (k << 8) + o;
            var26 = (var26 << 8) + p;
            dyeableItem.setColor(itemStack, var26);
            return itemStack;
        }
    }
}
