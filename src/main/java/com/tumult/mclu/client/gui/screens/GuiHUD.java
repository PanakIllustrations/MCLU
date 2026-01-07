package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.core.DrawableSprite;
import com.tumult.mclu.client.gui.frame.core.EventHandler;
import com.tumult.mclu.client.gui.frame.core.UIManager;
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
    static final EventHandler rect = new EventHandler(Color.ORANGE, new Vector4DRect(30, 30, 50, 100), 5);
    //static final DrawableSprite backpack = IconUtils.getIcon().backpack;
    static final DrawableSprite bankCross = IconUtils.getIcon().bankCross;
    static final DrawableSprite bankResize = IconUtils.getIcon().bankResize;

    GuiHUD(){
        bankCross.setParent(rect);
        bankResize.setParent(rect);
    }

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        UIManager.init(screenWidth, screenHeight);

        if (player != null) {
            rect.drawTree(guiGraphics);
            if (UIManager.isCursorVisible()) {
                Vector2DPoint cursorPos = new Vector2DPoint(UIManager.getMousePos());
                cursor.draw(guiGraphics,cursorPos);
                rect.update(cursorPos, getMouseButtons());
            }
            //backpack.draw(guiGraphics, new Vector2DPoint(30, 10));
        }
    };
}
/*
blit(ResourceLocation location, int screenPosX, int screenPosY, int zLevel, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconPosX, int iconPosY, int iconWidth, int iconHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconWidth1, int iconHeight1, float iconPosX, float iconPosY, int iconWidth2, int iconHeight2, int textureWidth, int textureHeight);
blit(ResourceLocation location, int left, int right, int top, int bottom, int zLevel, int iconWidth2, int iconHeight2, float iconPosX, float iconPosY, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
*/