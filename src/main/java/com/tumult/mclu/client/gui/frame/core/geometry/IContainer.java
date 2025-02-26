package com.tumult.mclu.client.gui.frame.core.geometry;

import com.tumult.mclu.client.gui.frame.core.AbstractGuiElement;
import com.tumult.mclu.client.gui.frame.core.layout.LayoutStrategy;

import java.util.List;

public interface IContainer extends IRect {
    List<AbstractGuiElement<?>> getChildren();
    void addChild(AbstractGuiElement<?> child);
    void removeChild(AbstractGuiElement<?> child);

    // Layout management
    LayoutStrategy getLayoutStrategy();
    void setLayoutStrategy(LayoutStrategy strategy);
    void layoutChildren();

    // recalculation management
    boolean isLayoutDirty();
    void markLayoutDirty();
    void clearLayoutDirty();
}
