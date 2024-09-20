package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.client.gui.geometry.Rectangle;
import com.tumult.mclu.client.gui.frame.ClickableFrame;
import net.minecraft.client.gui.GuiGraphics;

public class ClickableIcon {
    /*private final GuiIcon guiIcon;
    private boolean contains = false;

    public ClickableIcon(double left, double top, double right, double bottom, GuiIcon guiIcon) {
        super(left, top, right, bottom);
        this.guiIcon = guiIcon;
    }

    public void render(GuiGraphics guiGraphics, double mouseX, double mouseY, float zLevel) {
        if (!isVisible()) return;
        guiIcon.draw(guiGraphics, this.getLeft(), this.getTop(), zLevel);
        if (this.containsPoint(mouseX, mouseY) && isActive()) {
            renderHoverEffect(guiGraphics);
            contains = true;
        } else {
            contains = false;
        }
    }

    public void renderHoverEffect(GuiGraphics guiGraphics) {
        System.out.println("Hover effect");
    }

    @Override
    public void mouseDownEvent(double mouseX, double mouseY, int button) {
        if (contains) {
            super.mouseDownEvent(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseUpEvent(double mouseX, double mouseY, int button) {
        if (contains) {
            super.mouseUpEvent(mouseX, mouseY, button);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
    }*/
}
