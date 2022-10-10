package tech.thatgravyboat.lootbags.client.popup;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.common.recipe.LootRecipe;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LootBagToast implements Toast {
    private final LootRecipe recipe;
    private final List<ItemStack> rewards;

    public LootBagToast(LootRecipe recipe, List<ItemStack> rewards) {
        this.recipe = recipe;
        this.rewards = rewards;
    }

    public Visibility render(@NotNull PoseStack poseStack, ToastComponent toastComponent, long l) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        ItemStack itemStack = rewards.get((int) ((l / 500) % rewards.size()));
        DisplayInfo displayInfo = recipe.getDisplayInfo(itemStack);
        toastComponent.blit(poseStack, 0, 0, 0, 0, this.width(), this.height());
        List<FormattedCharSequence> list = toastComponent.getMinecraft().font.split(displayInfo.getTitle(), 125);
        int i = displayInfo.getFrame() == FrameType.CHALLENGE ? 16746751 : 16776960;
        if (list.size() == 1) {
            toastComponent.getMinecraft().font.draw(poseStack, displayInfo.getFrame().getDisplayName(), 30.0F, 7.0F, i | -16777216);
            toastComponent.getMinecraft().font.draw(poseStack, list.get(0), 30.0F, 18.0F, -1);
        } else {
            int k;
            if (l < 1500L) {
                k = Mth.floor(Mth.clamp((float)(1500L - l) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                toastComponent.getMinecraft().font.draw(poseStack, displayInfo.getFrame().getDisplayName(), 30.0F, 11.0F, i | k);
            } else {
                k = Mth.floor(Mth.clamp((float)(l - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                int var10000 = this.height() / 2;
                int var10001 = list.size();
                Objects.requireNonNull(toastComponent.getMinecraft().font);
                int m = var10000 - var10001 * 9 / 2;

                for(Iterator<FormattedCharSequence> var12 = list.iterator(); var12.hasNext(); m += 9) {
                    FormattedCharSequence formattedCharSequence = var12.next();
                    toastComponent.getMinecraft().font.draw(poseStack, formattedCharSequence, 30.0F, (float) m, 16777215 | k);
                    Objects.requireNonNull(toastComponent.getMinecraft().font);
                }
            }
        }
        toastComponent.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(displayInfo.getIcon(), 8, 8);
        return l >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }
}
