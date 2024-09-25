package com.tumult.mclu.client.gui.frame;

import com.tumult.mclu.client.gui.geometry.Rectangle;

public class ClickableIcon extends Rectangle implements IClickable {
    private DrawableIcon icon;

    public ClickableIcon(DrawableIcon icon) {
        this.icon = icon;
    }
}
