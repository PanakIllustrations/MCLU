package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.client.gui.frame.geometry.DrawableRect;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.joml.Vector2d;

public class UIComponent extends DrawableRect {
    // State flags
    public boolean isHidden;
    private boolean isDisabled, isResizable, isGrabbable;

    // Interaction states
    private boolean isHovered, wasHovered;
    private boolean isCornerHovered;

    private boolean isGrabbed, wasGrabbed;

    // Interaction tracking
    public boolean isValidPosition = true;
    private int tickCounter;
    private int dragInitialX, dragInitialY;
    //private int resizeInitialWidth, resizeInitialHeight;
    

    // Configuration constants
    private static final int GRAB_TICK_THRESHOLD = 5;
    private static final double CORNER_DISTANCE_THRESHOLD = 1.0;
    private static final double MIN_WIDTH = 20.0, MIN_HEIGHT = 20.0; // Minimum width and height
    private static final double MAX_WIDTH = 400.0, MAX_HEIGHT = 400.0;

    // Screen bounds
    private static double SCREEN_WIDTH;
    private static double SCREEN_HEIGHT;

    public UIComponent(ResourceLocation resourceLocation, Vector2DPoint iconDimensions, Vector2DPoint textureDimensions) {
        this(resourceLocation, iconDimensions, textureDimensions, false, false, true, true);
        SCREEN_WIDTH = Minecraft.getInstance().getWindow().getWidth();
        SCREEN_HEIGHT = Minecraft.getInstance().getWindow().getHeight();
        resetRectInitialValues();
    }

    public UIComponent(ResourceLocation resourceLocation, Vector2DPoint iconDimensions, Vector2DPoint textureDimensions, boolean isDisabled, boolean isHidden, boolean isResizable, boolean isGrabbable) {
        super(resourceLocation, iconDimensions, textureDimensions);
        setFlagState(isDisabled, isHidden, isResizable, isGrabbable);
        SCREEN_WIDTH = Minecraft.getInstance().getWindow().getWidth();
        SCREEN_HEIGHT = Minecraft.getInstance().getWindow().getWidth();
        resetRectInitialValues();
    }
    public void setFlagState(boolean isDisabled, boolean isHidden, boolean isResizable, boolean isGrabbable){
        this.isDisabled = isDisabled;
        this.isHidden = isHidden;
        this.isResizable = isResizable;
        this.isGrabbable = isGrabbable;
    }
    /**
     * Initializes or resets initial values for position and dimensions.
     */
    private void resetRectInitialValues() {
        dragInitialX = (int) this.ul.x;
        dragInitialY = (int) this.ul.y;
        //resizeInitialWidth = (int) this.wh.x;
        //resizeInitialHeight = (int) this.wh.y;
    }
    /**
     * Main update method to handle hover and click state based on mouse interaction.
     */
    public void update(double mouseX, double mouseY, int button){
        if (isHidden || isDisabled) return;
        updateHoverState(mouseX, mouseY);
        handleCornerHover(mouseX, mouseY);
        handleGrabState(mouseX, mouseY, button);
    }
    /**
     * Updates the hover state and triggers hover-related events.
     */
    private void updateHoverState(double mouseX, double mouseY) {
        isHovered = this.contains(mouseX, mouseY);
        if (!wasHovered && isHovered) onMouseHover();
        if (wasHovered && !isHovered) onMouseLeave();
        wasHovered = isHovered;
    }
    /**
     * Handles state changes and events for grabbing the component.
     */
    private void handleGrabState(double mouseX, double mouseY, int button) {
        if (button == 1) {
            if (!wasGrabbed) {
                isGrabbed = true;
                clickSound(Minecraft.getInstance().getSoundManager());
                onMouseDown();
                tickCounter = 0;
                dragInitialX = (int) (mouseX - this.ul.x);
                dragInitialY = (int) (mouseY - this.ul.y);
            }
        } else {
            if (isGrabbed) {
                isGrabbed = false;
                onMouseUp();
                tickCounter = 0;
            }
        }

        // Handle dragging state separately now that isGrabbed is independent of hover
        if (isGrabbable && isGrabbed) {
            handleDragging(mouseX, mouseY);
        }

        // Update the previous grab state for the next frame
        wasGrabbed = isGrabbed;
    }
    /**
     * Determines if the component is being dragged and updates its position.
     */
    private void handleDragging(double mouseX, double mouseY) {
        if (tickCounter > GRAB_TICK_THRESHOLD) {
            if (isCornerHovered) {
                this.wh.set(
                        Math.max(MIN_WIDTH, Math.min(mouseX - this.ul.x, MAX_WIDTH)),
                        Math.max(MIN_HEIGHT, Math.min(mouseY - this.ul.y, MAX_HEIGHT))
                );
                onResizing();
            } else {
                // Update component position while dragging
                double deltaX = mouseX - this.getWh().x / 2;
                double deltaY = mouseY - this.getWh().y / 2;

                this.ul.set(deltaX, deltaY);
                onDragging();
            }
        } else {
            System.out.println("tickCounter: " + tickCounter);
        }
        tickCounter++;
    }
    /**
     * Updates the state of corner hover based on the mouse position.
     */
    private void handleCornerHover(double mouseX, double mouseY) {
        isCornerHovered = isResizable && this.near(mouseX, mouseY, CORNER_DISTANCE_THRESHOLD);
        if (isCornerHovered) onCornerHover();
    }

    protected void clickSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
    // Placeholder methods for handling UI component events
    protected void onMouseHover() { System.out.println("Component is hovered!"); }
    protected void onMouseLeave() { System.out.println("Mouse cursor left the component!"); }
    protected void onMouseDown() { System.out.println("Mouse button pressed!"); }
    protected void onMouseUp() { System.out.println("Mouse button released!"); }
    protected void onDragging() { System.out.println("Component is dragging!"); }
    protected void onCornerHover() { System.out.println("Component corner is hovered!"); }
    protected void onResizing() { System.out.println("Component is resizing!"); }
}
