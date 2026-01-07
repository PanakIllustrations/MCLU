package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.frame.core.*;
import com.tumult.mclu.client.gui.frame.geometry.DrawableSprite;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import com.tumult.mclu.client.gui.icons.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;

import static com.tumult.mclu.client.gui.frame.core.UIManager.getMouseButtons;

public class GuiHUD {
    static final DrawableSprite cursor = IconUtils.getIcon().mouse_cursor;
    static final DraggableRect rect = new DraggableRect(Color.BLACK, new Vector4DRect(30, 30, 128, 160), 10);
    static final CloseButton bankCross = IconUtils.getIcon().bankCross;
    static final ResizeHandle bankResize = IconUtils.getIcon().bankResize;

    static final ToggleButton backpack = IconUtils.getIcon().backpack;
    static final DrawableSprite backpackReflection = IconUtils.getIcon().backpack_reflection;


    static Vector2DPoint mouseCursor = new Vector2DPoint();

    // Static initializer to set up the hierarchy
    static {
        // Add children to rect
        rect.addChild(bankCross);
        rect.addChild(bankResize);

        // Set minimum resize dimensions
        bankResize.setMinSize(32, 40);

        // Set up toggle button
        backpack.setTarget(rect);
        backpack.setUL(new Vector2DPoint(315, 200));
        backpackReflection.setUL(new Vector2DPoint(315, 215));

        // Position controls
        updateControlPositions();
    }

    private static void updateControlPositions() {
        Vector4DRect rectBounds = rect.getBounds();

        // Position bankCross at top-right corner
        bankCross.setUL(new Vector2DPoint(
                rectBounds.right() - (bankCross.getBounds().width() + 2),
                rectBounds.top() + 2
        ));

        // Position bankResize at bottom-right corner
        bankResize.setUL(new Vector2DPoint(
                rectBounds.right() - (bankResize.getBounds().width() + 1),
                rectBounds.bottom() - (bankResize.getBounds().height() + 11)
        ));
    }

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        UIManager.init(screenWidth, screenHeight);
        mouseCursor = UIManager.getMousePos();

        if (player != null) {
            if (UIManager.isCursorVisible()) {

                // Update toggle button FIRST (always active)
                backpack.update(mouseCursor, getMouseButtons());

                // Only update window controls if window is visible
                if (rect.isVisible) {
                    bankCross.update(mouseCursor, getMouseButtons());
                    bankResize.update(mouseCursor, getMouseButtons());
                    rect.update(mouseCursor, getMouseButtons());

                    // Update control positions after any drag/resize
                    updateControlPositions();
                }
            }

            // Draw toggle button (always visible)
            backpack.draw(guiGraphics);
            backpackReflection.draw(guiGraphics);

            // Draw rect and all children (only if visible)
            if (rect.isVisible) {
                rect.drawTree(guiGraphics);
            }
        }
    };

    public static final IGuiOverlay CURSOR = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        UIManager.init(screenWidth, screenHeight);

        if (player != null) {
            if (UIManager.isCursorVisible()) {
                // Draw cursor on top
                cursor.draw(guiGraphics, UIManager.getMousePos());
            }
        }
    };
}