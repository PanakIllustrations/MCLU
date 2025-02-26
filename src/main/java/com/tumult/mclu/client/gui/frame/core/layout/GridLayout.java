package com.tumult.mclu.client.gui.frame.core.layout;

import com.tumult.mclu.client.gui.frame.core.AbstractGuiElement;
import com.tumult.mclu.client.gui.frame.core.geometry.IContainer;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;

import java.util.List;

public class GridLayout implements LayoutStrategy {
    private final int columns;
    private final int rowGap;
    private final int columnGap;

    public GridLayout(int columns, int rowGap, int columnGap) {
        this.columns = columns;
        this.rowGap = rowGap;
        this.columnGap = columnGap;
    }

    @Override
    public void applyLayout(IContainer container) {
        List<AbstractGuiElement<?>> children = container.getChildren();
        if (children.isEmpty()) return;

        float startX = container.left();
        float x = startX;
        float y = container.top();
        int columnCount = 0;

        // First pass: measure row heights
        float[] rowHeights = new float[children.size() / columns + 1];
        int currentRow = 0;

        for (int i = 0; i < children.size(); i++) {
            AbstractGuiElement<?> child = children.get(i);
            int rowIndex = i / columns;
            rowHeights[rowIndex] = Math.max(rowHeights[rowIndex], child.rect.height());

            if ((i + 1) % columns == 0 && i < children.size() - 1) {
                currentRow++;
            }
        }

        // Second pass: position elements
        currentRow = 0;
        float rowY = container.top();

        for (AbstractGuiElement<?> child : children) {
            // If we've reached the column limit, move to next row
            if (columnCount >= columns) {
                columnCount = 0;
                x = startX;
                rowY += rowHeights[currentRow] + rowGap;
                currentRow++;
            }

            child.rect.setUL(x, rowY);

            // Move to next column position
            x += child.rect.width() + columnGap;
            columnCount++;
        }

        // Mark layout as clean
        container.clearLayoutDirty();
    }
}