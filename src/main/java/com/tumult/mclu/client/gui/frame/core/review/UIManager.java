package com.tumult.mclu.client.gui.frame.core.review;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;

public class UIManager {
    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;

    public static boolean[] pressedButtons = new boolean[3];
    private static float[] clampedMousePos = new float[2];
    private static boolean isCursorVisible;

    protected static final int LEFT_BUTTON = 0;
    protected static final int RIGHT_BUTTON = 1;
    protected static final int MIDDLE_BUTTON = 2;

    private static int tickCounter = 0;
    private static ShaderInstance shader;

    public static void init(double width, double height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
    public static void toggleCursor() {
        isCursorVisible = !isCursorVisible;
    }
    public static boolean isCursorVisible() {
        return isCursorVisible;
    }

    public static boolean[] getMouseButtons() {
        Minecraft mc = Minecraft.getInstance();
        pressedButtons[LEFT_BUTTON] = mc.mouseHandler.isLeftPressed();
        pressedButtons[RIGHT_BUTTON] = mc.mouseHandler.isRightPressed();
        pressedButtons[MIDDLE_BUTTON] = mc.mouseHandler.isMiddlePressed();
        return pressedButtons;
    }

    public static float[] getMousePos() {
        if (isCursorVisible) {
            Minecraft mc = Minecraft.getInstance();
            long window = mc.getWindow().getWindow();
            double guiScaleFactor = mc.getWindow().getGuiScale() / 2;
            try (MemoryStack stack = MemoryStack.stackPush()) {
                DoubleBuffer xPos = stack.mallocDouble(1);
                DoubleBuffer yPos = stack.mallocDouble(1);
                GLFW.glfwGetCursorPos(window, xPos, yPos);

                // Update screenMousePos and clampedMousePos based on current mouse position
                double screenMouseX = xPos.get(0) / guiScaleFactor;
                double screenMouseY = yPos.get(0) / guiScaleFactor;

                float[] windowDimensions = {mc.getWindow().getWidth(), mc.getWindow().getHeight()};

                // Clamp the mouse position within the window bounds
                clampedMousePos = new float[]{
                        (float) Math.max(0, Math.min(screenMouseX, windowDimensions[0] / guiScaleFactor - 1)),
                        (float) Math.max(0, Math.min(screenMouseY, windowDimensions[1] / guiScaleFactor - 1))
                };
                if (tickCounter > 20) {
                    System.out.println("x: " + clampedMousePos[0] + " y: " + clampedMousePos[1]);
                    tickCounter = 0;
                }
                tickCounter++;
            }
        }
        return clampedMousePos;
    }


}
