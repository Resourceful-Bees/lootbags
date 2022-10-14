package tech.thatgravyboat.lootbags.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import com.teamresourceful.resourcefullib.common.color.ConstantColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.lootbags.common.recipe.Loot;

import java.util.List;

public class LootBagToast implements Toast {
     private final Loot loot;
     private final List<ItemStack> rewards;

     public LootBagToast(Loot loot, List<ItemStack> rewards) {
         this.loot = loot;
         this.rewards = rewards;
     }

     public Visibility render(@NotNull PoseStack poseStack, ToastComponent toastComponent, long l) {
         RenderUtils.bindTexture(TEXTURE);
         ItemStack itemStack = rewards.get((int) (Math.min(rewards.size() - 1, l / 1000)));
         toastComponent.blit(poseStack, 0, 0, 0, 0, this.width(), this.height());
         Font font = toastComponent.getMinecraft().font;
         font.draw(poseStack, loot.name(), 30.0F, 7.0F, ConstantColors.lightgray.getValue());
         font.draw(poseStack, itemStack.getHoverName(), 30.0F, 18.0F, ConstantColors.lightgray.getValue());
         toastComponent.getMinecraft().getItemRenderer().renderAndDecorateItem(itemStack, 8, 8);
         if(itemStack.getCount() > 1) {
             String string2 = String.valueOf(itemStack.getCount());
             poseStack.pushPose();
             poseStack.translate(0.0D, 0.0D, toastComponent.getMinecraft().getItemRenderer().blitOffset + 200);
             font.draw(poseStack, string2, (float) (9 + 19 - 2 - font.width(string2)), (float) (9 + 19 - font.lineHeight), 0xFF555555);
             font.draw(poseStack, string2, (float) (8 + 19 - 2 - font.width(string2)), (float) (8 + 19 - font.lineHeight), ConstantColors.white.getValue());
             poseStack.popPose();
         }
         return l >= (rewards.size() > 2 ? Math.min(10000L, rewards.size() * 1000L) : 2000L)  ? Visibility.HIDE : Visibility.SHOW;
     }
 }