package net.stln.launchersandarrows.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.stln.launchersandarrows.LaunchersAndArrows;
import net.stln.launchersandarrows.entity.AttriburteDataTracker;
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
            int flame = ((AttriburteDataTracker)playerEntity).getTracker(0);
            barW = getScaledBarWidth(playerEntity, flame, StatusEffectInit.BURNING);
            frameW = getScaledFrameWidth(playerEntity, StatusEffectInit.BURNING);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 0, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 1, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(playerEntity.getStatusEffect(StatusEffectInit.BURNING).getDuration() / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(flame),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int frost = ((AttriburteDataTracker)playerEntity).getTracker(1);
            barW = getScaledBarWidth(playerEntity, frost, StatusEffectInit.FREEZE);
            frameW = getScaledFrameWidth(playerEntity, StatusEffectInit.FREEZE);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 8, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 9, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(playerEntity.getStatusEffect(StatusEffectInit.FREEZE).getDuration() / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(frost),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int lightning = ((AttriburteDataTracker)playerEntity).getTracker(2);
            barW = getScaledBarWidth(playerEntity, lightning, StatusEffectInit.ELECTRIC_SHOCK);
            frameW = getScaledFrameWidth(playerEntity, StatusEffectInit.ELECTRIC_SHOCK);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 16, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 17, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(playerEntity.getStatusEffect(StatusEffectInit.ELECTRIC_SHOCK).getDuration() / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(lightning),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int acid = ((AttriburteDataTracker)playerEntity).getTracker(3);
            barW = getScaledBarWidth(playerEntity, acid, StatusEffectInit.CORROSION);
            frameW = getScaledFrameWidth(playerEntity, StatusEffectInit.CORROSION);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 24, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 25, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(playerEntity.getStatusEffect(StatusEffectInit.CORROSION).getDuration() / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(acid),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int flood = ((AttriburteDataTracker)playerEntity).getTracker(4);
            barW = getScaledBarWidth(playerEntity, flood, StatusEffectInit.SUBMERGED);
            frameW = getScaledFrameWidth(playerEntity, StatusEffectInit.SUBMERGED);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 32, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 33, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(playerEntity.getStatusEffect(StatusEffectInit.SUBMERGED).getDuration() / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(flood),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int echo = ((AttriburteDataTracker)playerEntity).getTracker(5);
            barW = getScaledBarWidth(playerEntity, echo, StatusEffectInit.CONFUSION);
            frameW = getScaledFrameWidth(playerEntity, StatusEffectInit.CONFUSION);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 40, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 41, barW, 6, 64, 64);
                if (frameW > -1) {
                    drawContext.drawTexture(TEXTURE, 2, h - offsetH, 0, 56, frameW, 8, 64, 64);
                    drawContext.drawText(renderer,
                            String.valueOf(playerEntity.getStatusEffect(StatusEffectInit.CONFUSION).getDuration() / 20),
                            30, h - offsetH, 0xFFFFFF, true);
                } else {
                    drawContext.drawText(renderer,
                            String.valueOf(echo),
                            30, h - offsetH, 0xFFFFFF, true);
                }
                offsetH += 10;
            }
            int injury = ((AttriburteDataTracker)playerEntity).getTracker(6);
            barW = getScaledBarWidth(playerEntity, injury, null);
            if (barW > -1) {
                drawContext.drawTexture(TEXTURE, 2, h - offsetH, 24, 48, 24, 8, 64, 64);
                drawContext.drawTexture(TEXTURE, 3, h - offsetH + 1, 1, 49, barW, 6, 64, 64);
                drawContext.drawText(renderer,
                        String.valueOf(injury),
                        30, h - offsetH, 0xFFFFFF, true);
            }
        }));
    }

    private static int getScaledBarWidth(PlayerEntity playerEntity, int accumulation, @Nullable RegistryEntry<StatusEffect> effect) {
        if (effect != null && playerEntity.hasStatusEffect(effect)) {
            return 22;
        }
        if (accumulation > 0) {
            int maxAmpl = (int) (Math.sqrt(playerEntity.getMaxHealth()) * 5);
            return (accumulation * 22 / maxAmpl);
        }
        return -1;
    }
    private static int getScaledFrameWidth(PlayerEntity playerEntity, RegistryEntry<StatusEffect> effect) {
        if (playerEntity.hasStatusEffect(effect)) {
            return playerEntity.getStatusEffect(effect).getDuration() * 24 / 300;
        }
        return -1;
    }
}
