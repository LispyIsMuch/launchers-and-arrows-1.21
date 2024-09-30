package net.stln.launchersandarrows.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.entity.AttributeDataTracker;
import net.stln.launchersandarrows.status_effect.StatusEffectInit;
import org.jetbrains.annotations.Nullable;

public class AttributeEffectInfoRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register(((drawContext, tickCounter) -> {
            Identifier TEXTURE = Identifier.of(LaunchersAndArrows.MOD_ID, "textures/gui/effect_bar.png");
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            PlayerEntity playerEntity = MinecraftClient.getInstance().player;
            int h = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int w = MinecraftClient.getInstance().getWindow().getScaledWidth();
            int ch = h / 2;
            int cw = w / 2;
            int offsetH = 32;
            int barW = 0;
            int frameW = 0;
            int flame = ((AttributeDataTracker)playerEntity).getAccumulationTracker(0);
            int burning = ((AttributeDataTracker)playerEntity).getEffectDuration(0);
            barW = getScaledBarWidth(playerEntity, flame, burning);
            frameW = getScaledFrameWidth(playerEntity, burning);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 0, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 1, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(burning / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(flame),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int frost = ((AttributeDataTracker)playerEntity).getAccumulationTracker(1);
            int freeze = ((AttributeDataTracker)playerEntity).getEffectDuration(1);
            barW = getScaledBarWidth(playerEntity, frost, freeze);
            frameW = getScaledFrameWidth(playerEntity, freeze);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 8, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 9, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(freeze / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(frost),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int lightning = ((AttributeDataTracker)playerEntity).getAccumulationTracker(2);
            int electricShock = ((AttributeDataTracker)playerEntity).getEffectDuration(2);
            barW = getScaledBarWidth(playerEntity, lightning, electricShock);
            frameW = getScaledFrameWidth(playerEntity, electricShock);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 16, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 17, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(electricShock / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(lightning),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int acid = ((AttributeDataTracker)playerEntity).getAccumulationTracker(3);
            int corrosion = ((AttributeDataTracker)playerEntity).getEffectDuration(3);
            barW = getScaledBarWidth(playerEntity, acid, corrosion);
            frameW = getScaledFrameWidth(playerEntity, corrosion);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 24, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 25, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(corrosion / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(acid),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int flood = ((AttributeDataTracker)playerEntity).getAccumulationTracker(4);
            int submerged = ((AttributeDataTracker)playerEntity).getEffectDuration(4);
            barW = getScaledBarWidth(playerEntity, flood, submerged);
            frameW = getScaledFrameWidth(playerEntity, submerged);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 32, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 33, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(submerged / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(flood),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int echo = ((AttributeDataTracker)playerEntity).getAccumulationTracker(5);
            int confusion = ((AttributeDataTracker)playerEntity).getEffectDuration(5);
            barW = getScaledBarWidth(playerEntity, echo, confusion);
            frameW = getScaledFrameWidth(playerEntity, confusion);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 40, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 41, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(confusion / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(echo),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int injury = ((AttributeDataTracker)playerEntity).getAccumulationTracker(6);
            barW = getScaledBarWidth(playerEntity, injury, 0);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 48, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 49, barW, 6, 64, 64);
                drawContext.drawText(renderer,
                        String.valueOf(injury),
                        30, h - offsetH, 0xFFFFFF, true);
            }
        }));
    }

    private static int getScaledBarWidth(PlayerEntity playerEntity, int accumulation, int duration) {
        if (duration > 0) {
            return 22;
        }
        if (accumulation > 0) {
            int maxAmpl = (int) (Math.sqrt(playerEntity.getMaxHealth()) * 5);
            return (accumulation * 22 / maxAmpl);
        }
        return -1;
    }
    private static int getScaledFrameWidth(PlayerEntity playerEntity, int duration) {
        if (duration > 0) {
            return duration * 24 / 300;
        }
        return -1;
    }
}
