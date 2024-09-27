package com.tumult.mclu.client.gui.frame;

import com.tumult.mclu.client.gui.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import org.jetbrains.annotations.NotNull;

public  class AbstractWidget extends Vector4DRect implements Renderable, GuiEventListener  {
    public boolean active = true;
    public boolean visible = true;

    public AbstractWidget(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public void onClick(double x, double y) {
    }
    public void onRelease(double x, double y) {
    }
    public void onDrag(double x, double y) {
    }
    public boolean isHovered(double x, double y) {
        return this.visible && this.contains(x,y);
    }
    public boolean mouseClicked(double x, double y, int button) {
        if (isHovered(x, y)) {

        }
    }
    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int screenX, int screenY, float zLevel) {
    }
    @Override
    public void setFocused(boolean focused) {
        this.visible = focused;
    }
    @Override
    public boolean isFocused() {
        return false;
    }
    public void playDownSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }


}
