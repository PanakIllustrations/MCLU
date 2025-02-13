package com.tumult.mclu.client.gui.frame.old;

public interface IEvents {

    default void onMouseHover() { System.out.println("Mouse hovered over component"); }
    default void onMouseLeave() { System.out.println("Mouse cursor left the component"); }
    default void onMouseDown() { System.out.println("Mouse clicked on component"); }
    default void onMouseHeld() { System.out.println("Mouse held on component"); }
    default void onMouseDrag() { System.out.println("Mouse dragged component"); }
    default void onMouseScroll() { System.out.println("Mouse scrolled on component"); }
    default void onMouseUp() { System.out.println("Mouse released component"); }
}
