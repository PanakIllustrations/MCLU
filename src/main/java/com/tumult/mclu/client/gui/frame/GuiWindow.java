package com.tumult.mclu.client.gui.frame;
import java.util.List;

public class GuiWindow extends Frame {
    private List<GuiComponent> children;

    GuiWindow(double x, double y, int width, int height) {
        super(x, y, width, height);
    }
}
