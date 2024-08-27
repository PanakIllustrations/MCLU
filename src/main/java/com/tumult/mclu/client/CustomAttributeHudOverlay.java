package com.tumult.mclu.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;
import java.util.Objects;

public class CustomAttributeHudOverlay {
    private static final ResourceLocation MOD_ICONS = new ResourceLocation("minecraft", "textures/gui/mod_icons.png");

    public enum AnimationState {
        IDLE,
        FLASH_DELAY,
        ANTICIPATION,
        INTERPOLATION,
        FOLLOW_THROUGH
    }

    public enum HealthState {
        HEALED(179),
        DAMAGED(183);

        private final int stateValue;

        HealthState(int stateValue) {
            this.stateValue = stateValue;
        }

        public int getStateValue() {
            return stateValue;
        }

        public static HealthState toggleState(HealthState currentState) {
            return currentState == HEALED ? DAMAGED : HEALED;
        }
    }

    private static class HudState {
        PoseStack poseStack;
        HealthState previousState = HealthState.HEALED;
        HealthState currentState = HealthState.HEALED;
        AnimationState animationState = AnimationState.IDLE;
        int tickCounter = 0;

        int previousValue = 0;
        int maxValue;
        int currentValue;

        int previousBarWidth;
        int currentBarWidth;
        int maxBarWidth;

        int interpolationWidth;
        int anticipationWidth;
        int followThroughWidth;

        Color baseColor;
        boolean hasIcon = true;
        int iconX = 188;
        int iconY;

        int xOffset = 0;
        int yOffset = 0;
        int screenX;
        int screenY;

        public int getColorInt() {
            int red = baseColor.getRed();
            int green = baseColor.getGreen();
            int blue = baseColor.getBlue();
            return (red << R) | (green << G) | (blue << B);
        }

        public float getColor(String color) {
            if (Objects.equals(color, "red")) {
                return baseColor.getRed() / 255f;
            } else if (Objects.equals(color, "green")) {
                return baseColor.getGreen() / 255f;
            } else if (Objects.equals(color, "blue")) {
                return baseColor.getBlue() / 255f;
            }
            return 0;
        }
    }

    private static final HudState healthHudState = new HudState();
    private static final HudState armorHudState = new HudState();
    private static final HudState imaginationHudState = new HudState();
    private static final HudState uLevelHudState = new HudState();
    private static final int tickDelay = 4;
    private static final int R = 16;
    private static final int G = 8;
    private static final int B = 0;

