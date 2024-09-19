package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.MCLU;
import com.tumult.mclu.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;

public class GuiCursor {
    private boolean isCursorVisible;
    private double clampedMouseX;
    private double clampedMouseY;
    private double screenMouseX;
    private double screenMouseY;
    private DoubleBuffer xPos;
    private DoubleBuffer yPos;

    private final GuiIcon cursorIcon;

    public GuiCursor() {
        this.isCursorVisible = false;
        this.clampedMouseX = 0;
        this.clampedMouseY = 0;
        this.screenMouseX = 0;
        this.screenMouseY = 0;
        this.cursorIcon = new GuiIcon(new ResourceLocation(MCLU.MODID, "textures/gui/mouse_cursor.png"), 9, 17, 0, 0, 32, 32);
        this.xPos = null;
        this.yPos = null;
       }

    public boolean isCursorVisible() {
        return isCursorVisible;
    }

    public void toggleCursorVisible() {
        this.isCursorVisible = !this.isCursorVisible;
        long window = Minecraft.getInstance().getWindow().getWindow();
        if (isCursorVisible) {
            System.out.println("Cursor is now visible");
            GLFW.glfwSetInputMode(window,GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
        } else {
            System.out.println("Cursor is now hidden");
            GLFW.glfwSetInputMode(window,GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
        }
    }

    public void getMousePosition() {
        Minecraft mc = Minecraft.getInstance();
        long window = mc.getWindow().getWindow();
        double guiScaleFactor = mc.getWindow().getGuiScale() * 2;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Allocate DoubleBuffers to store X and Y positions
            xPos = stack.mallocDouble(1);
            yPos = stack.mallocDouble(1);
            GLFW.glfwGetCursorPos(window, xPos, yPos);

            screenMouseX = xPos.get(0) / guiScaleFactor;
            screenMouseY = yPos.get(0) / guiScaleFactor;

            // Access the cursor position from the buffers
            this.clampedMouseX = Math.max(0, (Math.min(screenMouseX, mc.getWindow().getWidth() / guiScaleFactor) - 1));
            this.clampedMouseY = Math.max(0, (Math.min(screenMouseY, mc.getWindow().getHeight() / guiScaleFactor) - 1));

            // Print the cursor coordinates
            //System.out.println("Mouse X: " + clampedMouseX);
            //System.out.println("Mouse Y: " + clampedMouseY);
        }
    }

    public static final IGuiOverlay CURSOR_ICON = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        GuiCursor guiCursor = ClientEvents.guiCursor;

        if (player != null && guiCursor.isCursorVisible()) {
            guiCursor.getMousePosition();
            guiCursor.drawCursor(guiGraphics);
        }
    };

    public void drawCursor(GuiGraphics guiGraphics) {
        cursorIcon.draw(guiGraphics, clampedMouseX, clampedMouseY, 255);
    }

    public double getMouseX() {
        return clampedMouseX;
    }
    public double getMouseY() {
        return clampedMouseY;
    }
}
