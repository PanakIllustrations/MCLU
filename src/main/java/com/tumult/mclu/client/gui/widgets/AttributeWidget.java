package com.tumult.mclu.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import com.tumult.mclu.client.gui.widgets.MCLUWidget;
import net.minecraft.resources.ResourceLocation;

public abstract class AttributeWidget extends MCLUWidget {
    private int maxValue;
    private int currentValue;
    private int previousValue;

    private static final ResourceLocation MOD_ICONS = new ResourceLocation("minecraft", "textures/gui/mod_icons.png");

    public AttributeWidget(int x, int y, int width, int height, int maxValue) {
        super(x,y,width,height, MOD_ICONS);
        this.maxValue = maxValue;
        this.currentValue = maxValue;
        this.previousValue = maxValue;
    }

    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }

    public int getCurrentValue() { return currentValue; }
    public void setCurrentValue(int currentValue) {
        this.previousValue = this.currentValue;
        this.currentValue = currentValue;
    }

    public int getPreviousValue() { return previousValue; }

    protected void updateState() {
        if (currentValue > previousValue) {
            // value increased
        } else if (currentValue < previousValue) {
            // value decreased
        } else {
            // value remained the same
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        updateState();

        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        guiGraphics.blit(MOD_ICONS, this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}
