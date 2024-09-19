package com.tumult.mclu.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tumult.mclu.MCLU;
import com.tumult.mclu.client.gui.CustomAttributes;
import com.tumult.mclu.events.ModEvents;
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
    private static final ResourceLocation MOD_ICONS = new ResourceLocation(MCLU.MODID, "textures/gui/mod_icons.png");
    private static final ResourceLocation SLIDER = new ResourceLocation(MCLU.MODID, "textures/gui/slider.png");

    public enum AnimationState {
        IDLE,
        FLASH_DELAY,
        ANTICIPATION,
        INTERPOLATION,
        FOLLOW_THROUGH
    }

    public enum HealthState {
        HEALED(19), DAMAGED(15);
        private final int stateValue;
        HealthState(int stateValue) { this.stateValue = stateValue; }
        public int getStateValue() { return stateValue; }
        public static HealthState toggleState(HealthState currentState) {
            return currentState == HEALED ? DAMAGED : HEALED;
        }
    }

    private static class HudState {
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
        boolean drawValues = true;
        int iconX;
        int iconY = 7;

        int xOffset = 0;
        int yOffset = 0;
        int screenX;
        int screenY;

        public int getColorInt() {
            return baseColor.getRGB();
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
    private static final int tickDelay = 5;

    public static final IGuiOverlay CUSTOM_HEALTH_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            minimumHudStateSetup(healthHudState,
                    screenWidth / 2 - 92, 46,
                    (int) player.getHealth(), (int) player.getMaxHealth(),
                    new Color(0xbb1313), true);
            healthHudState.iconX = 0;
            healthHudState.iconY = 7;
            handleHudOverlay(healthHudState, guiGraphics);
            renderAdditionalElements(healthHudState, guiGraphics);
        }
    };

    public static final IGuiOverlay CUSTOM_ARMOR_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            int maxValue = player.getArmorValue();
            if (maxValue > 0) {
                int currentValue = (int) player.getAttributeValue(CustomAttributes.CUSTOM_ARMOR_CURRENT.get());
                minimumHudStateSetup(armorHudState,
                        screenWidth / 2 - 92, 37,
                        currentValue, maxValue,
                        new Color(0x696a70), true);
                armorHudState.yOffset = -3;
                armorHudState.iconX = 7;
                healthHudState.iconY = 7;
                handleHudOverlay(armorHudState, guiGraphics);
                renderAdditionalElements(armorHudState, guiGraphics);
            }
        }
    };

    public static final IGuiOverlay U_LEVEL_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            int currentValue = (int) player.getAttributeValue(CustomAttributes.U_SCORE_CURRENT.get());
            int maxValue = (int) player.getAttributeValue(CustomAttributes.U_SCORE_NEEDED.get());
            minimumHudStateSetup(uLevelHudState,
                    screenWidth / 2 - 46,  22,
                    currentValue, maxValue,
                    new Color(0xf5c648), false);
            uLevelHudState.hasIcon = false;
            uLevelHudState.drawValues = false;
            uLevelHudState.maxBarWidth = 10 * 8;
            uLevelHudState.previousBarWidth = (int)((float) uLevelHudState.maxBarWidth * ((float) uLevelHudState.currentValue / (float) uLevelHudState.maxValue));
            uLevelHudState.currentBarWidth = uLevelHudState.previousBarWidth;

            handleHudOverlay(uLevelHudState, guiGraphics);
            for (int i = 0; i < 9; i++) {
                guiGraphics.blit(MOD_ICONS, uLevelHudState.screenX + 9 + i * 8 , uLevelHudState.screenY + 3, 14, 7, 1, 1);
            }
        }
    };

    public static final IGuiOverlay IMAGINATION_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            minimumHudStateSetup(imaginationHudState,
                    screenWidth / 2 - 92, screenHeight - 38,
                    (int) player.getAttributeValue(CustomAttributes.IMAGINATION_CURRENT.get()),
                    (int) player.getAttributeValue(CustomAttributes.IMAGINATION_MAX.get()),
                    new Color(0x50b2f9), true);
            imaginationHudState.hasIcon = false;
            handleHudOverlay(imaginationHudState, guiGraphics);
            renderAdditionalElements(imaginationHudState, guiGraphics);
        }
    };

    public static final IGuiOverlay TEST_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && !player.isCreative()) {
            // Coordinates where the frame should appear on the screen.
            int xPosition = 50;
            int yPosition = 70;

            // The size of the area that you want to cover with the frame.
            int frameWidth = 80;
            int frameHeight = 7;

            // Padding or slice sizes for each part of the frame.
            int leftPadding = 2;  // Width of the left side (fixed)
            int topPadding = 2;   // Height of the top side (fixed)
            int rightPadding = 4; // Width of the right side (fixed)
            int bottomPadding = 2;// Height of the bottom side (fixed)

            // Original size of the image (full size of the texture).
            int textureWidth = 8;
            int textureHeight = 7;

            // The starting x and y coordinates in the texture (where the frame's texture begins).
            int textureX = 0;
            int textureY = 14; // 16 and 20

            RenderSystem.setShaderColor(0.16f, 0.16f, 0.16f, 0.5f);
            //containers
            guiGraphics.blitNineSliced(MOD_ICONS, xPosition, yPosition, frameWidth, frameHeight + 2,
                    leftPadding, topPadding, rightPadding, bottomPadding,
                    textureWidth, textureHeight, textureX, textureY);
            guiGraphics.blitNineSliced(MOD_ICONS, xPosition, yPosition + 20, frameWidth, frameHeight + 4,
                    leftPadding, topPadding, rightPadding, bottomPadding,
                    textureWidth, textureHeight, textureX, textureY);
            //color
            RenderSystem.setShaderColor(187f / 255, 19f / 255, 19f / 255, 1f);
            guiGraphics.blitNineSliced(MOD_ICONS, xPosition, yPosition, frameWidth - 10, frameHeight + 2,
                    leftPadding, topPadding, rightPadding, bottomPadding,
                    textureWidth, textureHeight, textureX, textureY);
            guiGraphics.blitNineSliced(MOD_ICONS, xPosition, yPosition + 20, frameWidth - 40, frameHeight + 4,
                    leftPadding, topPadding, rightPadding, bottomPadding,
                    textureWidth, textureHeight, textureX, textureY + 7);
        }
    };



    private static void handleHudOverlay(HudState hudState, GuiGraphics guiGraphics) {
        if (hudState.previousValue == 0) {
            resetHudState(hudState);
        }
        renderBarContainer(hudState, guiGraphics);

        if (hudState.animationState == AnimationState.IDLE && hudState.previousValue > 0) {
            if (hudState.currentValue > hudState.previousValue | !ModEvents.ModBusEvents.isHealthInitialized) {
                // value increased
                ModEvents.ModBusEvents.isHealthInitialized = true;
                initiateStateTransition(hudState, true);
            } else if (hudState.currentValue < hudState.previousValue) {
                // value reduced
                initiateStateTransition(hudState, false);
            } else {
                hudState.animationState = AnimationState.IDLE;
                // value remained the same
            }
        }
        hudState.tickCounter++;

        handleAnimationState(hudState, guiGraphics);
        renderAdditionalElements(hudState, guiGraphics);
    }

    private static void resetHudState(HudState hudState) {
        hudState.previousState = HealthState.HEALED;
        hudState.currentState = HealthState.HEALED;
        hudState.animationState = AnimationState.IDLE;
    }

    private static void minimumHudStateSetup(HudState hudState, int screenX, int screenY, int currentValue, int maxValue, Color baseColor, boolean standardBarWidths) {
        hudState.screenX = screenX;
        hudState.screenY = screenY;
        hudState.currentValue = currentValue;
        hudState.maxValue = maxValue;
        hudState.baseColor = baseColor;
        if (standardBarWidths) {
            hudState.previousBarWidth = hudState.previousValue * 8;
            hudState.currentBarWidth = currentValue * 8;
            hudState.maxBarWidth = maxValue * 8;
        }

    }

    private static void renderBarContainer(HudState hudState, GuiGraphics guiGraphics) {
        RenderSystem.setShaderColor(0.16f, 0.16f, 0.16f, 1f);
        guiGraphics.blit(MOD_ICONS, hudState.screenX, hudState.screenY, 0, 0, hudState.maxBarWidth, 7);
        guiGraphics.blit(MOD_ICONS, hudState.screenX + hudState.maxBarWidth, hudState.screenY, 19, 7, 4, 7);
    }

    private static float screenChannel(float baseChannel, float opacity) {
        float screen_alpha = 1 - opacity;
        return (1 - (1 - baseChannel) * screen_alpha);
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
                guiGraphics.blit(MOD_ICONS, x, y, 0, 0, hudState.currentBarWidth, 7); // Health bar shaft
                if (hudState.currentValue > 0) {
                    guiGraphics.blit(MOD_ICONS, x + hudState.currentBarWidth, y, hudState.previousState.getStateValue(), 7, 4, 7); // Final damage state with dithered cap
                }
                break;
            case FLASH_DELAY:
                RenderSystem.setShaderColor(
                        screenChannel(hudState.getColor("red"), 0.72f),
                        screenChannel(hudState.getColor("green"), 0.72f),
                        screenChannel(hudState.getColor("blue"), 0.72f), 1.0f);
                guiGraphics.blit(MOD_ICONS, x, y, 0, 7, hudState.previousBarWidth, 7); // Health bar shaft
                guiGraphics.blit(MOD_ICONS, x + hudState.previousBarWidth, y, hudState.previousState.getStateValue(), 6, 4, 6); // Dithered cap for damage
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
                guiGraphics.blit(MOD_ICONS, x, y, 0, 0, anticipationWidth, 7); // Health bar shaft
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
                guiGraphics.blit(MOD_ICONS, x, y, 0, 0, hudState.interpolationWidth, 7); // Health bar shaft
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
                guiGraphics.blit(MOD_ICONS, x, y, 0, 0, followThroughWidth, 7); // Health bar shaft
                if (hudState.currentValue > 0) {
                    guiGraphics.blit(MOD_ICONS, x + followThroughWidth, y, hudState.currentState.getStateValue(), 7, 4, 7); // Final damage state with dithered cap
                }
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

    private static void renderAdditionalElements(HudState hudState, GuiGraphics guiGraphics) {
        if (hudState.drawValues) {
            PoseStack poseStack = guiGraphics.pose();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            // Draw max value
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            Font font = Minecraft.getInstance().font;
            guiGraphics.drawString(font, String.format("%d", hudState.maxValue),
                    (hudState.screenX + (hudState.maxBarWidth)) * 2 - 2,
                    hudState.screenY * 2 + 4,
                    0xafaeac, false);
            poseStack.popPose();
            // Draw large current value
            poseStack.pushPose();
            poseStack.scale(2.0F, 2.0F, 2.0F);
            guiGraphics.drawString(font, String.format("%d", hudState.currentValue),
                    hudState.screenX / 2 - font.width(String.format("%d", hudState.currentValue)) + hudState.xOffset,
                    hudState.screenY / 2 + hudState.yOffset,
                    hudState.getColorInt(), false);
            poseStack.popPose();
        }

        // Draw value icon
        if (hudState.hasIcon) {
            guiGraphics.blit(MOD_ICONS, hudState.screenX + 4, hudState.screenY + 3, hudState.iconX, hudState.iconY, 7, 4);
        }
    }
}