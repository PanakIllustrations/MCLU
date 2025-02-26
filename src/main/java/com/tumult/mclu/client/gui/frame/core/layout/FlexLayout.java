package com.tumult.mclu.client.gui.frame.core.layout;

import com.tumult.mclu.client.gui.frame.core.AbstractGuiElement;
import com.tumult.mclu.client.gui.frame.core.css.Style;
import com.tumult.mclu.client.gui.frame.core.geometry.IContainer;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;

import java.util.List;

public class FlexLayout implements LayoutStrategy {
    private final Style.JustifyContent justifyContent;
    private final Style.Align alignItems;
    private final int gap;

    public FlexLayout(Style.JustifyContent justifyContent, Style.Align alignItems, int gap) {
        this.justifyContent = justifyContent;
        this.alignItems = alignItems;
        this.gap = gap;
    }
    @Override
    public void applyLayout(IContainer container) {
        List<AbstractGuiElement<?>> children = container.getChildren();
        if (children.isEmpty()) return;

        float containerLeft = container.left();
        float containerTop = container.top();
        float containerWidth = container.width();
        float containerHeight = container.height();

        // Calculate total content width
        float totalContentWidth = 0;
        for (AbstractGuiElement<?> child : children) {
            totalContentWidth += child.rect.width();
        }
        if (children.size() > 1) {
            totalContentWidth += gap * (children.size() - 1);
        }

        // Starting X position based on justifyContent
        float x = containerLeft;
        switch (justifyContent) {
            case flexStart:
                // Already set to containerLeft
                break;
            case center:
                x += (containerWidth - totalContentWidth) / 2;
                break;
            case flexEnd:
                x += (containerWidth - totalContentWidth);
                break;
        }

        // Position each child
        for (AbstractGuiElement<?> child : children) {
            // Y position based on alignItems
            float y = containerTop;
            switch (alignItems) {
                case top:
                    // Already set to containerTop
                    break;
                case center:
                    y += (containerHeight - child.rect.height()) / 2;
                    break;
                case bottom:
                    y += (containerHeight - child.rect.height());
                    break;
            }

            // Set position of the child's geometry
            child.rect.setUL(x, y);

            // Move to next position
            x += child.rect.width() + gap;
        }

        // Mark layout as clean
        container.clearLayoutDirty();
    }
}