package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import com.tumult.mclu.client.gui.frame.core.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;

import java.awt.*;
import java.util.List;

public class EventHandler extends DrawableRect {

    private enum State {
        IDLE,              // Button is not being interacted with
        HOVERED,           // Mouse is hovering over the component
        PRESSED,           // Mouse button pressed on component
        HOLDING,           // Mouse button held down for duration (inside bounds)
        PRESSED_OUTSIDE,   // Mouse pressed inside, now outside bounds
        HOLDING_OUTSIDE    // Mouse held inside, now outside bounds
    }

    public EventHandler(Color color, Vector4DRect rect, float radius) {
        super(color, rect, radius);
    }

    private State currentState = State.IDLE;
    private State previousState = State.IDLE;
    private long pressStartTime = 0;
    private boolean clickFired = false;
    private boolean holdFired = false;
    private boolean pressedInside = false; // Track if press started inside

    // Configurable delay in milliseconds before onMouseHeld triggers
    private long holdDelayMs = 500; // Default: 500ms

    public void setHoldDelay(long delayMs) {
        this.holdDelayMs = delayMs;
    }

    public long getHoldDelay() {
        return this.holdDelayMs;
    }

    public void update(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        previousState = currentState;
        updateState(mousePosition, mouseButtons);
        handleStateTransitions();
    }

    private void updateState(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        boolean isMouseOver = this.rectBounds.contains(mousePosition);
        boolean isPressed = mouseButtons.contains(UIManager.LEFT_BUTTON);

        if (isPressed) {
            // If button is pressed, check if we initially pressed inside
            if (currentState == State.IDLE || currentState == State.HOVERED) {
                // New press
                if (isMouseOver) {
                    pressedInside = true;
                    currentState = State.PRESSED;
                } else {
                    pressedInside = false;
                    currentState = State.IDLE;
                }
            } else if (pressedInside) {
                // Continue existing press that started inside
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                boolean shouldHold = pressDuration >= holdDelayMs;

                if (isMouseOver) {
                    // Mouse is back inside or still inside
                    currentState = shouldHold ? State.HOLDING : State.PRESSED;
                } else {
                    // Mouse dragged outside
                    currentState = shouldHold ? State.HOLDING_OUTSIDE : State.PRESSED_OUTSIDE;
                }
            }
        } else {
            // Button released
            pressedInside = false;
            if (isMouseOver) {
                currentState = State.HOVERED;
            } else {
                currentState = State.IDLE;
            }
        }
    }

    private void handleStateTransitions() {
        // Detect initial press (transition into PRESSED state)
        if (currentState == State.PRESSED && previousState != State.PRESSED && previousState != State.PRESSED_OUTSIDE) {
            pressStartTime = System.currentTimeMillis();
            clickFired = false;
            holdFired = false;
            onMouseDown();
            clickFired = true;
        }

        // Detect transition into HOLDING state
        if (currentState == State.HOLDING && (previousState == State.PRESSED || previousState == State.PRESSED_OUTSIDE)) {
            if (!holdFired) {
                onMouseHeld();
                holdFired = true;
            }
        }

        // Continue holding (inside or outside)
        if ((currentState == State.HOLDING || currentState == State.HOLDING_OUTSIDE) &&
                (previousState == State.HOLDING || previousState == State.HOLDING_OUTSIDE)) {
            onMouseHeld(); // Call continuously while holding
        }

        // Detect mouse entering hover state
        if (currentState == State.HOVERED && previousState == State.IDLE) {
            onMouseHover();
        }

        // Detect mouse leaving component (but not if we're pressing/holding)
        if (currentState == State.IDLE &&
                previousState != State.IDLE &&
                previousState != State.PRESSED_OUTSIDE &&
                previousState != State.HOLDING_OUTSIDE) {
            onMouseLeave();
        }

        // Detect mouse button release (from any pressed/holding state)
        if ((previousState == State.PRESSED || previousState == State.HOLDING ||
                previousState == State.PRESSED_OUTSIDE || previousState == State.HOLDING_OUTSIDE) &&
                (currentState == State.HOVERED || currentState == State.IDLE)) {
            onMouseRelease();
        }

        // Detect mouse re-entering bounds while pressed
        if ((currentState == State.PRESSED || currentState == State.HOLDING) &&
                (previousState == State.PRESSED_OUTSIDE || previousState == State.HOLDING_OUTSIDE)) {
            onMouseReenter();
        }

        // Detect mouse leaving bounds while pressed
        if ((currentState == State.PRESSED_OUTSIDE || currentState == State.HOLDING_OUTSIDE) &&
                (previousState == State.PRESSED || previousState == State.HOLDING)) {
            onMouseLeaveWhilePressed();
        }
    }

    private void clickSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    protected void onMouseHover() {
        System.out.println("Mouse hovered over component!");
    }

    protected void onMouseLeave() {
        System.out.println("Mouse cursor left the component!");
    }

    protected void onMouseDown() {
        System.out.println("Mouse clicked on component!");
        clickSound(Minecraft.getInstance().getSoundManager());
        this.rectBounds.startDrag(UIManager.getMousePos());

        for (Node child : children) {
            child.getBounds().startDrag(UIManager.getMousePos());
        }
    }

    protected void onMouseHeld() {
        System.out.println("Mouse held on component!");
        this.rectBounds.drag(UIManager.getMousePos());
        for (Node child : children) {
            child.getBounds().drag(UIManager.getMousePos());
        }
    }

    protected void onMouseRelease() {
        System.out.println("Mouse button released!");
        this.rectBounds.endDrag();
        this.debugPrinted = false;
        for (Node child : children) {
            child.getBounds().endDrag();
        }
    }

    protected void onMouseLeaveWhilePressed() {
        System.out.println("Mouse left bounds while pressed!");
    }

    protected void onMouseReenter() {
        System.out.println("Mouse re-entered bounds while pressed!");
    }
}