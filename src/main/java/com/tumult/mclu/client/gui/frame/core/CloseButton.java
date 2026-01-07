package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;


public class CloseButton extends InteractiveSprite {

    public CloseButton(ResourceLocation texture, Vector4DRect rect, Vector4DRect uv, float z) {
        super(texture, rect, uv, z);
    }

    @Override
    public boolean isCapturingInput() {
        return currentState == InteractiveState.PRESSED;
    }

    @Override
    public void onMouseDown() {
        super.onMouseDown();
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
        );
    }

    @Override
    public void onClick() {
        super.onClick();

        // Toggle parent visibility
        if (parent != null) {
            parent.toggleVisibility();
            System.out.println("Parent visibility toggled to: " + parent.isVisible);
        }
    }
}