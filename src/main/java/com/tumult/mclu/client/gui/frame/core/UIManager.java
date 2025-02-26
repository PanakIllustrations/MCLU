package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.core.util.RenderingConfig;
import com.tumult.mclu.client.gui.frame.core.util.UIProfiler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    // Add thread pool for async operations
    private static final ExecutorService layoutService = Executors.newSingleThreadExecutor();
    private static final ScheduledExecutorService profilerService = Executors.newSingleThreadScheduledExecutor();

    // Root container for UI hierarchy
    private static GuiContainer rootContainer;

    public static void init(double width, double height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;

        // Set up profiler service to print results periodically if enabled
        if (RenderingConfig.ENABLE_PROFILING) {
            profilerService.scheduleAtFixedRate(() -> {
                UIProfiler.printResults();
                UIProfiler.reset();
            }, 5, 5, TimeUnit.SECONDS);
        }
    }

    public static void setRootContainer(GuiContainer container) {
        rootContainer = container;
    }

    public static GuiContainer getRootContainer() {
        return rootContainer;
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

                // Only log occasionally to reduce spam
                if (tickCounter > 20 && RenderingConfig.ENABLE_PROFILING) {
                    System.out.println("x: " + clampedMousePos[0] + " y: " + clampedMousePos[1]);
                    tickCounter = 0;
                }
                tickCounter++;
            }
        }
        return clampedMousePos;
    }

    // Add method to request asynchronous layout
    public static void requestAsyncLayout(final GuiContainer container) {
        layoutService.execute(container::layoutChildren);
    }

    // Add method to render the full UI hierarchy
    public static void renderUI() {
        if (rootContainer == null) return;

        RenderManager.beginFrame();

        // Handle interaction and rendering
        if (isCursorVisible()) {
            float[] cursorPos = getMousePos();
            boolean[] buttons = getMouseButtons();

            if (rootContainer != null) {
                rootContainer.handleDragging(cursorPos, buttons);
                rootContainer.render();
            }
        } else if (rootContainer != null) {
            rootContainer.render();
        }

        RenderManager.renderBatches();
        RenderManager.endFrame();
    }

    public static void shutdown() {
        layoutService.shutdown();
        profilerService.shutdown();
    }
}