package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIElement extends DrawableRect {
    protected List<UIElement> children = new ArrayList<>();

    // State flags
    protected boolean isHidden, isDisabled = false;
    protected boolean isResizable, isMovable = false;

    // interaction tracking
    private int tickCounter;
    private final int tickThreshold = 5;
    private double initialOffsetX;
    private double initialOffsetY;
    private double initialWidth;
    private double initialHeight;
    private final float cornerProximityThreshold = 5.0f;

    // Visual configurations
    protected Color color = Color.WHITE;

    protected enum State {
        IDLE, HOVERED, GRABBED, DRAGGING, SCALING
    }

    private State currentState = State.IDLE;
    private State previousState = State.IDLE;

    protected boolean isGrabbed = false;
    protected boolean wasGrabbed = false;

    public UIElement( ResourceLocation resource, Vector4DRect bounds, Vector4DRect uvMap) {
        super(resource, bounds, uvMap);
    }

    public UIElement(Vector4DRect bounds, Color color, float radius) {
        super(bounds, color, radius);
    }

    public void setFlagState(boolean isDisabled, boolean isHidden, boolean isResizable, boolean isMovable){
        this.setDisabled(isDisabled);
        this.setHidden(isHidden);
        this.isResizable = isResizable;
        this.isMovable = isMovable;
    }
    
    public void addChild(UIElement child) {
        children.add(child);
    }
    public void removeChild(UIElement child) {
        children.remove(child);
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
        for (UIElement child : children) {
            child.setHidden(hidden);
        }
    }
    public void setDisabled(boolean hidden) {
        isHidden = hidden;
        for (UIElement child : children) {
            child.setHidden(hidden);
        }
    }
    public void toggleHidden() {
        isHidden = !isHidden;
    }

    public void update(Vector2DPoint position, List<Integer> buttons) {
        if (isHidden || isDisabled) return;
        State newState = determineState(position, buttons);
        handleStateTransition(newState, position);
        previousState = currentState;
        currentState = newState;
    }
    private State determineState(Vector2DPoint position, List<Integer> pressedButtons) {
        boolean isHovered = bounds.contains(position);
        boolean isNearCorner = isNearBottomRightCorner(position);
        boolean isGrabButtonPressed = pressedButtons.contains(1); // Assuming 1 is the left mouse button

        if (isGrabButtonPressed) {
            if (isNearCorner) {
                return State.SCALING;
            } else if (isHovered) {
                return State.GRABBED;
            }
        } else if (isHovered) {
            return State.HOVERED;
        } else if (currentState == State.GRABBED || currentState == State.SCALING) {
            return State.IDLE;
        }

        return State.IDLE;
    }
    private void handleStateTransition(State newState, Vector2DPoint position) {
        // Only handle state transition if there's an actual change in state
        if (newState != currentState) {
            // Handle exit actions for the current state
            switch (currentState) {
                case IDLE:
                    if (newState == State.HOVERED) {
                        onMouseHover();
                    }
                    break;

                case HOVERED:
                    if (newState == State.IDLE) {
                        onMouseLeave();
                    }
                    break;

                case GRABBED:
                    if (newState == State.IDLE) {
                        onMouseUp();
                    }
                    break;
            }

            // Handle entry actions for the new state
            switch (newState) {
                case HOVERED:
                    if (currentState == State.IDLE) {
                        onMouseHover();
                    }
                    break;

                case GRABBED:
                    if (currentState == State.HOVERED) {
                        onMouseDown(position);
                    }
                    break;

                case SCALING:
                    if (currentState == State.HOVERED || currentState == State.GRABBED) {
                        onResizingStart(position);
                    }
                    break;

                case DRAGGING:
                    if (currentState == State.GRABBED) {
                        onDragging();
                    }
                    break;

                case IDLE:
                    if (currentState == State.SCALING || currentState == State.DRAGGING || currentState == State.GRABBED) {
                        onMouseUp();
                    }
                    break;
            }
        }

        // Update state tracking variables
        previousState = currentState;
        currentState = newState;

        // Handle state-specific updates outside of transition (e.g., scaling or dragging operations)
        if (currentState == State.DRAGGING) {
            handleDrag(position);
        } else if (currentState == State.SCALING) {
            handleScaling(position);
        }
    }

    private boolean isNearBottomRightCorner(Vector2DPoint position) {
        Vector2DPoint bottomRight = bounds.getBr(); // Get the BR corner position of the bounds
        return bottomRight.isNear(position, cornerProximityThreshold);
    }
    private void handleScaling(Vector2DPoint position) {
        double newWidth = initialWidth + (position.x - initialOffsetX);
        double newHeight = initialHeight + (position.y - initialOffsetY);

        // Update the bounds to apply the new width and height
        bounds.setWh(newWidth, newHeight);
        onResizing();
    }
    private void handleDrag(Vector2DPoint position) {
        double offsetX = position.x - initialOffsetX;
        double offsetY = position.y - initialOffsetY;

        bounds.setUl(offsetX, offsetY);
        onDragging();
    }

    protected void clickSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
    protected void onMouseHover() { System.out.println("Component is hovered!"); }
    protected void onMouseLeave() { System.out.println("Mouse cursor left the component!"); }
    protected void onMouseDown(Vector2DPoint position) {
        initialOffsetX = position.x - bounds.left();
        initialOffsetY = position.y - bounds.top();
        System.out.println("Mouse button pressed!"); }
    protected void onMouseUp() { System.out.println("Mouse button released!"); }
    protected void onDragging() { System.out.println("Component is dragging!"); }
    protected void onCornerHover() { System.out.println("Component corner is hovered!"); }
    protected void onResizingStart(Vector2DPoint position) {
        initialOffsetX = position.x;
        initialOffsetY = position.y;
        initialWidth = bounds.width();
        initialHeight = bounds.height();
        System.out.println("Component scaling started!");
    }
    protected void onResizing() { System.out.println("Component is resizing!"); }
}
