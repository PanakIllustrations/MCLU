package com.tumult.mclu.client.gui.frame;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface  IClickableFrame {
    boolean isActive();
    boolean isVisible();

    void onPressed();
    void onReleased();

    double top();
    double bottom();
    double left();
    double right();

    void setOnMouseDown(IMouseDown onPressed);
    void setOnMouseUp(IMouseUp onReleased);

    IClickableFrame setTop(double value);
    IClickableFrame setBottom(double value);
    IClickableFrame setLeft(double value);
    IClickableFrame setRight(double value);

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

    default boolean contains(double x, double y) {
        return x > left() && x < right() && y > top() && y < bottom();
    }

    default void clickSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    default boolean mouseDownEvent(double mouseX, double mouseY, int button) {
        this.clickSound(Minecraft.getInstance().getSoundManager());
        return handleMouseEvent(mouseX, mouseY, button, this::onPressed);
    }

    default boolean mouseUpEvent(double mouseX, double mouseY, int button) {
        return handleMouseEvent(mouseX, mouseY, button, this::onReleased);
    }

    @OnlyIn(Dist.CLIENT)
    interface IMouseUp {
        void onMouseUp(IClickableFrame doThis);
    }
    @OnlyIn(Dist.CLIENT)
    interface IMouseDown {
        void onMouseDown(IClickableFrame doThis);
    }
}
