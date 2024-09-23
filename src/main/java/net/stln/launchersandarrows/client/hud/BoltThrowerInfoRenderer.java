package net.stln.launchersandarrows.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.stln.launchersandarrows.item.component.ModComponentInit;
import net.stln.launchersandarrows.item.launcher.BoltThrowerItem;
import net.stln.launchersandarrows.util.TextUtil;

public class BoltThrowerInfoRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register(((drawContext, tickCounter) -> {
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            PlayerEntity playerEntity = MinecraftClient.getInstance().player;
            int h = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int w = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int ch = h / 2;
            int cw = w / 2;
            if (playerEntity != null) {
                ItemStack mainStack = playerEntity.getMainHandStack();
                ItemStack offStack = playerEntity.getOffHandStack();
                ItemStack renderStack = mainStack.getItem() instanceof BoltThrowerItem ? mainStack :
                        offStack.getItem() instanceof BoltThrowerItem ? offStack : null;
                if (renderStack != null) {
                    Integer chargedCount = renderStack.get(ModComponentInit.CHARGED_BOLT_COUNT_COMPONENT);
                    Integer count = renderStack.get(ModComponentInit.BOLT_COUNT_COMPONENT);
                    drawContext.drawText(renderer, "| " + chargedCount.toString() + " |",
                            cw - TextUtil.getNumberCenter(chargedCount) - 6, (int) (ch * 1.3), 0xFFFFFF, true);
                    drawContext.drawText(renderer,
                            "| " + (count - chargedCount) + " |",
                            cw - TextUtil.getNumberCenter((count - chargedCount)) - 6, (int) (ch * 1.3) + 8, 0x808080, true);
                }
            }
        }));
    }
}
