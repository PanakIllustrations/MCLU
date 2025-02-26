package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.core.css.Style;
import com.tumult.mclu.client.gui.frame.core.css.StyleSheet;
import com.tumult.mclu.client.gui.frame.core.layout.FlexLayout;
import com.tumult.mclu.client.gui.frame.core.layout.GridLayout;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import com.tumult.mclu.client.gui.frame.core.geometry.RoundRect;
import com.tumult.mclu.client.gui.frame.core.GuiContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;
public class GuiHUD {
    private static boolean setup = false;
    private static GuiContainer UICanvas;
    private static GuiContainer inventory;
    private static GuiElement inventorySlot;
    private static GuiSprite mouseCursor;
    private static GuiContainer toolbar;
    private static GuiElement toolbarSlot;
    private static GuiContainer scrollContainer;

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        // Initialize the UI manager with screen dimensions
        UIManager.init(screenWidth, screenHeight);


        if (!setup) {
//            inventory = new GuiContainer(
//                    new RoundRect(0,0,0,0,1,0),
//                    StyleSheet.getStyle("inventory").getBackgroundColor());
//
//            for (int i = 0; i < 20; i++) {
//                inventorySlot = new GuiElement(
//                        new RoundRect(0,0,0,0,1,0),
//                        StyleSheet.getStyle("inventory_slot"));
//                inventory.addChild(inventorySlot);
//            }
//            UICanvas = new GuiContainer(
//                    new RoundRect(0, 0, screenWidth, screenHeight, 1, 15),
//                    new Color(33, 33, 33, 200) // Dark semi-transparent background
//            );
//            UICanvas.addChild(inventory);
//            UIManager.setRootContainer(UICanvas);
//            mouseCursor = Registry.icon.get().mouse_cursor;
//            setup = true;
        }

        if (UIManager.isCursorVisible()) {
            float[] mousePos = UIManager.getMousePos();
            mouseCursor.rect.setUL(mousePos);
            mouseCursor.render();
        }
    };
}