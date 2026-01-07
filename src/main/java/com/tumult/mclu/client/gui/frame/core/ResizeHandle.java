package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.util.List;

public class ResizeHandle extends InteractiveSprite {

    private enum DragState {
        IDLE,
        HOVERED,
        DRAGGING
    }

    private DragState dragState = DragState.IDLE;
    private DragState previousDragState = DragState.IDLE;
    private Vector2DPoint dragStartMouse = null;
    private Vector2DPoint dragStartSize = null;
    private double minWidth = 50;
    private double minHeight = 50;

    public ResizeHandle(ResourceLocation texture, Vector4DRect rect, Vector4DRect uv, float z) {
        super(texture, rect, uv, z);
    }

    @Override
    public boolean isCapturingInput() {
        return dragState == DragState.DRAGGING;
    }

    public void setMinSize(double width, double height) {
        this.minWidth = width;
        this.minHeight = height;
    }

    @Override
    public void update(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        previousDragState = dragState;
        updateDragState(mousePosition, mouseButtons);
        handleDragTransitions();
    }

    private void updateDragState(Vector2DPoint mousePosition, List<Integer> mouseButtons) {
        boolean isOver = rectBounds.contains(mousePosition);
        boolean isPressed = mouseButtons.contains(UIManager.LEFT_BUTTON);

        if (dragState == DragState.DRAGGING && isPressed) {
            // Continue dragging even if mouse leaves
            dragState = DragState.DRAGGING;
        } else if (isOver && isPressed) {
            dragState = DragState.DRAGGING;
        } else if (isOver) {
            dragState = DragState.HOVERED;
        } else {
            dragState = DragState.IDLE;
        }
    }

    private void handleDragTransitions() {
        // Start dragging
        if (dragState == DragState.DRAGGING && previousDragState != DragState.DRAGGING) {
            onDragStart();
        }

        // Continue dragging
        if (dragState == DragState.DRAGGING && previousDragState == DragState.DRAGGING) {
            onDrag();
        }

        // End dragging
        if (previousDragState == DragState.DRAGGING && dragState != DragState.DRAGGING) {
            onDragEnd();
        }

        // Mouse entered
        if (dragState == DragState.HOVERED && previousDragState == DragState.IDLE) {
            onMouseEnter();
        }

        // Mouse left
        if (dragState == DragState.IDLE && previousDragState == DragState.HOVERED) {
            onMouseLeave();
        }
    }

    private void onDragStart() {
        System.out.println("Resize drag started!");
        dragStartMouse = new Vector2DPoint(UIManager.getMousePos());

        if (parent != null) {
            Vector4DRect parentBounds = parent.getBounds();
            dragStartSize = new Vector2DPoint(parentBounds.width(), parentBounds.height());
        }

        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.8F)
        );
    }

    private void onDrag() {
        if (parent == null || dragStartMouse == null || dragStartSize == null) return;

        Vector2DPoint currentMouse = UIManager.getMousePos();
        Vector2DPoint delta = currentMouse.sub(dragStartMouse);

        // Calculate new size
        double newWidth = Math.max(minWidth, dragStartSize.x() + delta.x());
        double newHeight = Math.max(minHeight, dragStartSize.y() + delta.y());

        // Update parent size
        parent.getBounds().setWh(newWidth, newHeight);
    }

    private void onDragEnd() {
        System.out.println("Resize drag ended!");
        dragStartMouse = null;
        dragStartSize = null;

        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F)
        );
    }
}