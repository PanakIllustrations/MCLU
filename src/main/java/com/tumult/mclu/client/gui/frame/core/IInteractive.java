package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;

import java.util.List;

/**
 * Interface for UI elements that can receive mouse input events
 */
public interface IInteractive {

    /**
     * Update the interactive state based on mouse input
     * @param mousePosition Current mouse position
     * @param mouseButtons List of currently pressed mouse buttons
     */
    void update(Vector2DPoint mousePosition, List<Integer> mouseButtons);

    /**
     * Returns true if this element is actively capturing input (e.g., dragging)
     * When true, parent elements should not process input
     */
    default boolean isCapturingInput() {
        return false;
    }

    default void onMouseEnter() {
        System.out.println("Mouse entered " + this.getClass().getSimpleName());
    }

    default void onMouseLeave() {
        System.out.println("Mouse left " + this.getClass().getSimpleName());
    }

    default void onMouseDown() {
        System.out.println("Mouse down on " + this.getClass().getSimpleName());
    }

    default void onMouseUp() {
        System.out.println("Mouse up on " + this.getClass().getSimpleName());
    }

    default void onMouseHover() {}

    default void onClick() {
        System.out.println("Clicked " + this.getClass().getSimpleName());
    }
}