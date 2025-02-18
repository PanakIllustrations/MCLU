package com.tumult.mclu.client.gui.frame.old;

import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

import java.util.List;

public class EventHandler extends Rect {

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

    public EventHandler(Rect rect) {
        super(rect);
    }

    private State currentState = State.IDLE;
    private ClickDuration clickDuration = ClickDuration.NONE;
    private long clickStartTime;

    public void update(float[] mousePosition, List<Integer> mouseButtons) {
        updateState(mousePosition, mouseButtons);
        handleState();
    }

    private void updateState(float[] mousePosition, List<Integer> mouseButtons) {
        if (this.contains(mousePosition)) {
            if (mouseButtons.contains(1)) {
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
