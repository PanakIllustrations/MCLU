package com.tumult.mclu.client.gui.frame;

public interface IScrollable extends IClickable {
    void scrollUp(int amount);
    void scrollDown(int amount);
    void setScrollPosition(int amount);
    int getScrollPosition();
    int getMaxScrollPosition();
    default void handleMouseScroll(double scrollAmount) {
        if (scrollAmount > 0) {
            scrollDown((int) scrollAmount);
        } else if (scrollAmount < 0) {
            scrollUp((int) scrollAmount);
        }
    }
}
