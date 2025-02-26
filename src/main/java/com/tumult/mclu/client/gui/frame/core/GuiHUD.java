package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.core.css.Style;
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
    private static GuiContainer mainPanel;
    private static GuiContainer gridContainer;
    private static GuiContainer topToolbar;
    private static GuiContainer bottomToolbar;
    private static GuiContainer scrollContainer;

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        // Initialize the UI manager with screen dimensions
        UIManager.init(screenWidth, screenHeight);

        if (!setup) {
            // Create the main panel that holds everything
            mainPanel = new GuiContainer(
                    new RoundRect(screenWidth * 0.6f, screenHeight * 0.15f, screenWidth * 0.35f, screenHeight * 0.7f, 1, 15),
                    new Color(33, 33, 33, 200) // Dark semi-transparent background
            );

            // Create the grid container for the 4x5 button grid
            gridContainer = new GuiContainer(
                    new RoundRect(10, 150, mainPanel.width() - 50, mainPanel.height() - 200, 1, 5),
                    new Color(45, 45, 45, 220)
            );
            gridContainer.setLayoutStrategy(new GridLayout(4, 10, 10)); // 4 columns, 10px gaps

            // Create the top toolbar for circular sprite buttons
            topToolbar = new GuiContainer(
                    new RoundRect(10, 50, mainPanel.width() - 20, 80, 1, 5),
                    new Color(0, 0, 0, 0) // Transparent background
            );
            topToolbar.setLayoutStrategy(new FlexLayout(
                    Style.JustifyContent.flexStart,
                    Style.Align.center,
                    15 // Gap between items
            ));

            // Create the bottom toolbar
            bottomToolbar = new GuiContainer(
                    new RoundRect(screenWidth * 0.2f, screenHeight * 0.85f, screenWidth * 0.4f, 50, 1, 20),
                    new Color(33, 33, 33, 200)
            );
            bottomToolbar.setLayoutStrategy(new FlexLayout(
                    Style.JustifyContent.center,
                    Style.Align.center,
                    10
            ));

            // Create the scroll container
            scrollContainer = new GuiContainer(
                    new RoundRect(mainPanel.width() - 30, 150, 20, mainPanel.height() - 200, 1, 10),
                    new Color(60, 60, 60, 180)
            );

            // Create grid buttons (4x5 = 20 buttons)
            for (int i = 0; i < 20; i++) {
                final GuiShape gridButton = new GuiShape(
                        new RoundRect(0, 0, 80, 80, 1, 15),
                        new Color(200, 80, 80, 255) // Red buttons
                );
                gridContainer.addChild(gridButton);
            }

            // Create top toolbar circular buttons (5 buttons)
            for (int i = 0; i < 5; i++) {
                final GuiSprite circleButton = new GuiSprite(
                        new RoundRect(0, 0, 60, 60, 1, 30), // High radius for circular appearance
                        "backpack", // Replace with appropriate icons
                        64,
                        Color.WHITE
                );
                topToolbar.addChild(circleButton);
            }

            // Create bottom toolbar buttons (5 buttons)
            for (int i = 0; i < 5; i++) {
                final GuiShape toolbarButton = new GuiShape(
                        new RoundRect(0, 0, 60, 60, 1, 15),
                        new Color(200, 80, 80, 255)
                );
                bottomToolbar.addChild(toolbarButton);
            }

            // Create scrollbar
            final GuiShape scrollBar = new GuiShape(
                    new RoundRect(2, 20, 16, 150, 1, 8),
                    new Color(180, 100, 255, 255) // Purple scrollbar
            );
            scrollContainer.addChild(scrollBar);

            // Add all components to the main container
            mainPanel.addChild(gridContainer);
            mainPanel.addChild(topToolbar);
            mainPanel.addChild(scrollContainer);

            // Set main panel and bottom toolbar as root containers
            // We'll manage them separately since they're not nested
            UIManager.setRootContainer(mainPanel);

            setup = true;
        }

        if (player != null) {
            // Get mouse position and buttons if cursor is visible
            if (UIManager.isCursorVisible()) {
                float[] cursorPos = UIManager.getMousePos();
                boolean[] buttons = UIManager.getMouseButtons();

                // Create and render the mouse cursor
                final GuiSprite mouseCursor = new GuiSprite(
                        new Rect(9, 17, 1),
                        "mouse_cursor",
                        32,
                        Color.WHITE
                );
                mouseCursor.rect.setUL(cursorPos);
                mouseCursor.render();
            }

            // Render the main UI components
            UIManager.renderUI();

            // Render the bottom toolbar separately
            bottomToolbar.render();
        }
    };
}