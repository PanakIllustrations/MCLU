package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.DrawableSprite;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class InteractiveSprite extends DrawableSprite implements IInteractive {

    protected enum InteractiveState {
        IDLE,
        HOVERED,
        PRESSED
    }

    protected InteractiveState currentState = InteractiveState.IDLE;
    protected InteractiveState previousState = InteractiveState.IDLE;

    public InteractiveSprite(ResourceLocation texture, Vector4DRect rect, Vector4DRect uv, float z) {
        super(texture, rect, uv, z);
    }

    @Override
    public void update(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        previousState = currentState;
        updateState(mousePosition, mouseButtons);
        handleTransitions();
    }

    protected void updateState(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        boolean isOver = rectBounds.contains(mousePosition);
        boolean isPressed = mouseButtons.contains(UIManager.LEFT_BUTTON);

        if (isOver && isPressed) {
            currentState = InteractiveState.PRESSED;
        } else if (isOver) {
            currentState = InteractiveState.HOVERED;
        } else {
            currentState = InteractiveState.IDLE;
        }
    }

    protected void handleTransitions() {
        // Mouse entered
        if (currentState == InteractiveState.HOVERED && previousState == InteractiveState.IDLE) {
            onMouseEnter();
        }

        // Mouse left
        if (currentState == InteractiveState.IDLE && previousState != InteractiveState.IDLE) {
            onMouseLeave();
        }

        // Mouse pressed
        if (currentState == InteractiveState.PRESSED && previousState != InteractiveState.PRESSED) {
            onMouseDown();
        }

        // Mouse released
        if (previousState == InteractiveState.PRESSED && currentState != InteractiveState.PRESSED) {
            onMouseUp();

            // Click completed (released over same element)
            if (currentState == InteractiveState.HOVERED) {
                onClick();
            }
        }

        // Hovering
        if (currentState == InteractiveState.HOVERED && previousState == InteractiveState.HOVERED) {
            onMouseHover();
        }
    }
}