package com.tumult.mclu.client.gui.frame;

import com.mojang.blaze3d.platform.InputConstants;
import com.tumult.mclu.client.gui.geometry.Rectangle;
import com.tumult.mclu.client.gui.geometry.Vector2DPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

public class ClickableIcon extends Rectangle {
    boolean isActive = true;
    boolean isVisible = true;

    public ClickableIcon(Vector2DPoint nwCorner, Vector2DPoint seCorner) {
        super(nwCorner, seCorner);
    }

    public ClickableIcon(double left, double top, double right, double bottom) {
        super(left, top, right, bottom);
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean containsPoint(double x, double y) {
        return x > getLeft() && x < getRight() && y > getTop() && y < getBottom();
    }

    public void mouseDownEvent(double mouseX, double mouseY, int button) {
        this.clickSound(Minecraft.getInstance().getSoundManager());
        handleMouseEvent(mouseX, mouseY, button, this::onPressed);
    }

    public void mouseUpEvent(double mouseX, double mouseY, int button) {
        handleMouseEvent(mouseX, mouseY, button, this::onReleased);
    }

    protected boolean handleMouseEvent(double mouseX, double mouseY, int button, Runnable onPressOrRelease) {
        if (!isActive() || !isVisible() || !containsPoint(mouseX, mouseY))
            return false;
        InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
        boolean flag = Minecraft.getInstance().options.keyPickItem.isActiveAndMatches(mouseKey);
        System.out.println("Mouse key matched keyPickItem: " + flag);
        if (button == 0 || button == 1 || flag) {
            onPressOrRelease.run();
            return true;
        }
        return false;
    }

    protected void clickSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    protected void onPressed() {
        // Handle the mouse press event
        System.out.println("Mouse button pressed!");
    }

    protected void onReleased() {
        // Handle the mouse release event
        System.out.println("Mouse button released!");
    }
}
