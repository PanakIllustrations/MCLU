package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.DrawableSprite;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

/**
 * A button that toggles the visibility of a target Node (independent, not a child)
 * Automatically swaps between open and closed sprites based on target state
 */
public class ToggleButton extends InteractiveSprite {

    private Node target;
    private DrawableSprite openSprite;
    private DrawableSprite closedSprite;

    public ToggleButton(ResourceLocation texture, Vector4DRect rect, Vector4DRect uv, float z) {
        super(texture, rect, uv, z);
    }

    public ToggleButton(DrawableSprite openSprite, DrawableSprite closedSprite) {
        // Copy the bounds instead of sharing the reference
        super(openSprite.getTexture(),
                openSprite.getBounds().copy(),  // Create a copy!
                openSprite.getTextureUV(),
                openSprite.getzLevel());
        this.openSprite = openSprite;
        this.closedSprite = closedSprite;
    }

    public void setStateSprites(DrawableSprite openSprite, DrawableSprite closedSprite) {
        this.openSprite = openSprite;
        this.closedSprite = closedSprite;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public Node getTarget() {
        return target;
    }

    public boolean isTargetVisible() {
        return target != null && target.isVisible;
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        if (!isVisible) return;

        // Use different sprite based on target visibility
        if (openSprite != null && closedSprite != null) {
            DrawableSprite currentSprite = isTargetVisible() ? openSprite : closedSprite;

            // Sync position with this button's position
            currentSprite.setUL(new Vector2DPoint(rectBounds.left(), rectBounds.top()));
            currentSprite.draw(guiGraphics);
        } else {
            // Fallback to normal draw if sprites not set
            super.draw(guiGraphics);
        }
    }

    @Override
    public void onMouseEnter() {
        System.out.println("Toggle button hovered - Target is " +
                (isTargetVisible() ? "VISIBLE" : "HIDDEN"));
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
        if (target != null) {
            target.toggleVisibility();

            boolean nowVisible = target.isVisible;
            System.out.println("Toggled - Target now " + (nowVisible ? "VISIBLE" : "HIDDEN"));

            // Different sound pitch for open/close
            float pitch = nowVisible ? 1.2F : 0.8F;
            Minecraft.getInstance().getSoundManager().play(
                    SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, pitch)
            );
        } else {
            System.out.println("Toggle button has no target!");
        }
    }
}