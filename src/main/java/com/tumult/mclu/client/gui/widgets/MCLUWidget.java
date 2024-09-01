package com.tumult.mclu.client.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class MCLUWidget extends AbstractWidget {
    private ResourceLocation resourceLocation;

    private int x;
    private int y;
    private final int width;
    private final int height;

    private boolean isDragging = false;
    private int dragOffsetX;
    private int dragOffsetY;

    public MCLUWidget(final int x, final int y, final int width, final int height, ResourceLocation resourceLocation) {
        super(x,y,width,height, Component.empty());
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        if (isDragging) {
            this.x = mouseX - dragOffsetX;
            this.y = mouseY - dragOffsetY;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isClicked((int) mouseX,(int) mouseY, button == 0)) {
            isDragging = true;
            dragOffsetX = (int) mouseX - this.x;
            dragOffsetY = (int) mouseY - this.y;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            isDragging = false;
            return true;
        }
        return false;
    }

    protected boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width
            && mouseY >= this.y && mouseY < this.y + this.height;
    }

    protected boolean isClicked(int mouseX, int mouseY, boolean button) {
        return button
            && mouseX >= this.x && mouseX < this.x + this.width
            && mouseY >= this.y && mouseY < this.y + this.height;
    }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height;}
}
