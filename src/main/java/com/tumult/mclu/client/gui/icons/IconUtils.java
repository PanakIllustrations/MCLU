package com.tumult.mclu.client.gui.icons;

public enum IconUtils {
    INSTANCE;

    private static GuiIcons icon;
    private static UIComponent UIComponent;

    public static GuiIcons getIcon() {
        if (icon == null) {
            icon = new GuiIcons();
        }
        return icon;
    }
}