    public static final IGuiOverlay CUSTOM_HEALTH_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            healthHudState.screenX = screenWidth / 2 - 92;
            healthHudState.screenY = 46;
            healthHudState.currentValue = (int) player.getHealth();
            healthHudState.maxValue = (int) player.getMaxHealth();
            healthHudState.baseColor = new Color(0xbb1313);
            healthHudState.iconY = 8;
            handleHudOverlay(healthHudState, guiGraphics);
            renderAdditionalElements(healthHudState, guiGraphics);
        }
    };

    public static final IGuiOverlay CUSTOM_ARMOR_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            armorHudState.maxValue = player.getArmorValue();
            if (armorHudState.maxValue > 0) {
                armorHudState.screenX = screenWidth / 2 - 92;
                armorHudState.screenY = 38;
                armorHudState.baseColor = new Color(0x696a70);
                armorHudState.yOffset = -4;
                armorHudState.iconY = 15;
                armorHudState.currentValue = (int) player.getAttributeValue(CustomAttributes.ARMOR_CURRENT.get());
                renderAdditionalElements(healthHudState, guiGraphics);
                if (armorHudState.currentValue > 0) {
                    handleHudOverlay(armorHudState, guiGraphics);
                }
            }
        }
    };

    public static final IGuiOverlay IMAGINATION_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            imaginationHudState.screenX = screenWidth / 2 - 92;
            imaginationHudState.screenY = screenHeight - 38;
            imaginationHudState.currentValue = (int) player.getAttributeValue(CustomAttributes.IMAGINATION_CURRENT.get());
            imaginationHudState.maxValue = (int) player.getAttributeValue(CustomAttributes.IMAGINATION_MAX.get());
            imaginationHudState.baseColor = new Color(0x50b2f9);
            imaginationHudState.hasIcon = false;
            handleHudOverlay(imaginationHudState, guiGraphics);
            renderAdditionalElements(imaginationHudState, guiGraphics);
        }
    };

    public static final IGuiOverlay U_LEVEL_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            uLevelHudState.screenX = screenWidth / 2 - 46;
            uLevelHudState.screenY = 22;
            uLevelHudState.currentValue = (int) player.getAttributeValue(CustomAttributes.U_SCORE_CURRENT.get());
            uLevelHudState.maxValue = (int) player.getAttributeValue(CustomAttributes.U_SCORE_NEEDED.get());
            uLevelHudState.baseColor = new Color(0xf5c648);
            uLevelHudState.hasIcon = false;
            handleHudOverlay(armorHudState, guiGraphics);
        }
    };

    private static void handleHudOverlay(HudState hudState, GuiGraphics guiGraphics) {
        if (hudState.previousValue == 0) {
            resetHudState(hudState);
        }
        hudState.currentBarWidth = hudState.currentValue * 8;
        hudState.previousBarWidth = hudState.previousValue * 8;
        hudState.maxBarWidth = hudState.maxValue * 8;
        // Render the bar container
        renderBarContainer(hudState, guiGraphics);

        // Handle state transitions
        if (hudState.animationState == AnimationState.IDLE && hudState.previousValue > 0) {
            if (hudState.currentValue < hudState.previousValue) {
                // value reduced
                initiateStateTransition(hudState, false);
            } else if (hudState.currentValue > hudState.previousValue) {
                // value increased
                initiateStateTransition(hudState, true);
            } else {
                hudState.animationState = AnimationState.IDLE;
                // value remained the same
            }
        }
        hudState.tickCounter++;

        // State machine for animation handling
        handleAnimationState(hudState, guiGraphics);
    }

    private static void resetHudState(HudState hudState) {
        hudState.previousState = HealthState.HEALED;
        hudState.currentState = HealthState.HEALED;
        hudState.animationState = AnimationState.IDLE;
    }

    private static void renderBarContainer(HudState hudState, GuiGraphics guiGraphics) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        guiGraphics.blit(MOD_ICONS, hudState.screenX, hudState.screenY, 0, 0, hudState.maxBarWidth + 1, 7);
        guiGraphics.blit(MOD_ICONS, hudState.screenX + hudState.maxBarWidth + 1, hudState.screenY, 180, 0, 2, 7);
    }

    private static void renderAdditionalElements(HudState hudState, GuiGraphics guiGraphics) {
        healthHudState.poseStack = new PoseStack();
        // Draw max value
        Font font = Minecraft.getInstance().font;
        String maxValueText = String.format("%d", hudState.maxValue);
        guiGraphics.drawString(font, maxValueText, hudState.screenX + (hudState.maxBarWidth) + 5, hudState.screenY, 0xafaeac, false);
        // Draw large current value
        String currentValueText = String.format("%d", hudState.currentValue);
        int valueTextWidth = font.width(currentValueText);
        hudState.poseStack.pushPose();
        hudState.poseStack.scale(2.0F, 2.0F, 2.0F);
        guiGraphics.drawString(font, currentValueText, hudState.screenX / 2 - valueTextWidth + hudState.xOffset, hudState.screenY / 2 + hudState.yOffset, hudState.getColorInt(), false);
        hudState.poseStack.popPose();
        // Draw value icon
        if (hudState.hasIcon) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            guiGraphics.blit(MOD_ICONS, hudState.screenX + 4, hudState.screenY + 2, hudState.iconX, hudState.iconY, 7, 4);
        }
    }

    private static Color screenColor(Color baseColor, float opacity) {
        float screen_alpha = 1 - opacity;
        float r = 255 - (255 - baseColor.getRed()) * screen_alpha;
        float g = 255 - (255 - baseColor.getGreen()) * screen_alpha;
        float b = 255 - (255 - baseColor.getBlue()) * screen_alpha;
        return new Color(r, g, b);
    }

    private static float screenChannel(float baseChannel, float opacity) {
        float screen_alpha = 1 - opacity;
        return (1 - (1 - baseChannel) * screen_alpha);
    }

    private static int RGB(int red, int green, int blue) {
        return (red << R) | (green << G) | (blue << B);
    }

    private static void initiateStateTransition(HudState hudState, boolean isHealed) {
        hudState.currentState = isHealed ? HealthState.HEALED : HealthState.DAMAGED;
        hudState.animationState = AnimationState.FLASH_DELAY;
        hudState.interpolationWidth = Mth.lerpInt(isHealed ? 0.40f : 0.60f, hudState.currentBarWidth, hudState.previousBarWidth);
        hudState.anticipationWidth = hudState.previousBarWidth + (isHealed ? -1 : 1);
        hudState.followThroughWidth = hudState.currentBarWidth + (isHealed ? 1 : -1);
    }

    private static void handleAnimationState(HudState hudState, GuiGraphics guiGraphics) {
        int x = hudState.screenX;
        int y = hudState.screenY;
        switch (hudState.animationState) {
            case IDLE:
                RenderSystem.setShaderColor(
                        hudState.getColor("red"),
                        hudState.getColor("green"),
                        hudState.getColor("blue"), 1.0f);
                guiGraphics.blit(MOD_ICONS, x, y, 0, 7, hudState.currentBarWidth, 7); // Health bar shaft
                guiGraphics.blit(MOD_ICONS, x + hudState.currentBarWidth, y, hudState.previousState.getStateValue(), 7, 4, 7); // Final damage state with dithered cap
                break;
            case FLASH_DELAY:
                RenderSystem.setShaderColor(
                        screenChannel(hudState.getColor("red"), 0.72f),
                        screenChannel(hudState.getColor("green"), 0.72f),
                        screenChannel(hudState.getColor("blue"), 0.72f), 1.0f);
                guiGraphics.blit(MOD_ICONS, x, y, 0, 7, hudState.previousBarWidth, 7); // Health bar shaft
                guiGraphics.blit(MOD_ICONS, x + hudState.previousBarWidth, y, hudState.previousState.getStateValue(), 7, 4, 7); // Dithered cap for damage
                if (hudState.tickCounter >= tickDelay) {
                    hudState.tickCounter = 0;
                    hudState.animationState = AnimationState.ANTICIPATION;
                }
                break;
            case ANTICIPATION:
                RenderSystem.setShaderColor(
                        screenChannel(hudState.getColor("red"), 0.72f),
                        screenChannel(hudState.getColor("green"), 0.72f),
                        screenChannel(hudState.getColor("blue"), 0.72f), 1.0f);
                int anticipationWidth = hudState.currentValue != hudState.maxValue ? hudState.anticipationWidth : hudState.previousBarWidth;
                guiGraphics.blit(MOD_ICONS, x, y, 0, 7, anticipationWidth, 7); // Health bar shaft
                guiGraphics.blit(MOD_ICONS, x + anticipationWidth, y, hudState.previousState.getStateValue(), 7, 4, 7); // Dithered cap for bounce

                if (hudState.tickCounter >= tickDelay) {
                    hudState.tickCounter = 0;
                    if (hudState.previousState != hudState.currentState) {
                        hudState.previousState = HealthState.toggleState(hudState.previousState);
                    }
                    hudState.animationState = AnimationState.INTERPOLATION;
                }
                break;
            case INTERPOLATION:
                RenderSystem.setShaderColor(
                        screenChannel(hudState.getColor("red"), 0.38f),
                        screenChannel(hudState.getColor("green"), 0.38f),
                        screenChannel(hudState.getColor("blue"), 0.38f), 1.0f);
                guiGraphics.blit(MOD_ICONS, x, y, 0, 7, hudState.interpolationWidth, 7); // Health bar shaft
                guiGraphics.blit(MOD_ICONS, x + hudState.interpolationWidth, y, hudState.currentState.getStateValue(), 7, 4, 7); // Dithered cap for bounce
                if (hudState.tickCounter >= tickDelay) {
                    hudState.tickCounter = 0;
                    hudState.animationState = AnimationState.FOLLOW_THROUGH;
                }
                break;
            case FOLLOW_THROUGH:
                RenderSystem.setShaderColor(
                        hudState.getColor("red"),
                        hudState.getColor("green"),
                        hudState.getColor("blue"), 1.0f);
                int followThroughWidth = hudState.currentValue != hudState.maxValue ? hudState.followThroughWidth : hudState.currentBarWidth;
                guiGraphics.blit(MOD_ICONS, x, y, 0, 7, followThroughWidth, 7); // Health bar shaft
                guiGraphics.blit(MOD_ICONS, x + followThroughWidth, y, hudState.currentState.getStateValue(), 7, 4, 7); // Final damage state with dithered cap

                if (hudState.tickCounter >= tickDelay) {
                    hudState.tickCounter = 0;
                    hudState.animationState = AnimationState.IDLE;
                }
                break;
            default:
                RenderSystem.setShaderColor(
                        hudState.getColor("red"),
                        hudState.getColor("green"),
                        hudState.getColor("blue"), 1.0f);
                hudState.animationState = AnimationState.IDLE;
                break;
        }
        if (hudState.animationState == AnimationState.IDLE) {
            hudState.previousValue = hudState.currentValue;
        }
    }
}