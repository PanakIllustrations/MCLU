package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.core.UIElement;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import net.minecraft.client.Minecraft;
import org.joml.Vector2d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.List;

public class GuiCursor {
    private static boolean isCursorVisible;
    private static Vector2DPoint clampedMousePos;
    private static int tickCounter = 0;

    public Vector2DPoint getMousePos() {
        updateMousePosition();
        return clampedMousePos;
    }

    public static void updateMousePosition() {
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
    }

    public static boolean isCursorVisible() {
        return isCursorVisible;
    }

    public static void toggleCursorVisible() {
        isCursorVisible = !isCursorVisible;
        long window = Minecraft.getInstance().getWindow().getWindow();
        if (isCursorVisible) {
            System.out.println("Cursor is now visible");
            GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
        } else {
            System.out.println("Cursor is now hidden");
            GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        }
    }
}
