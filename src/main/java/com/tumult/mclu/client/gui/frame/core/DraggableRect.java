package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;

import java.awt.*;
import java.util.List;


public class DraggableRect extends InteractiveRect {

    protected enum DragState {
        IDLE,
        HOVERED,
        PRESSED,
        HOLDING,
        PRESSED_OUTSIDE,
        HOLDING_OUTSIDE
    }

    private DragState dragState = DragState.IDLE;
    private DragState previousDragState = DragState.IDLE;
    private long pressStartTime = 0;
    private boolean clickFired = false;
    private boolean holdFired = false;
    private boolean pressedInside = false;
    private long holdDelayMs = 500; // Default 500ms

    public DraggableRect(Color color, Vector4DRect rect, float radius) {
        super(color, rect, radius);
    }

    public void setHoldDelay(long delayMs) {
        this.holdDelayMs = delayMs;
    }

    public long getHoldDelay() {
        return holdDelayMs;
    }

    @Override
    public void update(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        // Check if any child is capturing input
        boolean childCapturing = children.stream()
                .filter(child -> child instanceof IInteractive)
                .anyMatch(child -> ((IInteractive) child).isCapturingInput());

        if (childCapturing) {
            // Don't update our drag state if a child is capturing
            return;
        }

        previousDragState = dragState;
        updateDragState(mousePosition, mouseButtons);
        handleDragTransitions();
    }

    private void updateDragState(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        boolean isMouseOver = rectBounds.contains(mousePosition);
        boolean isPressed = mouseButtons.contains(UIManager.LEFT_BUTTON);

        if (isPressed) {
            if (dragState == DragState.IDLE || dragState == DragState.HOVERED) {
                if (isMouseOver) {
                    pressedInside = true;
                    dragState = DragState.PRESSED;
                } else {
                    pressedInside = false;
                    dragState = DragState.IDLE;
                }
            } else if (pressedInside) {
                long pressDuration = System.currentTimeMillis() - pressStartTime;
                boolean shouldHold = pressDuration >= holdDelayMs;

                if (isMouseOver) {
                    dragState = shouldHold ? DragState.HOLDING : DragState.PRESSED;
                } else {
                    dragState = shouldHold ? DragState.HOLDING_OUTSIDE : DragState.PRESSED_OUTSIDE;
                }
            }
        } else {
            pressedInside = false;
            dragState = isMouseOver ? DragState.HOVERED : DragState.IDLE;
        }
    }

    private void handleDragTransitions() {
        // Initial press
        if (dragState == DragState.PRESSED &&
                previousDragState != DragState.PRESSED &&
                previousDragState != DragState.PRESSED_OUTSIDE) {
            pressStartTime = System.currentTimeMillis();
            clickFired = false;
            holdFired = false;
            onMouseDown();
            clickFired = true;
        }

        // Transition to holding
        if (dragState == DragState.HOLDING &&
                (previousDragState == DragState.PRESSED || previousDragState == DragState.PRESSED_OUTSIDE)) {
            if (!holdFired) {
                onMouseHeld();
                holdFired = true;
            }
        }

        // Continue holding
        if ((dragState == DragState.HOLDING || dragState == DragState.HOLDING_OUTSIDE) &&
                (previousDragState == DragState.HOLDING || previousDragState == DragState.HOLDING_OUTSIDE)) {
            onMouseHeld();
        }

        // Mouse entered
        if (dragState == DragState.HOVERED && previousDragState == DragState.IDLE) {
            onMouseEnter();
        }

        // Mouse left (but not if pressing/holding)
        if (dragState == DragState.IDLE &&
                previousDragState != DragState.IDLE &&
                previousDragState != DragState.PRESSED_OUTSIDE &&
                previousDragState != DragState.HOLDING_OUTSIDE) {
            onMouseLeave();
        }

        // Mouse released
        if ((previousDragState == DragState.PRESSED || previousDragState == DragState.HOLDING ||
                previousDragState == DragState.PRESSED_OUTSIDE || previousDragState == DragState.HOLDING_OUTSIDE) &&
                (dragState == DragState.HOVERED || dragState == DragState.IDLE)) {
            onMouseUp();
        }

        // Re-enter while pressed
        if ((dragState == DragState.PRESSED || dragState == DragState.HOLDING) &&
                (previousDragState == DragState.PRESSED_OUTSIDE || previousDragState == DragState.HOLDING_OUTSIDE)) {
            onMouseReenter();
        }

        // Leave while pressed
        if ((dragState == DragState.PRESSED_OUTSIDE || dragState == DragState.HOLDING_OUTSIDE) &&
                (previousDragState == DragState.PRESSED || previousDragState == DragState.HOLDING)) {
            onMouseLeaveWhilePressed();
        }
    }

    @Override
    public void onMouseDown() {
        super.onMouseDown();
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
        );
        rectBounds.startDrag(UIManager.getMousePos());

        // Start drag for all children
        for (Node child : children) {
            child.getBounds().startDrag(UIManager.getMousePos());
        }
    }

    @Override
    public void onMouseUp() {
        super.onMouseUp();
        rectBounds.endDrag();
        debugPrinted = false;

        // End drag for all children
        for (Node child : children) {
            child.getBounds().endDrag();
        }
    }

    protected void onMouseHeld() {
        rectBounds.drag(UIManager.getMousePos());

        // Drag all children
        for (Node child : children) {
            child.getBounds().drag(UIManager.getMousePos());
        }
    }

    protected void onMouseLeaveWhilePressed() {
        System.out.println("Mouse left bounds while pressed");
    }

    protected void onMouseReenter() {
        System.out.println("Mouse re-entered bounds while pressed");
    }
}