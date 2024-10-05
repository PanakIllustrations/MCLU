package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends DrawableRect {
    protected List<InventorySlot> slots;

    public Inventory(Vector4DRect bounds) {
        super(bounds);
        this.slots = new ArrayList<>();
    }

    public void addSlot(InventorySlot slot) {
        slots.add(slot);
        addChild(slot); // Add the slot as a child element
    }

    @Override
    public void update(double mouseX, double mouseY, int button) {
        super.update(mouseX, mouseY, button);
        // Update logic for the inventory and its slots
    }

    @Override
    public void draw() {
        super.draw();
        // Draw inventory slots
    }
}
