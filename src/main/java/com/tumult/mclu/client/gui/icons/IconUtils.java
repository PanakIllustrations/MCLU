package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.client.gui.frame.core.UIElement;

public enum IconUtils {
    INSTANCE;

    private static GuiIcons icon;

    public static GuiIcons getIcon() {
        if (icon == null) {
            icon = new GuiIcons();
        }
        return icon;
    }
}