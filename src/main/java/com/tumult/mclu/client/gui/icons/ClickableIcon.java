package com.tumult.mclu.client.gui.icons;

import com.mojang.blaze3d.platform.InputConstants;
import com.tumult.mclu.client.gui.geometry.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

import java.util.Map;

public class ClickableIcon extends DrawableIcon {
    private boolean isActive = true;
    private boolean isVisible = true;

    private final Map<Integer, Runnable> onPressedActions;
    private final Map<Integer, Runnable> onReleasedActions;

    public ClickableIcon(DrawableIcon icon, Map<Integer, Runnable> onPressedActions, Map<Integer, Runnable> onReleasedActions) {
        super(icon);
        this.onPressedActions = onPressedActions;
        this.onReleasedActions = onReleasedActions;
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

    public void mouseDownEvent(int button) {
        this.clickSound(Minecraft.getInstance().getSoundManager());
        handleMouseEvent(button, true);
    }
    public void mouseUpEvent(int button) {
        handleMouseEvent(button, false);
    }

    protected void handleMouseEvent(int button, boolean isPress) {
        if (!isActive() || !isVisible() || !containsPoint()) {
            return;
        }
        InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
        boolean isPickItem = Minecraft.getInstance().options.keyPickItem.isActiveAndMatches(mouseKey);
        if (button == 0 || button == 1 || isPickItem) {
            if (isPress) {
                Runnable action = onPressedActions.get(button);
                if (action != null) {
                    action.run();
                }
            } else {
                Runnable action = onReleasedActions.get(button);
                if (action != null) {
                    action.run();
                }
            }
        }
    }

    protected void clickSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    private boolean containsPoint() {
        if (isActive) {
            double mouseX = GuiCursor.clampedMousePos.x;
            double mouseY = GuiCursor.clampedMousePos.y;
            return mouseX > getLeft() && mouseX < getRight() && mouseY > getTop() && mouseY < getBottom();
        }
        return false;
    }
}
