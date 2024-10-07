package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UIManager {
    public static double SCREEN_WIDTH;
    public static double SCREEN_HEIGHT;


    public static void init(double width, double height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }

    public static void drawAll(GuiGraphics guiGraphics, List<UIElement> elements) {
        elements.sort(Comparator.comparingDouble(e -> e.zLevel));
        for (UIElement element : elements) {
            if (!element.isHidden) {
                element.draw(guiGraphics);
            }
        }
    }
    public static void updateAll(List<UIElement> elements) {
        for (UIElement element : elements) {
            if (!element.isHidden) {
                element.update(getMousePosition(), getMouseButtons());
            }
        }
    }
    public static List<Integer> getMouseButtons() {
        Minecraft mc = Minecraft.getInstance();
        List<Integer> pressedButtons = new ArrayList<>();
        if (mc.mouseHandler.isLeftPressed()) pressedButtons.add(0);
        if (mc.mouseHandler.isRightPressed()) pressedButtons.add(1);
        if (mc.mouseHandler.isMiddlePressed()) pressedButtons.add(2);
        return pressedButtons;
    }

    public static Vector2DPoint getMousePosition() {
        Minecraft mc = Minecraft.getInstance();
        double mouseX = mc.mouseHandler.xpos(); // or however you get the mouse X position
        double mouseY = mc.mouseHandler.ypos(); // or however you get the mouse Y position
        return new Vector2DPoint(mouseX, mouseY);
    }
}
