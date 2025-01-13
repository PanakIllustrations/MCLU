package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UIManager {
    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;

    public static List<Integer> pressedButtons = new ArrayList<>();
    private static Vector2DPoint clampedMousePos = new Vector2DPoint();
    private static boolean isCursorVisible;

    protected static final int LEFT_BUTTON = 0;
    protected static final int RIGHT_BUTTON = 1;
    protected static final int MIDDLE_BUTTON = 2;

    private static int tickCounter = 0;

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

    public static List<Integer> getMouseButtons() {
        Minecraft mc = Minecraft.getInstance();

        pressedButtons.clear();
        if (mc.mouseHandler.isLeftPressed()) pressedButtons.add(LEFT_BUTTON);
        if (mc.mouseHandler.isRightPressed()) pressedButtons.add(RIGHT_BUTTON);
        if (mc.mouseHandler.isMiddlePressed()) pressedButtons.add(MIDDLE_BUTTON);
        return pressedButtons;
    }

    public static Vector2DPoint getMousePos() {
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

                Vector2DPoint windowDimensions = new Vector2DPoint(mc.getWindow().getWidth(), mc.getWindow().getHeight());

                // Clamp the mouse position within the window bounds
                clampedMousePos = new Vector2DPoint(
                        Math.max(0, Math.min(screenMouseX, windowDimensions.x / guiScaleFactor - 1)),
                        Math.max(0, Math.min(screenMouseY, windowDimensions.y / guiScaleFactor - 1))
                );
                if (tickCounter > 20) {
                    System.out.println("x: " + clampedMousePos.x + " y: " + clampedMousePos.y);
                    tickCounter = 0;
                }
                tickCounter++;
            }
        }
        return clampedMousePos;
    }
}
