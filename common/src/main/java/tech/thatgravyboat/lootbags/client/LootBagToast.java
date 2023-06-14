package tech.thatgravyboat.lootbags.client;

import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import com.teamresourceful.resourcefullib.common.color.ConstantColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.common.recipe.Loot;

import java.util.List;

public record LootBagToast(Loot loot, List<ItemStack> rewards) implements Toast {

    @NotNull
    public Visibility render(@NotNull GuiGraphics graphics, ToastComponent toastComponent, long l) {
        ItemStack itemStack = rewards.get((int) (Math.min(rewards.size() - 1, l / 1000)));
        graphics.blit(TEXTURE, 0, 0, 0, 0, this.width(), this.height());
        Font font = toastComponent.getMinecraft().font;
        graphics.drawString(font, loot.name(), 30, 7, ConstantColors.lightgray.getValue(), false);
        graphics.drawString(font, itemStack.getHoverName(), 30, 18, ConstantColors.lightgray.getValue(), false);
        graphics.renderItem(itemStack, 8, 8);
        if (itemStack.getCount() > 1) {
            String string2 = String.valueOf(itemStack.getCount());
            try (var pose = new CloseablePoseStack(graphics)) {
                pose.translate(0.0D, 0.0D, 200);
                graphics.drawString(font, string2, 9 + 19 - 2 - font.width(string2), 9 + 19 - font.lineHeight, 0xFF555555, false);
                graphics.drawString(font, string2, 8 + 19 - 2 - font.width(string2), 8 + 19 - font.lineHeight, ConstantColors.white.getValue(), false);
            }
        }
        return l >= (rewards.size() > 2 ? Math.min(10000L, rewards.size() * 1000L) : 2000L) ? Visibility.HIDE : Visibility.SHOW;
    }

    public static void show(Loot recipe, List<ItemStack> rewards) {
        Minecraft.getInstance().getToasts().addToast(new LootBagToast(recipe, rewards));
    }
}