package com.tumult.mclu.client.gui.icons;

public enum IconUtils {
    INSTANCE;

    private static GuiIcons icon;

    public static GuiIcons getIcon() {
        if (icon == null) {
            icon = new GuiIcons();
        }
        return icon;
    }

    public static GuiIcons getClickableIcon(Runnable onClick) {
        if (icon == null) {
            icon = new GuiIcons();
        }
        return icon;
    }
}