package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import com.tumult.mclu.client.gui.frame.core.UIManager;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

import java.awt.*;
import java.util.List;

public class EventHandler extends DrawableRect {

    private enum State {
        IDLE,     // Button is not being interacted with
        HOVERED,  // Mouse is hovering over the component
        CLICKED   // Button is being clicked
    }

    private enum ClickDuration {
        NONE,     // No click
        SHORT,    // click
        LONG      // click and hold
    }

    public EventHandler(Color color, Vector4DRect rect, float radius ) {
        super(color, rect, radius);
    }

    private State currentState = State.IDLE;
    private ClickDuration clickDuration = ClickDuration.NONE;
    private long clickStartTime;

    public void update(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        updateState(mousePosition, mouseButtons);
        handleState();
    }

    private void updateState(Vector2DPoint mousePosition,  List<Integer> mouseButtons) {
        if (this.rectBounds.contains(mousePosition)) {
            if (mouseButtons.contains(UIManager.LEFT_BUTTON)) {
                this.currentState = State.CLICKED;
            } else {
                this.currentState = State.HOVERED;
            }
        }
    }
    private void handleState() {
        switch (currentState) {
            case HOVERED:
                onMouseHover();
                break;

            case CLICKED:
                onMouseDown();
                break;

            default:
                break;
        }
    }

    private void clickSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    protected void onMouseHover() { System.out.println("Mouse hovered over component!"); }
    protected void onMouseLeave() { System.out.println("Mouse cursor left the component!"); }
    protected void onMouseDown() { System.out.println("Mouse clicked on component!"); }
    protected void onMouseHeld() { System.out.println("Mouse held on component!"); }
    protected void onMouseRelease() { System.out.println("Mouse button released!"); }

}
