package com.tumult.mclu.client.gui.frame;

import com.mojang.blaze3d.platform.InputConstants;
import com.tumult.mclu.client.gui.geometry.IRectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IMouseEventFrame extends IRectangle {

    boolean isActive();
    boolean isVisible();

    void onPressed();
    void onReleased();
    void setOnMouseDown(IMouseDown onPressed);
    void setOnMouseUp(IMouseUp onReleased);

    default boolean mouseDownEvent(double mouseX, double mouseY, int button) {
        this.clickSound(Minecraft.getInstance().getSoundManager());
        return handleMouseEvent(mouseX, mouseY, button, this::onPressed);
    }

    default boolean mouseUpEvent(double mouseX, double mouseY, int button) {
        return handleMouseEvent(mouseX, mouseY, button, this::onReleased);
    }

    default boolean handleMouseEvent(double mouseX, double mouseY, int button, Runnable onPressOrRelease) {
        if (!isActive() || !isVisible() || !contains(mouseX, mouseY))
            return false;
        InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
        boolean flag = Minecraft.getInstance().options.keyPickItem.isActiveAndMatches(mouseKey);
        if (button == 0 || button == 1 || flag) {
            onPressOrRelease.run();
            return true;
        }
        return false;
    }

    default void clickSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    @OnlyIn(Dist.CLIENT)
    interface IMouseUp {
        void onMouseUp(IMouseEventFrame doThis);
    }
    @OnlyIn(Dist.CLIENT)
    interface IMouseDown {
        void onMouseDown(IMouseEventFrame doThis);
    }
}
